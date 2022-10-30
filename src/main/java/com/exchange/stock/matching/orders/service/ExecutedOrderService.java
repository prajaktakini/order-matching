package com.exchange.stock.matching.orders.service;

import com.exchange.stock.matching.orders.model.ExecutedOrder;
import com.exchange.stock.matching.orders.repository.ExecutedOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
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
            // TODO prajakta: throw exception
            return null;
        }
    }
}
