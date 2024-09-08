package kr.folio.feed.infrastructure.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.folio.feed.application.ports.output.GenericEventPublisher;
import kr.folio.feed.domain.core.event.DeleteFeedEvent;
import kr.folio.infrastructure.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeleteFeedEventKafkaPublisher implements GenericEventPublisher<DeleteFeedEvent> {

    private final KafkaProducer<String, String> kafkaProducer;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(DeleteFeedEvent event) {
        log.info("Publishing delete feed event for photoId: {}", event.deleteFeedEventDTO().photoId());

        try {
            String eventJson = objectMapper.writeValueAsString(event);

            kafkaProducer.send("feed-deleted-event", null, eventJson);

            log.info("Successfully published DeleteFeedEvent: {}", eventJson);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error("Failed to serialize DeleteFeedEvent to JSON", jsonProcessingException);
        }
    }
}
