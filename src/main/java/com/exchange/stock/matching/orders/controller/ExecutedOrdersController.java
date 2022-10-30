package com.exchange.stock.matching.orders.controller;


import com.exchange.stock.matching.orders.mapper.ExecutedOrderMapper;
import com.exchange.stock.matching.orders.model.ExecutedOrder;
import com.exchange.stock.matching.orders.representation.ExecutedOrderReadTO;
import com.exchange.stock.matching.orders.service.ExecutedOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/matching-engine/v1/orders")
@Slf4j
public class ExecutedOrdersController {

    private ExecutedOrderService executedOrderService;

    private ExecutedOrderMapper orderMapper;

    @Autowired
    public ExecutedOrdersController(final ExecutedOrderService executedOrderService,
                                    final ExecutedOrderMapper orderMapper) {
        this.executedOrderService = executedOrderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExecutedOrderReadTO>> getAllExecutedOrders() {
        log.info("Received request to fetch all executed orders");
        List<ExecutedOrder> entities = executedOrderService.getAllOrders();
        return ResponseEntity.ok(orderMapper.toReadTOs(entities));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExecutedOrderReadTO> getExecutedOrder(@PathVariable("id") String id) {
        log.info("Received request to fetch executed order {}", id);
        ExecutedOrder entity = executedOrderService.getOrder(id);
        return ResponseEntity.ok(orderMapper.toReadTO(entity));
    }

}
