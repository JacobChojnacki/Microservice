package com.zpo.project.safephoto.infrastructure.services;

import com.zpo.project.safephoto.infrastructure.interfaces.ICypherService;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/***
 * Dodanie do infrastruktury serwisu pozwala na odseparowanie operacji na danych do oddzielnych klas
 * Nie zapisują i nie odczytują danych z pamięci, ale modyfikują te dane jeśli baza nie potrafi (np. szyfrowanie)
 */
@Component
public class CypherService implements ICypherService {
    private static final String algorithm = "AES/CBC/PKCS5PADDING";
    @Override
    public String encrypt(byte[] bytesToEncode, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] encrypted = cipher.doFinal(bytesToEncode);
        return Base64.getEncoder()
                .encodeToString(encrypted);
    }

    @Override
    public byte[] decrypt(String stringToDecode, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(Base64.getDecoder()
                .decode(stringToDecode));
    }

    @Override
    public String encrypt(byte[] bytesToEncode, String password, String salt, IvParameterSpec iv) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKey secretKey = getSecretKeyFromPassword(password, salt);
        return encrypt(bytesToEncode, secretKey, iv);
    }

    @Override
    public byte[] decrypt(String stringToDecode, String password, String salt, IvParameterSpec iv) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKey secretKey = getSecretKeyFromPassword(password, salt);
        return decrypt(stringToDecode, secretKey, iv);
    }

    @Override
    public IvParameterSpec generateIv(int length) {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    @Override
    public String ivToString(IvParameterSpec iv){
        return Base64.getEncoder().encodeToString(iv.getIV());
    }

    @Override
    public IvParameterSpec ivFromString(String iv){
        return new IvParameterSpec(Base64.getDecoder().decode(iv));
    }

    @Override
    public String keyToString(SecretKey secretKey){
        byte[] rawData = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(rawData);
    }

    @Override
    public SecretKey keyFromString(String encodedKey){
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private SecretKeySpec getSecretKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(secretKeyFactory
                .generateSecret(spec)
                .getEncoded(), "AES");
    }
}
