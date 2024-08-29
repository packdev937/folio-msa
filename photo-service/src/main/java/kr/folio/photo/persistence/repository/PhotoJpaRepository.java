package kr.folio.photo.persistence.repository;

import java.util.List;
import kr.folio.photo.persistence.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoJpaRepository extends JpaRepository<PhotoEntity, Long> {
    List<PhotoEntity> findTop500ByOrderByUpdatedAt();
}
