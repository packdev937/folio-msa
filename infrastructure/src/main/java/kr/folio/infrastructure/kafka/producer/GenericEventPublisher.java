package kr.folio.infrastructure.kafka.producer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kr.folio.infrastructure.outbox.OutboxStatus;

public interface GenericEventPublisher<T> {

    void publish(T event, BiConsumer<T, OutboxStatus> outboxCallback);
}
