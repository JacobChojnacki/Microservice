package com.zpo.project.safephoto.infrastructure.services;

import com.zpo.project.safephoto.domain.Metadata;
import com.zpo.project.safephoto.domain.Photo;
import com.zpo.project.safephoto.domain.snapshots.PhotoSnapshot;
import com.zpo.project.safephoto.infrastructure.interfaces.IPhotoService;
import com.zpo.project.safephoto.infrastructure.models.FilterModel;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoFilterService implements IPhotoService {

    @Override
    public List<PhotoSnapshot> photoFilter(List<PhotoSnapshot> listOfImages, FilterModel filterModel) {
        return listOfImages.stream().filter(
                z -> {
                    if (filterModel.getAuthor() != null) {
                        boolean shouldAuthor = z.getMetadata().getAuthor().equalsIgnoreCase(filterModel.getAuthor());
                        if (!shouldAuthor) {
                            return false;
                        }
                    }
                    if (filterModel.getLocation() != null) {
                        boolean shouldLocation = z.getMetadata().getLocation().equalsIgnoreCase(filterModel.getLocation());
                        if (!shouldLocation) {
                            return false;
                        }
                    }
                    if (filterModel.getTitle() != null) {
                        boolean shouldTitle = z.getMetadata().getTitle().equalsIgnoreCase(filterModel.getTitle());
                        if (!shouldTitle) {
                            return false;
                        }
                    }
                    if (filterModel.getDescription() != null) {
                        boolean shouldDescription = z.getMetadata().getDescription().equalsIgnoreCase(filterModel.getDescription());
                        if (!shouldDescription) {
                            return false;
                        }
                    }
                    boolean correctDate = filterModel.getStartDate() == null && filterModel.getEndDate() == null;

                    if (filterModel.getStartDate() != null && filterModel.getEndDate() != null) {
                        ZonedDateTime localDate = z.getMetadata().getDate();
                        correctDate = localDate.isBefore(filterModel.getEndDate()) && localDate.isAfter(filterModel.getStartDate());
                    }
                    if (filterModel.getStartDate() != null && filterModel.getEndDate() == null) {
                        ZonedDateTime localDate = z.getMetadata().getDate();
                        correctDate = localDate.isAfter(filterModel.getStartDate());
                    }
                    if (filterModel.getStartDate() == null && filterModel.getEndDate() != null) {
                        ZonedDateTime localDate = z.getMetadata().getDate();
                        correctDate = localDate.isBefore(filterModel.getEndDate());
                    }
                    return correctDate;
                }
        ).collect(Collectors.toList());
    }
}
