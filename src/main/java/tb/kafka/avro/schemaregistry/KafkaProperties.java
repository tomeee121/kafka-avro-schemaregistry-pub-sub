package tb.kafka.avro.schemaregistry;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class KafkaProperties {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.topic.orders}")
    private String topicName;

    @Value("${spring.kafka.producer.schema.registry.url}")
    private String schemaRegistryUrl;
}
