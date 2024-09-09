package kr.folio.infrastructure.kafka.producer;

public interface GenericEventPublisher<T> {

    void publish(T event);
}
