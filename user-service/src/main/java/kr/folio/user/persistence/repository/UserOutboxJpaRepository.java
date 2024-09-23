package kr.folio.user.persistence.repository;

import kr.folio.user.application.ports.output.UserOutboxRepository;
import kr.folio.user.persistence.entity.UserOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOutboxJpaRepository extends UserOutboxRepository,
    JpaRepository<UserOutboxEntity, Long> {

}
