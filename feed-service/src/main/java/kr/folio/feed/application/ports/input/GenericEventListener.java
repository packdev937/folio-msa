package kr.folio.feed.application.ports.input;

public interface GenericEventListener <T> {

    void listen(String message);

    void handleEvent(T event);
}
