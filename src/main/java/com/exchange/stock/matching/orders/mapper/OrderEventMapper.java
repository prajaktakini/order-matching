package com.exchange.stock.matching.orders.mapper;

import com.exchange.stock.matching.orders.kafka.consumer.events.OrderEvent;
import com.exchange.stock.matching.orders.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class OrderEventMapper {

    public abstract Order toOrderEntity(OrderEvent orderEvent);
}
