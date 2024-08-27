package kr.folio.user.persistence.repository;

import java.util.Optional;
import kr.folio.user.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByNickname(String nickname);
}
