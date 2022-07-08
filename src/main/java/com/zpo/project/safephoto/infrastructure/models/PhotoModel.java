package com.zpo.project.safephoto.infrastructure.models;

public class PhotoModel {
    private String photo;
    private long id;
    private String base64Iv;

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getBase64Iv() {
        return base64Iv;
    }

    public void setBase64Iv(String base64Iv) {
        this.base64Iv = base64Iv;
    }

    public PhotoModel(String photo, long id, String base64Iv) {
        this.photo = photo;
        this.id = id;
        this.base64Iv = base64Iv;
    }
}
