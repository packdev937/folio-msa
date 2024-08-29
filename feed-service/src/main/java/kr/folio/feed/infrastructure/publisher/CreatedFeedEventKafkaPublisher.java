package kr.folio.feed.infrastructure.publisher;

import kr.folio.feed.application.ports.output.FeedMessagePublisher;
import kr.folio.feed.domain.core.event.CreatedFeedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("createdFeedMessagePublisher")
public class CreatedFeedEventKafkaPublisher implements FeedMessagePublisher<CreatedFeedEvent> {

    @Override
    public void publish(CreatedFeedEvent event) {
        log.info("Publishing event: {}", event);
    }
}
