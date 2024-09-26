package kr.folio.feed.application.service;

import kr.folio.common.domain.core.event.ExternalEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedEventService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(ExternalEvent event) {
        log.info("Publishing event: {}", event);
        applicationEventPublisher.publishEvent(event);
    }
}
