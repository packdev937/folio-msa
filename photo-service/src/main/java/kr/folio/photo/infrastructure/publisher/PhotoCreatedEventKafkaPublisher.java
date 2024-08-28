package kr.folio.photo.infrastructure.publisher;

import kr.folio.photo.application.ports.output.PhotoMessagePublisher;
import kr.folio.photo.domain.core.event.PhotoCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("photoCreatedEventKafkaPublisher")
public class PhotoCreatedEventKafkaPublisher implements PhotoMessagePublisher<PhotoCreatedEvent> {

    @Override
    public void publish(PhotoCreatedEvent createUserEvent) {
        log.info("Publishing UserCreatedEvent for user id: {}", createUserEvent.photo().getId());
    }
}
