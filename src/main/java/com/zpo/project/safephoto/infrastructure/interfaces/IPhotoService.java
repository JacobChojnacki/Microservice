package com.zpo.project.safephoto.infrastructure.interfaces;
import com.zpo.project.safephoto.domain.Photo;
import com.zpo.project.safephoto.domain.snapshots.PhotoSnapshot;
import com.zpo.project.safephoto.infrastructure.models.FilterModel;

import java.util.List;

public interface IPhotoService {
    List<PhotoSnapshot> photoFilter(List<PhotoSnapshot> listOfImages, FilterModel filterModel);
}
