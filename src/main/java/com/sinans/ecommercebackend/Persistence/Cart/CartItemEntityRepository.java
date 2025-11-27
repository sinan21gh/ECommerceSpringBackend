package com.sinans.ecommercebackend.Persistence.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemEntityRepository extends JpaRepository<CartItemEntity, Long> {
}
