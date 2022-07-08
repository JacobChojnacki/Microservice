package com.zpo.project.safephoto.application.services;

import com.zpo.project.safephoto.application.dtos.request.AddPhotoRequestDto;
import com.zpo.project.safephoto.application.dtos.request.GetPhotosRequestDto;
import com.zpo.project.safephoto.application.dtos.response.GetPhotoResponseDto;
import com.zpo.project.safephoto.application.dtos.response.GetPhotosResponseDto;
import com.zpo.project.safephoto.application.dtos.response.MetadataResponseDto;
import com.zpo.project.safephoto.application.interfaces.IPhotoService;
import com.zpo.project.safephoto.domain.Metadata;
import com.zpo.project.safephoto.domain.Photo;
import com.zpo.project.safephoto.domain.interfaces.ICustomerRepository;
import com.zpo.project.safephoto.domain.interfaces.IPhotoProjector;
import com.zpo.project.safephoto.domain.snapshots.PhotoSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/***
 * Serwisy obsługują zapytania, pośredniczą w komunikacji client -> domain
 * Przede wszystkim wykonują odpowiednie zapytania do repozytorium/projektora i przetwarzają otrzymane dane na DTO
 */
@Service
public class PhotoService implements IPhotoService {
    private ICustomerRepository customerRepository;
    private IPhotoProjector photoProjector;

    @Autowired
    public PhotoService(ICustomerRepository customerRepository, IPhotoProjector photoProjector) {
        this.customerRepository = customerRepository;
        this.photoProjector = photoProjector;
    }

    @Override
    public long addPhoto(AddPhotoRequestDto dto, String token) throws Exception {
        var customer = customerRepository.getOneByToken(token);
        var id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
        var photo = Photo.create(
                dto.getPhoto(),
                Metadata.create(dto.getMetadata().getAuthor(), dto.getMetadata().getTitle(), dto.getMetadata().getLocation(), dto.getMetadata().getDate(), dto.getMetadata().getDescription(), dto.getMetadata().getFormat()),
                id);
        customerRepository.savePhoto(customer, photo);
        customer.addPhoto(photo);
        return id;
    }

    @Override
    public GetPhotoResponseDto getPhoto(long id, String token) throws Exception {
        var photoProjection = photoProjector.getById(id, token);
        return new GetPhotoResponseDto(
                photoProjection.getPhotoBytes(),
                new MetadataResponseDto(
                        photoProjection.getMetadata().getAuthor(),
                        photoProjection.getMetadata().getTitle(),
                        photoProjection.getMetadata().getLocation(),
                        photoProjection.getMetadata().getDate(),
                        photoProjection.getMetadata().getDescription(),
                        photoProjection.getMetadata().getFormat()
                )
        );
    }

    @Override
    public GetPhotosResponseDto getPhotos(GetPhotosRequestDto dto, String token) throws Exception {
        var photoProjection = photoProjector.getManyByCustomerToken(token);
        return new GetPhotosResponseDto(
                photoProjection.stream().map(PhotoSnapshot::getId).collect(Collectors.toList())
        );
    }
}
