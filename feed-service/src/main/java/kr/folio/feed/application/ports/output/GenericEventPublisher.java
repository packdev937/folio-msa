package kr.folio.feed.application.ports.output;

public interface GenericEventPublisher<T> {

    void publish(T event);
}
