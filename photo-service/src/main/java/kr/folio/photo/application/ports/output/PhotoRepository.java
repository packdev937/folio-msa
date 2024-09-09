package kr.folio.photo.application.ports.output;


import java.util.List;
import java.util.Optional;
import kr.folio.photo.domain.core.entity.Photo;

public interface PhotoRepository {

    Photo save(Photo user);

    Optional<Photo> findPhotoById(Long photoId);

    void deletePhotoById(Long photoId);

    List<Photo> findLatestPhotos();
    List<Photo> findAll();

    List<Photo> findPhotosByTaggedUserId(String userId);
    void saveAll(List<Photo> photos);
}
