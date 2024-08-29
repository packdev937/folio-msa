package kr.folio.photo.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.folio.photo.application.ports.output.PhotoRepository;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.infrastructure.annotation.Adapter;
import kr.folio.photo.persistence.entity.PhotoEntity;
import kr.folio.photo.persistence.mapper.PhotoPersistenceMapper;
import kr.folio.photo.persistence.repository.PhotoJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
public class PhotoPersistenceAdapter implements PhotoRepository {

    private final PhotoPersistenceMapper photoPersistenceMapper;
    private final PhotoJpaRepository photoJpaRepository;

    @Override
    public Photo createPhoto(Photo user) {
        return photoPersistenceMapper.toDomain(
            photoJpaRepository.save(photoPersistenceMapper.toEntity(user)));
    }

    @Override
    public Optional<Photo> findPhotoById(Long photoId) {
        return Optional.ofNullable(photoPersistenceMapper.toDomain(
            photoJpaRepository.findById(photoId)
	.orElse(null)
        ));
    }

    @Override
    public Photo updatePhoto(Photo photo) {
        return photoPersistenceMapper.toDomain(
            photoJpaRepository.save(photoPersistenceMapper.toEntity(photo)));
    }

    @Override
    public void deletePhotoById(Long photoId) {
        photoJpaRepository.deleteById(photoId);
    }

    @Override
    public List<Photo> findLatestPhotos() {
        return photoJpaRepository.findTop500ByOrderByUpdatedAt().stream()
            .map(photoPersistenceMapper::toDomain)
            .collect(Collectors.toList());
    }
}
