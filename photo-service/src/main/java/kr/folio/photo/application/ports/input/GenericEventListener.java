package kr.folio.photo.application.ports.input;

public interface GenericEventListener<T> {

    void listen(String message);

    void handleEvent(T event);
}
