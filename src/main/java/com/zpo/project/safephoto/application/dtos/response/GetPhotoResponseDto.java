package com.zpo.project.safephoto.application.dtos.response;

public class GetPhotoResponseDto {
    private byte[] photo;
    private MetadataResponseDto meta;

    public GetPhotoResponseDto(byte[] photo, MetadataResponseDto meta) {
        this.photo = photo;
        this.meta = meta;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setMeta(MetadataResponseDto meta) {
        this.meta = meta;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public MetadataResponseDto getMeta() {
        return meta;
    }
}
