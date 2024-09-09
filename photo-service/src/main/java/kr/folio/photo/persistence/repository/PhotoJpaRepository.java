package kr.folio.photo.persistence.repository;

import java.util.Arrays;
import java.util.List;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.persistence.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhotoJpaRepository extends JpaRepository<PhotoEntity, Long> {
    List<PhotoEntity> findTop500ByOrderByUpdatedAt();

    @Query("SELECT p FROM PhotoEntity p WHERE :userId MEMBER OF p.taggedUserIds")
    List<PhotoEntity> findPhotosByTaggedUserIdsContains(String userId);
}
