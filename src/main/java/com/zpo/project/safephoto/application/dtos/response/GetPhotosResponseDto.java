package com.zpo.project.safephoto.application.dtos.response;

import java.util.List;

public class GetPhotosResponseDto {
    public void setPhotoIds(List<Long> photoIds) {
        this.photoIds = photoIds;
    }

    public List<Long> getPhotoIds() {
        return photoIds;
    }

    public GetPhotosResponseDto(List<Long> photoIds) {
        this.photoIds = photoIds;
    }

    private List<Long> photoIds;
}
