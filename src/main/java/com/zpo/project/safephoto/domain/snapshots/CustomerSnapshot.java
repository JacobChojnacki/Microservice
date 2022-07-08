package com.zpo.project.safephoto.domain.snapshots;

import javax.crypto.SecretKey;
import java.util.List;

/***
 * Snapshot to najmniejsza ilość informacji potrzebna do odtworzenia stanu encji
 * Nie ma logiki biznesowej
 */
public class CustomerSnapshot {
    private List<PhotoSnapshot> photoSnapshots;
    private SecretKey key;
    private long id;

    private String countryCode;


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public void setPhotoSnapshots(List<PhotoSnapshot> photoSnapshots) {
        this.photoSnapshots = photoSnapshots;
    }

    public List<PhotoSnapshot> getPhotoSnapshots() {
        return photoSnapshots;
    }

    public CustomerSnapshot(List<PhotoSnapshot> photoSnapshots, SecretKey key, long id, String countryCode) {
        this.photoSnapshots = photoSnapshots;
        this.key = key;
        this.id = id;
        this.countryCode = countryCode;
    }
}
