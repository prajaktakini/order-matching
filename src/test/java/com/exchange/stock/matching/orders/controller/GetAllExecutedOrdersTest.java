package com.exchange.stock.matching.orders.controller;

import com.exchange.stock.matching.orders.model.ExecutedOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllExecutedOrdersTest extends BaseControllerTest {

    private List<ExecutedOrder> orderList = new ArrayList<>();

    void createData() {
        ExecutedOrder executedOrder1 = buildExecutedOrder(2);
        ExecutedOrder executedOrder2 = buildExecutedOrder(2);

        executedOrderRepository.saveAll(List.of(executedOrder1, executedOrder2));
        orderList.addAll(List.of(executedOrder1, executedOrder2));
    }


    void deleteData() {
        orderList.forEach(executedOrderRepository::delete);
    }

    @Test
    @SneakyThrows
    void getAllOrdersSucceeds() {
        createData();

        getMvc().perform(get(EXECUTED_ORDERS_BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<ExecutedOrder> orders = readTypeEntity(mvcResult, new TypeReference<>() {
                    });
                    Assertions.assertNotNull(orders);
                    Assertions.assertEquals(2, orders.size());
                    List<String> orderIds = orderList.stream().map(ExecutedOrder::getId).collect(Collectors.toList());
                    List<String> persistedEntityIds =
                           orders.stream().map(ExecutedOrder::getId).collect(Collectors.toList());
                    MatcherAssert.assertThat(orderIds, CoreMatchers.hasItems(persistedEntityIds.toArray(new String[]{})));
                });
        deleteData();
    }

    @Test
    @SneakyThrows
    void getAllOrdersForEmptyOrders() {
        //executedOrderRepository.deleteAll();

        getMvc().perform(get(EXECUTED_ORDERS_BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<ExecutedOrder> orders = readTypeEntity(mvcResult, new TypeReference<>() {
                    });
                    Assertions.assertNotNull(orders);
                    Assertions.assertEquals(0, orders.size());
                });
    }
}
