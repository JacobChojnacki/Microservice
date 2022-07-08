package com.zpo.project.safephoto;

import com.zpo.project.safephoto.application.interfaces.IPhotoService;
import com.zpo.project.safephoto.domain.Customer;
import com.zpo.project.safephoto.domain.Metadata;
import com.zpo.project.safephoto.domain.Photo;
import com.zpo.project.safephoto.domain.interfaces.ICustomerRepository;
import com.zpo.project.safephoto.domain.interfaces.IPhotoProjector;
import com.zpo.project.safephoto.domain.projections.MetadataProjection;
import com.zpo.project.safephoto.domain.projections.PhotoProjection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@SpringBootTest
public class PhotoFilterServiceTest {

    @Autowired
    private IPhotoService photoService;

    @MockBean
    private IPhotoProjector photoProjector; // mock obiektu klasy implementującej IPhotoProjector

    @Test
    void getPhotoTest() throws Exception {
        var id = 123L; // nasze id zdjęcia

        // tu mówimy co ma robić mock tej klasy, gdy wywołamy konkretną metodę
        Mockito.when(photoProjector.getById(id, null)).thenReturn(new PhotoProjection("fake photo".getBytes(), new MetadataProjection("", "", "", ZonedDateTime.now(), "", "")));

        var photo = photoService.getPhoto(id, null); // pobieramy id z serwisu
        Assertions.assertArrayEquals("fake photo".getBytes(), photo.getPhoto()); // sprawdzamy czy pobrano odpowiednie zdjęcie
    }

}
