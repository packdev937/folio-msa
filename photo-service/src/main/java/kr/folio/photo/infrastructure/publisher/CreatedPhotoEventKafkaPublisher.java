package kr.folio.photo.infrastructure.publisher;

import kr.folio.photo.application.ports.output.PhotoMessagePublisher;
import kr.folio.photo.domain.core.event.CreatedPhotoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("CreatedPhotoEventKafkaPublisher")
public class CreatedPhotoEventKafkaPublisher implements PhotoMessagePublisher<CreatedPhotoEvent> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(CreatedPhotoEvent createUserEvent) {
        log.info("Publishing UserCreatedEvent for user id: {}", createUserEvent.photo().getId());

        kafkaTemplate.send("created_photo_event", createUserEvent);
    }
}
