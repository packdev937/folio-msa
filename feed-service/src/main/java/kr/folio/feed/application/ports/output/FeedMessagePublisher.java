package kr.folio.feed.application.ports.output;

public interface FeedMessagePublisher<T> {

    void publish(T event);
}
