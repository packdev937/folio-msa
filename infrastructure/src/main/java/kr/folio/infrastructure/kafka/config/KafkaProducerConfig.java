package kr.folio.infrastructure.kafka.config;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@RequiredArgsConstructor
@Configuration
public class KafkaProducerConfig<K, V> {

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, "all"); // 모든 복제본이 메시지 수신을 확인해야 성공
        config.put(ProducerConfig.RETRIES_CONFIG, 3); // 3번 재시도
        config.put(ProducerConfig.LINGER_MS_CONFIG, 10); // 10ms 동안 배치 전송 대기
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384); // 16KB 배치 크기
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // 멱등성 보장
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip"); // Gzip으로 압축 전송

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
