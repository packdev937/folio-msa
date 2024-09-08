package kr.folio.infrastructure.kafka.config;

import java.util.HashMap;
import java.util.Map;
import kr.folio.infrastructure.kafka.config.data.KafkaConsumerProperties;
import kr.folio.infrastructure.kafka.config.data.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({KafkaConsumerProperties.class, KafkaProperties.class})
public class KafkaConsumerConfig<K, V> {

    private final KafkaProperties kafkaProperties;
    private final KafkaConsumerProperties kafkaConsumerProperties;

    @Bean
    public ConsumerFactory<K, V> consumerConfigs() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            kafkaConsumerProperties.getKeyDeserializer());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            kafkaConsumerProperties.getValueDeserializer());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
            kafkaConsumerProperties.getAutoOffsetReset());
        config.put(kafkaProperties.getSchemaRegistryUrlKey(),
            kafkaProperties.getSchemaRegistryUrl());
        config.put(kafkaConsumerProperties.getSpecificAvroReaderKey(),
            kafkaConsumerProperties.getSpecificAvroReader());
        config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,
            kafkaConsumerProperties.getSessionTimeoutMs());
        config.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,
            kafkaConsumerProperties.getHeartbeatIntervalMs());
        config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,
            kafkaConsumerProperties.getMaxPollIntervalMs());
        config.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,
            kafkaConsumerProperties.getMaxPartitionFetchBytesDefault() *
	kafkaConsumerProperties.getMaxPartitionFetchBytesBoostFactor());
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
            kafkaConsumerProperties.getMaxPollRecords());

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<K, V> kafkaListenerContainerFactory(
        ConsumerFactory<K, V> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(kafkaConsumerProperties.getBatchListener());
        factory.setConcurrency(kafkaConsumerProperties.getConcurrencyLevel());
        factory.setAutoStartup(kafkaConsumerProperties.getAutoStartup());
        factory.getContainerProperties().setPollTimeout(kafkaConsumerProperties.getPollTimeoutMs());

        return factory;
    }
}
