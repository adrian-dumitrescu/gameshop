package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<ProductDetails, Long> {

    Optional<ProductDetails> findProductByProductName(String productName);
}
