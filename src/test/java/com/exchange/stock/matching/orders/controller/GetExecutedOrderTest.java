package com.exchange.stock.matching.orders.controller;

import com.exchange.stock.matching.orders.error.OrderMatchingErrorCode;
import com.exchange.stock.matching.orders.error.OrderMatchingExceptionHandler.ErrorResponse;
import com.exchange.stock.matching.orders.model.ExecutedOrder;
import lombok.SneakyThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetExecutedOrderTest extends BaseControllerTest {

    private ExecutedOrder executedOrder;

    @BeforeEach
    void createData() {
        executedOrder = buildExecutedOrder(2);
        executedOrderRepository.save(executedOrder);
    }

    @AfterEach
    void deleteData() {
        executedOrderRepository.delete(executedOrder);
    }

    @Test
    @SneakyThrows
    void getOrderSucceeds() {

        getMvc().perform(get(EXECUTED_ORDERS_BASE_PATH + "/" + executedOrder.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    ExecutedOrder order = readEntity(mvcResult, ExecutedOrder.class);
                    Assertions.assertNotNull(order);
                    Assertions.assertEquals(executedOrder.getId(), order.getId());
                    Assertions.assertEquals(executedOrder.getQuantity(), order.getQuantity());
                });
    }

    @Test
    @SneakyThrows
    void getOrderThrowsNotFoundException() {

        getMvc().perform(get(EXECUTED_ORDERS_BASE_PATH + "/45566")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> {
                    ErrorResponse errorResponse = readEntity(mvcResult, ErrorResponse.class);
                    Assertions.assertEquals(OrderMatchingErrorCode.RECORD_NOT_FOUND.name(), errorResponse.getErrors().get(0).getCode());
                });
    }


}
