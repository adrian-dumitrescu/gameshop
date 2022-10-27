package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.basic.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private BigDecimal pricePerKey;

    private AppUserBasicDto user;

    private Set<ProductKeyBasicDto> productKeys;

    private ProductDetailsBasicDto productDetails;

    private CartItemBasicDto cartItem;

    private OrderItemBasicDto orderItem;

}