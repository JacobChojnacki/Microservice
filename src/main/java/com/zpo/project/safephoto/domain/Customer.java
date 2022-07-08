package com.zpo.project.safephoto.domain;

import com.zpo.project.safephoto.domain.snapshots.CustomerSnapshot;
import com.zpo.project.safephoto.domain.value_objects.CountryCode;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.stream.Collectors;

/***
 * Customer może być naszym agregatem, ale to pojęcie lepiej się sprawdza na poziomie bazy danych, której nie mamy
 * Jest to jednak nasza reprezentacja stanu, encja
 */
public class Customer {
    private List<Photo> photos;
    private long id;
    private SecretKey key; // to nie jest najlepsze rozwiązanie

    private CountryCode countryCode;

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public Customer(List<Photo> photos, long id, SecretKey key, CountryCode countryCode) {
        this.photos = photos;
        this.id = id;
        this.key = key;
        this.countryCode = countryCode;
    }

    public static Customer create(List<Photo> photos, long id, SecretKey key, CountryCode countryCode){
        // tutaj walidacja
        return new Customer(photos, id, key, countryCode);
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public long getId() {
        return id;
    }

    public SecretKey getKey() {
        return key;
    }

    public static Customer restoreFromSnapshot(CustomerSnapshot snapshot) throws Exception {
        return new Customer(snapshot.getPhotoSnapshots().stream().map(Photo::restoreFromSnapshot).collect(Collectors.toList()), snapshot.getId(), snapshot.getKey(), CountryCode.create(snapshot.getCountryCode()));
    }

    public long addPhoto(Photo photo){
        photos.add(photo);
        return photo.getId();
    }
}
