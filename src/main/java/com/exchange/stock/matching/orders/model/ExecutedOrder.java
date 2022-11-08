package com.exchange.stock.matching.orders.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@Document(collection = "executed-orders")
@NoArgsConstructor
@AllArgsConstructor
public class ExecutedOrder {

    @Id
    private String id;

    private Order sellOrder;

    private Order buyOrder;

    private int quantity;

    private BigDecimal price;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @Version
    private Long version;

}
