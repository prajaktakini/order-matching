package com.exchange.stock.matching.orders.model;


import java.util.Comparator;
import java.util.PriorityQueue;

public class SellOrdersQueue {

    private PriorityQueue<Order> sellQueue;

    public SellOrdersQueue() {
        this.sellQueue = new PriorityQueue<Order>(getComparator());
    }

    public PriorityQueue<Order> getOrders() {
        return sellQueue;
    }

    public void addQueue(Order order) {
        sellQueue.add(order);
    }


    public static Comparator getComparator() {
        return new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                // Ascending order comparator
                int priceComparator = order1.getPrice().compareTo(order2.getPrice());

                if (priceComparator == 0) {
                    // If price is same, sort based on time
                    return order1.getOrderTime().compareTo(order2.getOrderTime());
                } else {
                    return priceComparator;
                }
            }
        };
    }

}


