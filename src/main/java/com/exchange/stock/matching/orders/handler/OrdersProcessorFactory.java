package com.exchange.stock.matching.orders.handler;

import com.exchange.stock.matching.orders.error.ErrorDetailBuilder;
import com.exchange.stock.matching.orders.error.OrderMatchingErrorCode;
import com.exchange.stock.matching.orders.error.OrderMatchingException;
import com.exchange.stock.matching.orders.model.OrderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrdersProcessorFactory {

    private final BuyOrdersProcessor buyOrdersProcessor;

    private final SellOrdersProcessor sellOrdersProcessor;

    public OrdersProcessor getOrderProcessor(OrderType type) {
        switch (type) {
            case BUY:
                return buyOrdersProcessor;
            case SELL:
                return sellOrdersProcessor;
            default:
                throw new OrderMatchingException(
                        ErrorDetailBuilder.errorBuilder(OrderMatchingErrorCode.INVALID_ORDER_TYPE).build())
                        .withHttpStatus(HttpStatus.BAD_REQUEST)
                        .logError(log);
        }
    }
}
