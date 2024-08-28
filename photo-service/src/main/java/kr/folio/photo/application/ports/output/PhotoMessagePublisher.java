package kr.folio.photo.application.ports.output;

public interface PhotoMessagePublisher<T> {

    void publish(T event);
}
