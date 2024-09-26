package kr.folio.user.persistence.repository;

import java.util.Optional;
import kr.folio.user.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByNickname(String nickname);
    Optional<UserEntity> findByFolioId(String folioId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserEntity u WHERE u.folioId = :folioId")
    void deleteByFolioId(@Param("folioId") String folioId);
}
