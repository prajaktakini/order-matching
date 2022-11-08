package com.exchange.stock.matching.orders.kafka.consumer.events;

import com.exchange.stock.matching.orders.model.Company;
import com.exchange.stock.matching.orders.model.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    @NonNull
    private String orderId;

    @NonNull
    private LocalTime orderTime;

    @NonNull
    private OrderType orderType;

    private int quantity;

    @NonNull
    private Company company;

    @NonNull
    private BigDecimal price;

}
