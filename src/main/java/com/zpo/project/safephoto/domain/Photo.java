package com.zpo.project.safephoto.domain;

import com.zpo.project.safephoto.domain.snapshots.PhotoSnapshot;
import org.springframework.beans.factory.annotation.Autowired;

public class Photo {
    private byte[] photoBytes;
    private Metadata metadata;
    private long id;

    public Photo(byte[] photoBites, Metadata metadata, long id) {
        this.photoBytes = photoBites;
        this.metadata = metadata;
        this.id = id;
    }

    public static Photo create(byte[] photoBites, Metadata metadata, long id){
        // walidacja
        return new Photo(photoBites, metadata, id);
    }

    public long getId() {
        return id;
    }

    public static Photo restoreFromSnapshot(PhotoSnapshot snapshot){
        return new Photo(snapshot.getPhoto(), Metadata.restoreFromSnapshot(snapshot.getMetadata()), snapshot.getId());
    }

    public byte[] getPhotoBytes() {
        return photoBytes;
    }

    public Metadata getMetadata() {
        return metadata;
    }
}
