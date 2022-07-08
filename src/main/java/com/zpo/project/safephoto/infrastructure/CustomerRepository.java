package com.zpo.project.safephoto.infrastructure;

import com.zpo.project.safephoto.domain.Customer;
import com.zpo.project.safephoto.domain.Photo;
import com.zpo.project.safephoto.domain.interfaces.ICustomerRepository;
import com.zpo.project.safephoto.domain.snapshots.CustomerSnapshot;
import com.zpo.project.safephoto.domain.snapshots.MetadataSnapshot;
import com.zpo.project.safephoto.domain.snapshots.PhotoSnapshot;
import com.zpo.project.safephoto.infrastructure.interfaces.ICustomerService;
import com.zpo.project.safephoto.infrastructure.interfaces.ICustomerTokenDecoder;
import com.zpo.project.safephoto.infrastructure.interfaces.ICypherService;
import com.zpo.project.safephoto.infrastructure.interfaces.IFileService;
import com.zpo.project.safephoto.infrastructure.models.CustomerModel;
import com.zpo.project.safephoto.infrastructure.models.MetadataModel;
import com.zpo.project.safephoto.infrastructure.models.PhotoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.stereotype.Repository;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

/***
 * Służy do pobierania agregatu/encji w celu zmiany jego stanu (dodawanie, edycja itd.)
 */
@Repository
public class CustomerRepository implements ICustomerRepository {
    public static final String PHOTOS = "photos/";
    private ICypherService cypherService;
    private ICustomerTokenDecoder customerTokenDecoder;
    private ICustomerService customerService;
    private IFileService fileService;

    @Autowired
    public CustomerRepository(ICypherService cypherService, ICustomerTokenDecoder customerTokenDecoder, ICustomerService customerService, IFileService fileService) {
        this.cypherService = cypherService;
        this.customerTokenDecoder = customerTokenDecoder;
        this.customerService = customerService;
        this.fileService = fileService;
    }

    @Override
    public Customer getOneByToken(String token) throws Exception {
        var customerModel = composeCustomer(token);
        var customerKey = getCustomerKey(customerModel);

        var fileList = fileService.listFilesInDirectory("photos/" + customerModel.getId());
        var photoList = fileList.stream().filter(name -> !name.contains(".metadata")).toList();
        List<PhotoSnapshot> photoSnapshots = new ArrayList<>();
        for (var photoId : photoList) {
            var photoString = fileService.loadFromFile(PHOTOS + customerModel.getId() + "/" + photoId);
            var photoModel = customerService.parsePhotoFromString(photoString);
            var photoBytes = cypherService.decrypt(photoModel.getPhoto(), customerKey, cypherService.ivFromString(photoModel.getBase64Iv()));
            var metadataFileName = photoId + ".metadata";
            var metadataModelString = fileService.loadFromFile("photos/" + customerModel.getId() + "/" + metadataFileName);
            var metadataModel = customerService.parseMetadataFromString(metadataModelString);
            var metadataString = new String(cypherService.decrypt(metadataModel.getMetadata(), customerKey, cypherService.ivFromString(metadataModel.getBase64Iv())));
            var metadataSnapshot = customerService.parseMetadataSnapshotFromString(metadataString);
            var photoSnapshot = new PhotoSnapshot(photoBytes, metadataSnapshot, Long.parseLong(photoId));
            photoSnapshots.add(photoSnapshot);
        }
        return Customer.restoreFromSnapshot(new CustomerSnapshot(photoSnapshots, customerKey, customerModel.getId(), customerModel.getCountryCode())); // tutaj ważne, żeby new Customer zamienić na zmapowany na snapshot customerFromToken
    }

    private SecretKey getCustomerKey(CustomerModel customer) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return cypherService.keyFromString(
                new String(cypherService.decrypt(
                        customer.getEncryptedKey(), customer.getPassword(), customer.getSalt(), cypherService.ivFromString(customer.getBase64Iv())
                ))
        );
    }

    @Override
    public void savePhoto(Customer customer, Photo photo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        var metadataString = customerService.serializeToString(photo.getMetadata());
        var metadataIv = cypherService.generateIv(16);
        var encryptedMetadata = cypherService.encrypt(metadataString.getBytes(), customer.getKey(), metadataIv);
        var metadataModel = new MetadataModel(encryptedMetadata, cypherService.ivToString(metadataIv));
        var metadataModelString = customerService.serializeToString(metadataModel);
        var photoIv = cypherService.generateIv(16);
        var encryptedPhoto = cypherService.encrypt(photo.getPhotoBytes(), customer.getKey(), photoIv);
        var photoModel = new PhotoModel(encryptedPhoto, photo.getId(),cypherService.ivToString(photoIv));
        var photoModelString = customerService.serializeToString(photoModel);
        fileService.saveToFile("photos/" + customer.getId() + "/" + photo.getId() + ".metadata", metadataModelString);
        fileService.saveToFile("photos/" + customer.getId() + "/" + photo.getId(), photoModelString);
    }

    private CustomerModel composeCustomer(String token) throws Exception {
        var customer = customerTokenDecoder.getCustomerFromToken(token);
        var customerString = fileService.loadFromFile("customer/" + customer.getId());
        var customerFromFile = customerService.parseFromString(customerString);
        customer.setEncryptedKey(customerFromFile.getEncryptedKey());
        customer.setBase64Iv(customerFromFile.getBase64Iv());
        customer.setSalt(customerFromFile.getSalt());
        return customer;
    }
}
