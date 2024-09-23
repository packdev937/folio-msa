package kr.folio.infrastructure.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Configuration
@Primary
@ConfigurationProperties(prefix = "kafka-producer")
public class KafkaProducerProperties {
    private String keySerializerClass = "org.apache.kafka.common.serialization.StringSerializer";
    private String valueSerializerClass = "org.apache.kafka.common.serialization.StringSerializer";
    private String compressionType = "gzip";
    private String acks = "all";
    private Integer batchSize = 16384;  // 기본값 설정
    private Integer batchSizeBoostFactor = 100;
    private Integer lingerMs = 5;
    private Integer requestTimeoutMs = 30000;
    private Integer retryCount = 3;
}
