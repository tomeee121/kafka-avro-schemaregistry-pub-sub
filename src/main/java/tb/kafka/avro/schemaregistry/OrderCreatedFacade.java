package tb.kafka.avro.schemaregistry;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderCreatedFacade {

    public void consume(value_orders_event_record event) {
        System.out.println("deserialized message by avro: " + event);
    }
}
