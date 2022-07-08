package com.zpo.project.safephoto.infrastructure;

import com.zpo.project.safephoto.application.services.PhotoService;
import com.zpo.project.safephoto.domain.Photo;
import com.zpo.project.safephoto.domain.interfaces.IPhotoProjector;
import com.zpo.project.safephoto.domain.projections.PhotoProjection;
import com.zpo.project.safephoto.domain.snapshots.PhotoSnapshot;
import com.zpo.project.safephoto.infrastructure.interfaces.*;
import com.zpo.project.safephoto.infrastructure.models.CustomerModel;
import com.zpo.project.safephoto.infrastructure.models.FilterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/***
 * Służy do pobierania projekcji (reprezentacji) encji dla celów odczytu danych, projekcja może różnić się od encji, np. łączyć kilka encji itd. (bazodanowy widok)
 * Projektor to mój sposób nazywania, można to robić różnie
 */
@Repository
public class PhotoProjector implements IPhotoProjector {

    public static final String PHOTOS = "photos/";
    private ICypherService cypherService;
    private ICustomerTokenDecoder customerTokenDecoder;
    private ICustomerService customerService;
    private IFileService fileService;

    private IPhotoService photoService;

    @Autowired
    public PhotoProjector(ICypherService cypherService,
                          ICustomerTokenDecoder customerTokenDecoder,
                          ICustomerService customerService,
                          IFileService fileService,
                          IPhotoService photoService) {
        this.cypherService = cypherService;
        this.customerTokenDecoder = customerTokenDecoder;
        this.customerService = customerService;
        this.fileService = fileService;
        this.photoService = photoService;
    }

    @Override
    public PhotoProjection getById(long id, String token) throws Exception {
        var customerModel = composeCustomer(token);
        var customerKey = getCustomerKey(customerModel);
        var photoString = fileService.loadFromFile(PHOTOS + customerModel.getId() + "/" + id);
        var photoModel = customerService.parsePhotoFromString(photoString);
        var photoBytes = cypherService.decrypt(photoModel.getPhoto(), customerKey, cypherService.ivFromString(photoModel.getBase64Iv()));
        var metadataFileName = id + ".metadata";
        var metadataModelString = fileService.loadFromFile("photos/" + customerModel.getId() + "/" + metadataFileName);
        var metadataModel = customerService.parseMetadataFromString(metadataModelString);
        var metadataString = new String(cypherService.decrypt(metadataModel.getMetadata(), customerKey, cypherService.ivFromString(metadataModel.getBase64Iv())));
        var metadataProjection = customerService.parseMetadataProjectionFromString(metadataString);
        return new PhotoProjection(photoBytes, metadataProjection);
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

    private SecretKey getCustomerKey(CustomerModel customer) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return cypherService.keyFromString(
                new String(cypherService.decrypt(
                        customer.getEncryptedKey(), customer.getPassword(), customer.getSalt(), cypherService.ivFromString(customer.getBase64Iv())
                ))
        );
    }

    @Override
    public List<PhotoSnapshot> getManyByCustomerToken(String token) throws Exception {
        var customerModel = composeCustomer(token);
        var customerKey = getCustomerKey(customerModel);
        var fileList = fileService.listFilesInDirectory("photos/" + customerModel.getId());
        var photoList = fileList.stream().filter(name -> !name.contains(".metadata")).toList();
        List<PhotoSnapshot> photoSnapshots = new ArrayList<>();
        for (var id : photoList) {
            var photoString = fileService.loadFromFile(PHOTOS + customerModel.getId() + "/" + id);
            var photoModel = customerService.parsePhotoFromString(photoString);
            var photoBytes = cypherService.decrypt(photoModel.getPhoto(), customerKey, cypherService.ivFromString(photoModel.getBase64Iv()));
            var metadataFileName = id + ".metadata";
            var metadataModelString = fileService.loadFromFile("photos/" + customerModel.getId() + "/" + metadataFileName);
            var metadataModel = customerService.parseMetadataFromString(metadataModelString);
            var metadataString = new String(cypherService.decrypt(metadataModel.getMetadata(), customerKey, cypherService.ivFromString(metadataModel.getBase64Iv())));
            var metadataSnapshot = customerService.parseMetadataSnapshotFromString(metadataString);
            var photoSnapshot = new PhotoSnapshot(photoBytes, metadataSnapshot, Long.parseLong(id));
            photoSnapshots.add(photoSnapshot);
        }
        return photoService.photoFilter(photoSnapshots, new FilterModel(null, null, null, null, null, "Jakub Chojnacki"));
    }

}
