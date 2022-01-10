package ru.geekbrains.persist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private int count;
    private BigDecimal amount;

    public CartDTO(Product product,Integer count) {
        productId=product.getId();
        productName=product.getName();
        productPrice=product.getPrice();
        this.count=count;
        amount=productPrice.multiply(new BigDecimal(count));
    }

}
