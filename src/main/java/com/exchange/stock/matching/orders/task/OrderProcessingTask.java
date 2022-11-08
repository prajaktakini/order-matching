package com.exchange.stock.matching.orders.task;

import com.exchange.stock.matching.orders.handler.BuyOrdersProcessor;
import com.exchange.stock.matching.orders.handler.SellOrdersProcessor;
import com.exchange.stock.matching.orders.model.BuyOrdersQueue;
import com.exchange.stock.matching.orders.model.Company.Symbol;
import com.exchange.stock.matching.orders.model.ExecutedOrder;
import com.exchange.stock.matching.orders.model.Order;
import com.exchange.stock.matching.orders.model.SellOrdersQueue;
import com.exchange.stock.matching.orders.service.ExecutedOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderProcessingTask  {

    private ScheduledExecutorService scheduledExecutorService;

    private ScheduledFuture<?> scheduledFuture;

    private final BuyOrdersProcessor buyOrdersProcessor;

    private final SellOrdersProcessor sellOrdersProcessor;

    private final ExecutedOrderService executedOrderService;

    @Autowired
    public OrderProcessingTask(final BuyOrdersProcessor buyOrdersProcessor,
                               final SellOrdersProcessor sellOrdersProcessor,
                               final ExecutedOrderService executedOrderService) {
        this.buyOrdersProcessor = buyOrdersProcessor;
        this.sellOrdersProcessor = sellOrdersProcessor;
        this.executedOrderService = executedOrderService;
    }

    private static long initialDelaySecs = 20;

    private static long runIntervalSecs = 20;

    @PostConstruct
    public void configureOrderProcessingScheduledExecutorService() {

        Runnable orderProcessingTask = () -> {
            log.info("Order processing task started!.......");
            processOrder();
        };

        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newScheduledThreadPool(4);

            // Start the scheduler and schedule the interval at which they poll.
            log.info("Scheduling order processing task to run at interval of {}s with initial delay of {}s", runIntervalSecs,
                    initialDelaySecs);
            scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(
                    orderProcessingTask,
                    initialDelaySecs,
                    runIntervalSecs,
                    TimeUnit.SECONDS);
        }
    }

    public synchronized void processOrder() {
        Map<Symbol, BuyOrdersQueue> buyOrdersMap = buyOrdersProcessor.getBuyOrders();
        Map<Symbol, SellOrdersQueue> sellOrdersMap = sellOrdersProcessor.getSellOrders();

        if (CollectionUtils.isEmpty(buyOrdersMap) || CollectionUtils.isEmpty(sellOrdersMap)) {
            log.warn("No orders to be processed, returning...");
            return;
        }

        buyOrdersMap.forEach((company, orders)  -> {
            BuyOrdersQueue buyOrdersQueue = orders;

            if (buyOrdersQueue == null || buyOrdersQueue.getOrders().isEmpty()) {
                log.info("No buy orders to be processed, returning...");
                return;
            }

            SellOrdersQueue sellOrdersQueue = sellOrdersMap.get(company);
            if (sellOrdersQueue == null || sellOrdersQueue.getOrders().isEmpty()) {
                log.info("No sell orders to match. Returning...");
                return;
            }

            PriorityQueue<Order> sellOrders = sellOrdersQueue.getOrders();

            buyOrdersQueue.getOrders().forEach(order -> {
                executeOrders(sellOrders, order, buyOrdersQueue);
            });
        });

    }

    private void executeOrders(final PriorityQueue<Order> sellOrders,
                               final Order buyOrder,
                               final BuyOrdersQueue buyOrdersQueue) {
        // Execute when sell orders exist
        while(buyOrder.getQuantity() > 0 && sellOrders.size() > 0) {
            log.info("Executing buy order {}", buyOrder.getOrderId());
            Order sellOrder = sellOrders.peek();
            // Find all sell orders where sell order price <= buy order price
            if (buyOrder.getPrice().compareTo(sellOrder.getPrice()) >= 0) {

                int quantity;
                int remainingSellQuantity = sellOrder.getQuantity() - buyOrder.getQuantity();

                if (remainingSellQuantity > 0) {
                    quantity = buyOrder.getQuantity();
                    sellOrder.setQuantity(remainingSellQuantity);

                    // buy order fulfilled as a whole
                    buyOrder.setQuantity(0);
                } else {

                    // Because sell order matched completely with buy order, remove it from sell orders queue
                    sellOrders.poll();

                    // Partial or full order fulfillment
                    int remainingBuyQuantity = buyOrder.getQuantity() - sellOrder.getQuantity();

                    quantity = sellOrder.getQuantity();
                    buyOrder.setQuantity(remainingBuyQuantity);
                }

                ExecutedOrder executedOrder = ExecutedOrder.builder()
                        .sellOrder(sellOrder)
                        .buyOrder(buyOrder)
                        .price(sellOrder.getPrice())
                        .quantity(quantity)
                        .build();
                performPostOrderExecutionActions(executedOrder);

            } else {
                // We didn't find any matching sell order for given buy order, end the search
                log.info("No matching sell order found for buy order {}", buyOrder.getOrderId());
                break;
            }
        }

    }

    private void performPostOrderExecutionActions(ExecutedOrder executedOrder) {
        executedOrderService.saveOrder(executedOrder);
        writeToCLI(executedOrder);
    }

    public void writeToCLI(ExecutedOrder order) {
        String output = String.format("%s %d %.2f %s", order.getSellOrder().getOrderId(), order.getQuantity(),
                order.getPrice(), order.getBuyOrder().getOrderId());
        System.out.println(output);
    }

}
