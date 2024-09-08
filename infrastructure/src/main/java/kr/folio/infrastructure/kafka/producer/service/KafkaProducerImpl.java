package kr.folio.infrastructure.kafka.producer.service;

import jakarta.annotation.PreDestroy;
import kr.folio.infrastructure.kafka.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducerImpl<K extends Serializable, V extends Serializable> implements
    KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    @Override
    public void send(String topicName, K key, V message) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {
            kafkaTemplate.send(topicName, key, message);
        } catch (KafkaException kafkaException) {
            log.error("Error on kafka producer with key: {}, message: {} and exception: {}", key,
	message, kafkaException.getMessage());
        }
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
