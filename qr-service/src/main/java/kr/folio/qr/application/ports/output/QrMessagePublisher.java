package kr.folio.qr.application.ports.output;

public interface QrMessagePublisher<T> {

    void publish(T event);
}
