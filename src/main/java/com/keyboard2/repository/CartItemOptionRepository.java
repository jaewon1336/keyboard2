package com.keyboard2.repository;

import com.keyboard2.entity.CartItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemOptionRepository extends JpaRepository<CartItemOption, Long> {
}
