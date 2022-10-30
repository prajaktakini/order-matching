package com.exchange.stock.matching.orders.mapper;

import com.exchange.stock.matching.orders.model.ExecutedOrder;
import com.exchange.stock.matching.orders.representation.ExecutedOrderReadTO;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;

import java.util.List;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class ExecutedOrderMapper {

    public abstract ExecutedOrderReadTO toReadTO(ExecutedOrder entity);

    public abstract List<ExecutedOrderReadTO> toReadTOs(List<ExecutedOrder> entities);
}
