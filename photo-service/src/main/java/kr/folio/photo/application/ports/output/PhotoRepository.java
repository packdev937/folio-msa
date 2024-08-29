package kr.folio.photo.application.ports.output;


import java.util.List;
import java.util.Optional;
import kr.folio.photo.domain.core.entity.Photo;

public interface PhotoRepository {

    Photo createPhoto(Photo user);

    Optional<Photo> findPhotoById(Long photoId);

    Photo updatePhoto(Photo photo);

    void deletePhotoById(Long photoId);

    List<Photo> findLatestPhotos();
}
