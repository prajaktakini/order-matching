package com.exchange.stock.matching.orders.model;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BuyOrdersQueue {
    private PriorityQueue<Order> buyQueue;

    public BuyOrdersQueue() {
        this.buyQueue = new PriorityQueue<Order>(getComparator());
    }

    public PriorityQueue<Order> getOrders() {
        return buyQueue;
    }

    public void addQueue(Order order) {
        buyQueue.add(order);
    }


    private Comparator getComparator() {
        return new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {

                int timeComparator = order1.getOrderTime().compareTo(order2.getOrderTime());
                if (timeComparator == 0) {
                    // If time is same, sort based on Ids
                    return order1.getOrderId().compareTo(order2.getOrderId());
                } else {
                    return timeComparator;
                }
            }
        };
    }

}


