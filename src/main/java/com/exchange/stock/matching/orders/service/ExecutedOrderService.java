package com.exchange.stock.matching.orders.service;

import com.exchange.stock.matching.orders.error.ErrorDetailBuilder;
import com.exchange.stock.matching.orders.error.OrderMatchingErrorCode;
import com.exchange.stock.matching.orders.error.OrderMatchingException;
import com.exchange.stock.matching.orders.model.ExecutedOrder;
import com.exchange.stock.matching.orders.repository.ExecutedOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ExecutedOrderService {

    private ExecutedOrderRepository executedOrderRepository;

    @Autowired
    public ExecutedOrderService(final ExecutedOrderRepository executedOrderRepository) {
        this.executedOrderRepository = executedOrderRepository;
    }

    public List<ExecutedOrder> getAllOrders() {
        return executedOrderRepository.findAll();
    }

    public ExecutedOrder getOrder(final String id) {
        Optional<ExecutedOrder> mayBeOrder = executedOrderRepository.findById(id);
        if (mayBeOrder.isPresent()) {
            return mayBeOrder.get();
        } else {
            throw new OrderMatchingException(
                    ErrorDetailBuilder.errorBuilder(OrderMatchingErrorCode.RECORD_NOT_FOUND)
                            .withDetail(String.format("Order with id %s does not exist", id)).build())
                    .withHttpStatus(HttpStatus.NOT_FOUND)
                    .logError(log);
        }
    }

    public void saveOrder(ExecutedOrder order) {
        executedOrderRepository.save(order);
    }
}
