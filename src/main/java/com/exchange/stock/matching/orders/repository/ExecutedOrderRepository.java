package com.exchange.stock.matching.orders.repository;

import com.exchange.stock.matching.orders.model.ExecutedOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ExecutedOrderRepository extends MongoRepository<ExecutedOrder, String> {

    @Query("{ 'id' : ?0 }")
    public Optional<ExecutedOrder> findById(String id);
}
