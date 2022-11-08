package com.exchange.stock.matching.orders.repository;

import com.exchange.stock.matching.orders.model.ExecutedOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class ExecutedOrderRepositoryTest {

    @Autowired
    private ExecutedOrderRepository repository;

    private ExecutedOrder testOrder;

    @BeforeEach
    public void setup() {
        ExecutedOrder executedOrder = buildExecutedOrderEntity();
        testOrder = repository.save(executedOrder);
    }

    @Test
    void findAll() {
        List<ExecutedOrder> list = repository.findAll();
        Assertions.assertEquals(list.size(), 1);
    }

    @Test
    void findById() {
        Optional<ExecutedOrder> order = repository.findById(testOrder.getId());
        assertTrue(order.isPresent());
        assertEquals(testOrder.getId(), order.get().getId());
    }

    private ExecutedOrder buildExecutedOrderEntity() {
        ExecutedOrder executedOrder = ExecutedOrder.builder()
                .id(UUID.randomUUID().toString())
                .quantity(1)
                .price(new BigDecimal(12))
                .build();
        return executedOrder;
    }
}
