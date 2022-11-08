package com.exchange.stock.matching.orders.service;

import com.exchange.stock.matching.orders.model.ExecutedOrder;
import com.exchange.stock.matching.orders.repository.ExecutedOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ExecutedOrderServiceTest {

    @MockBean
    private ExecutedOrderRepository executedOrderRepository;

    private ExecutedOrderService executedOrderService;

    @BeforeEach
    public void setup() {
        executedOrderService = new ExecutedOrderService(executedOrderRepository);
    }


    @Test
    public void testSaveExecutedOrders() {
        ExecutedOrder executedOrder = buildExecutedOrderEntity();
        executedOrderService.saveOrder(executedOrder);

        when(executedOrderRepository.save(any())).thenReturn(executedOrder);
        verify(executedOrderRepository, times(1)).save(any());
    }

    @Test
    public void testFindAllOrders() {
        ExecutedOrder executedOrder1 = buildExecutedOrderEntity();
        ExecutedOrder executedOrder2 = buildExecutedOrderEntity();

        when(executedOrderRepository.findAll()).thenReturn(Arrays.asList(executedOrder1, executedOrder2));

        List<ExecutedOrder> orders = executedOrderService.getAllOrders();
        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(executedOrderRepository, times(1)).findAll();

    }

    @Test
    public void testFindOneOrder() {
        ExecutedOrder executedOrder1 = buildExecutedOrderEntity();

        when(executedOrderRepository.findById(any())).thenReturn(Optional.of(executedOrder1));

        ExecutedOrder order = executedOrderService.getOrder(any());
        assertNotNull(order);
        verify(executedOrderRepository, times(1)).findById(any());

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
