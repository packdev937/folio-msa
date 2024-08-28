package kr.folio.photo.infrastructure.publisher;

import kr.folio.photo.application.ports.output.PhotoMessagePublisher;
import kr.folio.photo.domain.core.event.RetrievedPhotoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("RetrievedPhotoEventKafkaPublisher")
public class RetrievedPhotoEventKafkaPublisher implements
    PhotoMessagePublisher<RetrievedPhotoEvent> {

    @Override
    public void publish(RetrievedPhotoEvent event) {
        log.info("Publishing RetrievedPhotoEvent for photo id: {}", event.photo().getId());
    }
}
