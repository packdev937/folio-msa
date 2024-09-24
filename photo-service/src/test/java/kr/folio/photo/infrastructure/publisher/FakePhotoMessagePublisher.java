package kr.folio.photo.infrastructure.publisher;

import java.util.function.BiConsumer;
import kr.folio.infrastructure.kafka.producer.GenericEventPublisher;
import kr.folio.infrastructure.outbox.OutboxStatus;

public class FakePhotoMessagePublisher implements GenericEventPublisher<Object> {


    @Override
    public void publish(Object event, BiConsumer<Object, OutboxStatus> outboxCallback) {
        return;
    }
}
