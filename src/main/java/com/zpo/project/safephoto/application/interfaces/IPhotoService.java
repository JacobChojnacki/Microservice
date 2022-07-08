package com.zpo.project.safephoto.application.interfaces;

import com.zpo.project.safephoto.application.dtos.request.AddPhotoRequestDto;
import com.zpo.project.safephoto.application.dtos.request.GetPhotosRequestDto;
import com.zpo.project.safephoto.application.dtos.response.GetPhotoResponseDto;
import com.zpo.project.safephoto.application.dtos.response.GetPhotosResponseDto;

import java.io.IOException;

public interface IPhotoService {
    long addPhoto(AddPhotoRequestDto dto, String token) throws Exception;
    GetPhotoResponseDto getPhoto(long id, String token) throws Exception;
    GetPhotosResponseDto getPhotos(GetPhotosRequestDto dto, String token) throws Exception;
}
