package tb.kafka.avro.schemaregistry;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tb.kafka.avro.schemaregistry.config.OrderCreatedConst;

import static lombok.AccessLevel.PRIVATE;
import static tb.kafka.avro.schemaregistry.config.OrderCreatedConst.Listeners.MESSAGE_READ_LISTENER_CONTAINER_FACTORY;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Component
class MessageListener {

    private OrderCreatedFacade orderCreatedFacade;

    @KafkaListener(topics = OrderCreatedConst.Listeners.TOPIC, groupId = "group-1", containerFactory = MESSAGE_READ_LISTENER_CONTAINER_FACTORY)
    public void handleMessage(value_orders_event_record event) {
        log.info("[READ MESSAGE] message {}", event.toString());
        orderCreatedFacade.consume(event);
    }
}