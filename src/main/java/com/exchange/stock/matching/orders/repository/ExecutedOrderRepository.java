package com.exchange.stock.matching.orders.repository;

import com.exchange.stock.matching.orders.model.ExecutedOrder;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface ExecutedOrderRepository extends CassandraRepository<ExecutedOrder, String> {

    public Optional<ExecutedOrder> findById(String id);
}
