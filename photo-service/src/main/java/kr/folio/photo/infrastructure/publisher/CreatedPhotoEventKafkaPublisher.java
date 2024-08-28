package kr.folio.photo.infrastructure.publisher;

import kr.folio.photo.application.ports.output.PhotoMessagePublisher;
import kr.folio.photo.domain.core.event.CreatedPhotoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CreatedPhotoEventKafkaPublisher")
public class CreatedPhotoEventKafkaPublisher implements PhotoMessagePublisher<CreatedPhotoEvent> {

    @Override
    public void publish(CreatedPhotoEvent createUserEvent) {
        log.info("Publishing UserCreatedEvent for user id: {}", createUserEvent.photo().getId());
    }
}
