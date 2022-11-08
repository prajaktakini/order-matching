package com.exchange.stock.matching.orders.handler;


import com.exchange.stock.matching.orders.model.Company.Symbol;
import com.exchange.stock.matching.orders.model.Order;
import com.exchange.stock.matching.orders.model.SellOrdersQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SellOrdersProcessor extends OrdersProcessor {

    private Map<Symbol, SellOrdersQueue> sellOrders;

    public SellOrdersProcessor() {
        this.sellOrders = new HashMap<>();
    }

    public Map<Symbol, SellOrdersQueue> getSellOrders() {
        return sellOrders;
    }

    @Override
    public void processOrder(final Order sellOrder) {
        SellOrdersQueue sellOrdersQueue = sellOrders.getOrDefault(sellOrder.getCompany().getSymbol(), new SellOrdersQueue());
        sellOrdersQueue.addQueue(sellOrder);
        sellOrders.put(sellOrder.getCompany().getSymbol(), sellOrdersQueue);
    }
}
