package com.zpo.project.safephoto.domain.interfaces;

import com.zpo.project.safephoto.domain.projections.PhotoProjection;
import com.zpo.project.safephoto.domain.snapshots.PhotoSnapshot;
import java.util.List;

public interface IPhotoProjector {
    PhotoProjection getById(long id, String token) throws Exception;
    List<PhotoSnapshot> getManyByCustomerToken(String token) throws Exception;
}
