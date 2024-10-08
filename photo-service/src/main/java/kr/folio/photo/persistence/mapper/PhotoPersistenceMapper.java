package kr.folio.photo.persistence.mapper;

import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.persistence.entity.PhotoEntity;
import org.springframework.stereotype.Component;

@Component
public class PhotoPersistenceMapper {

    public PhotoEntity toEntity(Photo photo) {
        return PhotoEntity.builder()
            .photoUrl(photo.getPhotoImageUrl())
            .brandType(photo.getBrandType())
            .taggedUserIds(photo.getTaggedUserIds())
            .viewCount(photo.getViewCount())
            .build();
    }

    public Photo toDomain(PhotoEntity photoEntity) {
        if (photoEntity == null) {
            return null;
        }

        return Photo.builder()
            .photoId(photoEntity.getId())
            .brandType(photoEntity.getBrandType())
            .photoImageUrl(photoEntity.getPhotoUrl())
            .taggedUserIds(photoEntity.getTaggedUserIds())
            .viewCount(photoEntity.getViewCount())
            .build();
    }
}
