package com.zpo.project.safephoto.application.dtos.request;

/***
 * DTO to dokładnie taka reprezentacja danych, jaką chcemy wysłać/otrzymać w zapytaniu (u nas HTTP)
 * Często są bardzo podobne lub nawet identyczne do encji/prokecji/snapshotów/modeli, ale nie muszą
 * Taki podział ułatwia przetwarzanie danych, np. DTO będą miały anotacje JSON (patrz metadata)
 */
public class AddPhotoRequestDto {
    private byte[] photo;
    private MetadataRequestDto metadata;

    public byte[] getPhoto() {
        return photo;
    }

    public MetadataRequestDto getMetadata() {
        return metadata;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setMetadata(MetadataRequestDto metadata) {
        this.metadata = metadata;
    }
}
