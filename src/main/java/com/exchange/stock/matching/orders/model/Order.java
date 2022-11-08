package com.exchange.stock.matching.orders.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class Order {

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
