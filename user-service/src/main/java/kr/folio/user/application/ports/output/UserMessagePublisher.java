package kr.folio.user.application.ports.output;

public interface UserMessagePublisher<T> {

    void publish(T createUserEvent);
}
