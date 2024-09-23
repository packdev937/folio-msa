package kr.folio.infrastructure.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {
    private String bootstrapServers;
    private Integer numOfPartitions;
    private Short replicationFactor;
}
