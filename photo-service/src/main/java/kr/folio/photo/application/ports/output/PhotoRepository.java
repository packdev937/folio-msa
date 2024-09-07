package kr.folio.photo.application.ports.output;


import java.util.List;
import java.util.Optional;
import kr.folio.photo.domain.core.entity.Photo;

public interface PhotoRepository {

    Photo save(Photo user);

    Optional<Photo> findPhotoById(Long photoId);

    void deletePhotoById(Long photoId);

    List<Photo> findLatestPhotos();
}
