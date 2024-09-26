package kr.folio.feed.application.mapper;

import java.time.LocalDateTime;
import java.util.UUID;
import kr.folio.common.domain.core.event.photo.PhotoDeleteExternalEvent;
import org.springframework.stereotype.Component;

@Component
public class FeedEventMapper {

    public PhotoDeleteExternalEvent toPhotoDeleteExternalEvent(Long photoId) {
        return new PhotoDeleteExternalEvent(photoId, UUID.randomUUID(), LocalDateTime.now());
    }
}
