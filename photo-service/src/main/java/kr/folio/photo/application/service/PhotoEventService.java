package kr.folio.photo.application.service;

import kr.folio.photo.domain.core.event.PhotoExternalEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PhotoEventService {

    private final ApplicationEventPublisher eventPublisher;

    public void publishEvent(PhotoExternalEvent event) {
        log.info("Publishing event : {} at PhotoEventService", event);
        eventPublisher.publishEvent(event);
    }
}
