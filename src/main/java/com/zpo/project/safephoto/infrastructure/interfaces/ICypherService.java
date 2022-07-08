package com.zpo.project.safephoto.infrastructure.interfaces;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface ICypherService {
    String encrypt(byte[] bytesToEncode, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException;
    byte[] decrypt(String stringToDecode, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;
    String encrypt(byte[] bytesToEncode, String password, String salt, IvParameterSpec iv) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
    byte[] decrypt(String stringToDecode, String password, String salt, IvParameterSpec iv) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
    IvParameterSpec generateIv(int length);

    String ivToString(IvParameterSpec iv);
    IvParameterSpec ivFromString(String iv);
    String keyToString(SecretKey secretKey);
    SecretKey keyFromString(String encodedKey);
}
