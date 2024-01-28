package tb.kafka.avro.schemaregistry;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tb.kafka.avro.schemaregistry.config.OrderCreatedConst;

@SpringBootApplication
public class KafkaAvroSchemaRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaAvroSchemaRegistryApplication.class, args);
    }

    @Bean
    CommandLineRunner run(KafkaSyncMessagePublisher kafkaSyncMessagePublisher) {
        return args -> {
            for (int i = 0; i < 5; i++) {
                value_orders_event_record record = new value_orders_event_record("order_id -> " + i,
                        "customer_id -> " + i, "supplier_id -> " + i, 5);
                kafkaSyncMessagePublisher.publish(OrderCreatedConst.Listeners.TOPIC, record, "key");
            }
        };
    }

}
