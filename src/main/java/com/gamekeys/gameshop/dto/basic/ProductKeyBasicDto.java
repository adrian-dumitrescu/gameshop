package com.gamekeys.gameshop.dto.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ProductKeyBasicDto {

    private Long id;

    private String activationKey;

    private BigDecimal price;

    // No reference to Inventory inventory;

    // No reference to ProductDetails productDetails;

    // No reference to OrderItem orderItem;


}
