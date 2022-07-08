package com.zpo.project.safephoto.infrastructure.models;

import com.zpo.project.safephoto.domain.value_objects.CountryCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/***
 * Bazodanowy model danych, to jak coś jest zapisywane do pliku/bazy
 * Tu pojawią się anotacje bazodanowe, np. @Column itd
 */
public class CustomerModel {
    private List<PhotoModel> photoModels; // plik
    private String password; // token
    private String salt; // plik
    private String encryptedKey; // plik
    private long id; // token
    private String base64Iv; // plik

    private ArrayList<Integer> enabledServices; // token

    private UUID externalId;

    private String countryCode;


    public CustomerModel(String countryCode, long id, UUID externalId, ArrayList<Integer> enabledServices, String password) {
        this.id = id;
        this.enabledServices = enabledServices;
        this.externalId = externalId;
        this.countryCode = countryCode;
        this.password = password;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


    public void setBase64Iv(String base64Iv) {
        this.base64Iv = base64Iv;
    }

    public String getBase64Iv() {
        return base64Iv;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getEncryptedKey() {
        return encryptedKey;
    }

    public void setPhotoModels(List<PhotoModel> photoModels) {
        this.photoModels = photoModels;
    }

    public List<PhotoModel> getPhotoModels() {
        return photoModels;
    }
}
