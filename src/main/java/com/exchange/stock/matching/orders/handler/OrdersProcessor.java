package com.exchange.stock.matching.orders.handler;

import com.exchange.stock.matching.orders.model.Order;


public abstract class OrdersProcessor {

    public abstract void processOrder(Order order);

}
