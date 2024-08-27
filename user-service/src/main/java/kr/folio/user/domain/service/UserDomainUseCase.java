package kr.folio.user.domain.service;

import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.UserCreatedEvent;

public interface UserDomainUseCase {

    UserCreatedEvent validateUser(User user);
}
