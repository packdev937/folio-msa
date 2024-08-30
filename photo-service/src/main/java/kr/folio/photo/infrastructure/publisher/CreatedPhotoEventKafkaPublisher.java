package kr.folio.photo.infrastructure.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(CreatedPhotoEvent createdPhotoEvent) {
        try {
            String eventJson = objectMapper.writeValueAsString(createdPhotoEvent);
            kafkaTemplate.send("created_photo_topic", eventJson);

            log.info("Publishing CreatedPhotoEvent: {}", eventJson);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize CreatedPhotoEvent to JSON", e);
        }
    }
}
