package kr.folio.photo.infrastructure.publisher;

import kr.folio.infrastructure.kafka.producer.GenericEventPublisher;

public class FakePhotoMessagePublisher implements GenericEventPublisher<Object> {

    @Override
    public void publish(Object event) {
        // do nothing
    }
}
