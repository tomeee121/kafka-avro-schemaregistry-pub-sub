package tb.kafka.avro.schemaregistry.config;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import tb.kafka.avro.schemaregistry.KafkaProperties;
import tb.kafka.avro.schemaregistry.KafkaSyncMessagePublisher;

import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@EnableKafka
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class KafkaProducerConfiguration {

    static final String SCHEMA_REGISTRY_URL_KEY = "schema.registry.url";
    KafkaProperties kafkaProperties;

    @Bean
    KafkaSyncMessagePublisher kafkaSyncPublisher() {
        return new KafkaSyncMessagePublisher(kafkaTemplate(producerFactory()));
    }

    @Bean("kafkaTemplate")
    public KafkaTemplate<String, SpecificRecord> kafkaTemplate(final ProducerFactory<String, SpecificRecord> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, SpecificRecord> producerFactory() {
        final var producerConfig = getProducerConfig();
        return new DefaultKafkaProducerFactory<>(producerConfig);
    }

    private Map<String, Object> getProducerConfig() {
        final Map<String, Object> properties = new HashMap<>();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(ProducerConfig.RETRIES_CONFIG, 1);
        properties.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, "5000");
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "5000");
        properties.put(SCHEMA_REGISTRY_URL_KEY, kafkaProperties.getSchemaRegistryUrl());

        return properties;
    }
}