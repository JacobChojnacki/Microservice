package com.zpo.project.safephoto.infrastructure.interfaces;

import com.zpo.project.safephoto.domain.Metadata;
import com.zpo.project.safephoto.domain.projections.MetadataProjection;
import com.zpo.project.safephoto.domain.snapshots.MetadataSnapshot;
import com.zpo.project.safephoto.infrastructure.models.CustomerModel;
import com.zpo.project.safephoto.infrastructure.models.MetadataModel;
import com.zpo.project.safephoto.infrastructure.models.PhotoModel;

public interface ICustomerService {
    CustomerModel parseFromString(String customerString);

    PhotoModel parsePhotoFromString(String photoString);

    MetadataModel parseMetadataFromString(String metadataString);

    String serializeToString(Metadata metadata);

    String serializeToString(MetadataModel metadata);

    String serializeToString(PhotoModel metadata);

    MetadataSnapshot parseMetadataSnapshotFromString(String metadataSnapshot);

    MetadataProjection parseMetadataProjectionFromString(String metadataProjection);
}
