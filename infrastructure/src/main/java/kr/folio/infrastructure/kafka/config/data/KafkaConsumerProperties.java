package kr.folio.infrastructure.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "kafka-consumer")
public class KafkaConsumerProperties {
    private String keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
    private String valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
    private String autoOffsetReset = "earliest";
    private Boolean batchListener = true;
    private Boolean autoStartup = true;
    private Integer concurrencyLevel =3;
    private Integer sessionTimeoutMs = 10000;
    private Integer heartbeatIntervalMs = 3000;
    private Integer maxPollIntervalMs = 300000;
    private Integer maxPollRecords = 500;
    private Integer maxPartitionFetchBytesDefault = 1048576;
    private Integer maxPartitionFetchBytesBoostFactor = 1;
    private Long pollTimeoutMs = 150L;
}
