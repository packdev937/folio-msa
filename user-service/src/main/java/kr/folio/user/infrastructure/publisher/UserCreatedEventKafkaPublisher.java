package kr.folio.user.infrastructure.publisher;

import kr.folio.user.application.ports.output.UserMessagePublisher;
import kr.folio.user.domain.core.event.CreatedUserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("userCreateEventKafkaPublisher")
public class UserCreatedEventKafkaPublisher implements UserMessagePublisher<CreatedUserEvent> {

    @Override
    public void publish(CreatedUserEvent createUserEvent) {
        log.info("Publishing CreatedUserEvent for user id: {}", createUserEvent.user().getId());
    }
}
