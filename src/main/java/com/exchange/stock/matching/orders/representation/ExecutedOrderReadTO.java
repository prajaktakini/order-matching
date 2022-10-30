package com.exchange.stock.matching.orders.representation;

import com.exchange.stock.matching.orders.model.StockOrder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Data
@SuperBuilder
public class ExecutedOrderReadTO {

    private String id;

    private StockOrder sellOrder;

    private StockOrder buyOrder;

    private int quantity;

    private BigDecimal price;

    private Date createdAt;

    private Date updatedAt;

    private Long version;
}
