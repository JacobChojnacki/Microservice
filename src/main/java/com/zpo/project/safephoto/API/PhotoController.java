package com.zpo.project.safephoto.API;

import com.zpo.project.safephoto.application.dtos.request.AddPhotoRequestDto;
import com.zpo.project.safephoto.application.dtos.request.GetPhotosRequestDto;
import com.zpo.project.safephoto.application.dtos.request.MetadataRequestDto;
import com.zpo.project.safephoto.application.dtos.response.GetPhotoResponseDto;
import com.zpo.project.safephoto.application.dtos.response.GetPhotosResponseDto;
import com.zpo.project.safephoto.application.interfaces.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@RestController
public class PhotoController {
    private IPhotoService service;

    @Autowired
    public PhotoController(IPhotoService service) {
        this.service = service;
    }

    @GetMapping(value = "/photo/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, HttpEntity<?>>> getPhoto(
            @PathVariable(value = "id") long id,
            @RequestHeader("CustomerToken") String token
    ) throws Exception {
        var photoDto = service.getPhoto(id, token);
        var response = new MultipartBodyBuilder();
        response.part("metadata", photoDto.getMeta(), MediaType.APPLICATION_JSON);
        response.part("photo", new ByteArrayResource(photoDto.getPhoto()), MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(response.build(), HttpStatus.OK);
    }

    @GetMapping("/photo")
    public GetPhotosResponseDto getPhotos(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "author", required = false) String author,
            @RequestHeader("CustomerToken") String token
    ) throws Exception {
        var dto = new GetPhotosRequestDto();
        dto.setTitle(title);
        dto.setLocation(location);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setDescription(description);
        dto.setAuthor(author);
        return service.getPhotos(dto, token);
    }

    @PostMapping(path = "/photo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long postPhoto(
            @RequestPart("metadata") MetadataRequestDto metadata,
            @RequestPart("photo") MultipartFile photo,
            @RequestHeader("CustomerToken") String token
    ) throws Exception {
        var dto = new AddPhotoRequestDto();
        dto.setPhoto(photo.getBytes());
        dto.setMetadata(metadata);
        dto.getMetadata().setFormat(photo.getContentType());
        return service.addPhoto(dto, token);
    }
}
