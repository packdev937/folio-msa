package kr.folio.user.application.mapper;

import java.time.LocalDateTime;
import java.util.UUID;
import kr.folio.common.domain.core.event.user.UserDeletedExternalEvent;
import kr.folio.user.domain.core.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserEventMapper {

    public UserDeletedExternalEvent toDeletedExternalEvent(User user) {
        return new UserDeletedExternalEvent(user.getId(), UUID.randomUUID(), LocalDateTime.now());
    }
}
