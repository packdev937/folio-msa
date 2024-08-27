package kr.folio.user.domain.service;

import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.UserCreatedEvent;
import kr.folio.user.presentation.dto.response.ValidateUserResponse;

public interface UserDomainUseCase {

    UserCreatedEvent validateUser(User user);
}
