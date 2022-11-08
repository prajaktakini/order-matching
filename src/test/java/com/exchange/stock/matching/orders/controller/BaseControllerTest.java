package com.exchange.stock.matching.orders.controller;

import com.exchange.stock.matching.orders.OrderMatchingEngineApplication;
import com.exchange.stock.matching.orders.model.Company;
import com.exchange.stock.matching.orders.model.Company.Symbol;
import com.exchange.stock.matching.orders.model.ExecutedOrder;
import com.exchange.stock.matching.orders.model.Order;
import com.exchange.stock.matching.orders.model.OrderType;
import com.exchange.stock.matching.orders.repository.ExecutedOrderRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrderMatchingEngineApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("MockUnitTest")
@Slf4j
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class BaseControllerTest {

    @Autowired
    protected ExecutedOrderRepository executedOrderRepository;

    @Autowired
    private MockMvc mvc;

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = (new ObjectMapper())
                .configure(Feature.AUTO_CLOSE_SOURCE, true)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static <T> T readTypeEntity(MvcResult result,
                                       TypeReference<T> typeReference) throws IOException {
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
        return OBJECT_MAPPER.readValue(responseContent, typeReference);
    }

    public static <T> T readEntity(MvcResult result,
                                   Class<T> type) throws IOException {
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
        return OBJECT_MAPPER.readValue(responseContent, type);
    }

    protected static final String EXECUTED_ORDERS_BASE_PATH = "/order-matching/v1/orders";

    protected MockMvc getMvc() {
        return mvc;
    }

    protected ExecutedOrder buildExecutedOrder(int quantity) {
        ExecutedOrder executedOrder = ExecutedOrder.builder()
                .id(UUID.randomUUID().toString())
                .quantity(quantity)
                .price(new BigDecimal(12))
                .buyOrder(Order.builder()
                        .orderId("123")
                        .orderTime(LocalTime.now())
                        .orderType(OrderType.BUY)
                        .quantity(quantity)
                        .company(new Company(Symbol.BAC))
                        .price(new BigDecimal(12))
                        .build())
                .sellOrder(Order.builder()
                        .orderId("124")
                        .orderTime(LocalTime.now())
                        .orderType(OrderType.SELL)
                        .quantity(quantity)
                        .company(new Company(Symbol.BAC))
                        .price(new BigDecimal(12))
                        .build())
                .build();
        return executedOrder;
    }
}
