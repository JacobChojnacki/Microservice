package com.zpo.project.safephoto.domain.interfaces;

import com.zpo.project.safephoto.domain.Customer;
import com.zpo.project.safephoto.domain.Photo;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ICustomerRepository {
    Customer getOneByToken(String token) throws Exception;
    void savePhoto(Customer customer, Photo photo) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException;
}
