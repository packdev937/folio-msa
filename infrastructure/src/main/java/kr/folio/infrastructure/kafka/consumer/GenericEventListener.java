package kr.folio.infrastructure.kafka.consumer;

public interface GenericEventListener<T> {
    void listen(String message);

    void handleEvent(T event);

}
