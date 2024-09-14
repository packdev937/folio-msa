package kr.folio.infrastructure.kafka.consumer;

public interface KafkaConsumer<T> {

    void receive(String message);

}
