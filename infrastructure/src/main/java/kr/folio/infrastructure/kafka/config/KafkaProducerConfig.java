package kr.folio.infrastructure.kafka.config;

import java.util.HashMap;
import java.util.Map;
import kr.folio.infrastructure.kafka.config.data.KafkaProducerProperties;
import kr.folio.infrastructure.kafka.config.data.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({KafkaProducerProperties.class, KafkaProperties.class})
public class KafkaProducerConfig<K, V> {

    private final KafkaProperties kafkaProperties;
    private final KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public ProducerFactory<K, V> producerConfig() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(kafkaProperties.getSchemaRegistryUrlKey(),
            kafkaProperties.getSchemaRegistryUrl());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            kafkaProducerProperties.getKeySerializerClass());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            kafkaProducerProperties.getValueSerializerClass());
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerProperties.getBatchSize() *
            kafkaProducerProperties.getBatchSizeBoostFactor());
        config.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerProperties.getLingerMs());
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,
            kafkaProducerProperties.getCompressionType());
        config.put(ProducerConfig.ACKS_CONFIG, kafkaProducerProperties.getAcks());
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,
            kafkaProducerProperties.getRequestTimeoutMs());
        config.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerProperties.getRetryCount());

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate(
        ProducerFactory<K, V> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
