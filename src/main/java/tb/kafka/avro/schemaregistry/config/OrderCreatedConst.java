package tb.kafka.avro.schemaregistry.config;

public interface OrderCreatedConst {
    interface Listeners {
        String MESSAGE_READ_LISTENER_CONTAINER_FACTORY = "kafka_listener_factory";
        String TOPIC = "orders";
    }
}
