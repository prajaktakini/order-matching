package com.exchange.stock.matching.orders.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Date;

@Data
@SuperBuilder
@Table
public class ExecutedOrder {

    @PrimaryKey
    private String id;

    private StockOrder sellOrder;

    private StockOrder buyOrder;

    private int quantity;

    private BigDecimal price;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @Version
    private Long version;

}
