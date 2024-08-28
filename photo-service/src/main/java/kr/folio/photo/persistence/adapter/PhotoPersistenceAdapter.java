package kr.folio.photo.persistence.adapter;

import kr.folio.photo.application.ports.output.PhotoRepository;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.infrastructure.annotation.Adapter;
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
}
