package com.zpo.project.safephoto.infrastructure.models;

public class MetadataModel {
    private String metadata;
    private String base64Iv;

    public String getBase64Iv() {
        return base64Iv;
    }

    public void setBase64Iv(String base64Iv) {
        this.base64Iv = base64Iv;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getMetadata() {
        return metadata;
    }

    public MetadataModel(String metadata, String base64Iv) {
        this.metadata = metadata;
        this.base64Iv = base64Iv;
    }
}
