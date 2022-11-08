package com.exchange.stock.matching.orders.handler;

import com.exchange.stock.matching.orders.kafka.consumer.events.OrderEvent;
import com.exchange.stock.matching.orders.mapper.OrderEventMapper;
import com.exchange.stock.matching.orders.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OrderEventHandler {


    private OrderEventMapper orderEventMapper;

    private OrdersProcessorFactory ordersProcessorFactory;

    @Autowired
    public OrderEventHandler(final OrderEventMapper orderEventMapper,
                             final OrdersProcessorFactory ordersProcessorFactory) {

        this.orderEventMapper = orderEventMapper;
        this.ordersProcessorFactory = ordersProcessorFactory;
    }


    public void handleOrderEvent(OrderEvent orderEvent) {
        log.info("Handling {} event for order {}", orderEvent.getOrderType(), orderEvent);
        Order order = orderEventMapper.toOrderEntity(orderEvent);
        addOrder(order);
    }

    private void addOrder(Order order) {
        OrdersProcessor ordersProcessor = ordersProcessorFactory.getOrderProcessor(order.getOrderType());
        ordersProcessor.processOrder(order);
    }

}
