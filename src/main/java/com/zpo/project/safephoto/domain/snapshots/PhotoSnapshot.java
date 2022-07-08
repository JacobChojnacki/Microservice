package com.zpo.project.safephoto.domain.snapshots;

public class PhotoSnapshot {
    private byte[] photo;
    private long id;
    private MetadataSnapshot metadata;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public PhotoSnapshot(byte[] photo, MetadataSnapshot metadata, long id) {
        this.photo = photo;
        this.metadata = metadata;
        this.id = id;
    }



    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setMetadata(MetadataSnapshot metadata) {
        this.metadata = metadata;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public MetadataSnapshot getMetadata() {
        return metadata;
    }


}
