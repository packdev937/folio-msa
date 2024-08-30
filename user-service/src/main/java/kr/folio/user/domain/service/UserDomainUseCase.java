package kr.folio.user.domain.service;

import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.CreatedUserEvent;

public interface UserDomainUseCase {

    CreatedUserEvent validateUser(User user);
}
