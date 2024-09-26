package kr.folio.common.domain.core.event.photo;

import java.time.LocalDateTime;
import java.util.UUID;
import kr.folio.common.domain.core.event.ExternalEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PhotoDeleteExternalEvent implements ExternalEvent {

    private final String eventType = "PhotoDeleteExternalEvent";
    private Long photoId;
    private UUID sagaId;
    private LocalDateTime createdAt;
}
