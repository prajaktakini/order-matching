package com.exchange.stock.matching.orders.kafka.consumer.events;

import com.exchange.stock.matching.orders.handler.OrderEventHandler;
import com.exchange.stock.matching.orders.util.ObjectMapperWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    private static final String TOPIC_PREFIX = "order.events.*";

    private OrderEventHandler orderEventHandler;

    @Autowired
    public KafkaConsumerService(final OrderEventHandler orderEventHandler) {
        this.orderEventHandler = orderEventHandler;
    }

    @KafkaListener(topicPattern = TOPIC_PREFIX,
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrderEvent(@Payload(required = false) String eventJson) {
        log.info("Consuming kafka order event: {}", eventJson);
        OrderEvent orderEvent;
        try {
            ObjectMapper objectMapper = ObjectMapperWrapper.getObjectMapper();
            orderEvent = objectMapper.readValue(eventJson, OrderEvent.class);
            orderEventHandler.handleOrderEvent(orderEvent);
        } catch (JsonProcessingException jpe) {
            log.error("Failed to parse event. Exception: ", jpe);

        } catch (Exception e) {
            log.error("Failed to process event due to exception:", e);
        }
    }
}
