package kr.folio.user.application.service;

import kr.folio.common.domain.core.event.ExternalEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserEventService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(ExternalEvent event) {
        log.info("Publishing event: {} at {}", event.getEventType(), this.getClass().getSimpleName());applicationEventPublisher.publishEvent(event);
    }
}
