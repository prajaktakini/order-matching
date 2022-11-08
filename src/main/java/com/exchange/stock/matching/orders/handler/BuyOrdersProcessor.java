package com.exchange.stock.matching.orders.handler;


import com.exchange.stock.matching.orders.model.BuyOrdersQueue;
import com.exchange.stock.matching.orders.model.Company.Symbol;
import com.exchange.stock.matching.orders.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BuyOrdersProcessor extends OrdersProcessor {

    private Map<Symbol, BuyOrdersQueue> buyOrders;

    public BuyOrdersProcessor() {
        this.buyOrders = new HashMap<>();
    }

    public Map<Symbol, BuyOrdersQueue> getBuyOrders() {
        return buyOrders;
    }

    @Override
    public void processOrder(final Order buyOrder) {
        BuyOrdersQueue buyOrdersQueue = buyOrders.getOrDefault(buyOrder.getCompany().getSymbol(), new BuyOrdersQueue());
        buyOrdersQueue.addQueue(buyOrder);
        buyOrders.put(buyOrder.getCompany().getSymbol(), buyOrdersQueue);
    }
}
