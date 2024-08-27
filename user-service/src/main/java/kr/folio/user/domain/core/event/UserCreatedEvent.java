package kr.folio.user.domain.core.event;

import java.time.ZonedDateTime;
import kr.folio.user.domain.core.entity.User;

public record UserCreatedEvent(User user, ZonedDateTime createdAt) implements DomainEvent<User> {

}
