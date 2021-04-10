package com.pusi.basketball.repository;

import com.pusi.basketball.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findOrderByIdAndConfirmIsFalse(Long id);
}
