package kr.folio.user.infrastructure.publisher;

import kr.folio.user.application.ports.output.UserMessagePublisher;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.DomainEvent;
import kr.folio.user.domain.core.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("userCreateEventKafkaPublisher")
public class UserCreateEventKafkaPublisher implements UserMessagePublisher<UserCreatedEvent> {

    @Override
    public void publish(UserCreatedEvent createUserEvent) {
        log.info("Publishing UserCreatedEvent for user id: {}", createUserEvent.user().getId());
    }
}
