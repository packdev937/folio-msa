package kr.folio.user.infrastructure.publisher;

import kr.folio.user.application.ports.output.UserMessagePublisher;
import kr.folio.user.domain.core.entity.User;
import kr.folio.user.domain.core.event.DomainEvent;
import kr.folio.user.domain.core.event.UserCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserCreateEventKafkaPublisher implements UserMessagePublisher<UserCreatedEvent> {

    @Override
    public void publish(UserCreatedEvent createUserEvent) {

    }
}
