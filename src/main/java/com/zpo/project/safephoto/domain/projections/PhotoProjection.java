package com.zpo.project.safephoto.domain.projections;

import com.zpo.project.safephoto.domain.Metadata;

public class PhotoProjection {
    private byte[] photoBytes;

    public byte[] getPhotoBytes() {
        return photoBytes;
    }

    public MetadataProjection getMetadata() {
        return metadata;
    }

    public PhotoProjection(byte[] photoBytes, MetadataProjection metadata) {
        this.photoBytes = photoBytes;
        this.metadata = metadata;
    }

    private MetadataProjection metadata;
}
