package kr.folio.photo.application.ports.output;


import kr.folio.photo.domain.core.entity.Photo;

public interface PhotoRepository {

    Photo createPhoto(Photo user);

    Photo findPhotoById(Long photoId);
}
