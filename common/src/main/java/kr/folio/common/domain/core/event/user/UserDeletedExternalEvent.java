package kr.folio.common.domain.core.event.user;

import java.time.LocalDateTime;
import java.util.UUID;
import kr.folio.common.domain.core.event.ExternalEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserDeletedExternalEvent implements ExternalEvent {

    private final String eventType = "UserDeletedExternalEvent";
    private String userId;
    private UUID sagaId;
    private LocalDateTime createdAt;
}
