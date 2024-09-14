package kr.folio.infrastructure.outbox;

public interface OutboxScheduler {

    void processOutboxEvent();
}
