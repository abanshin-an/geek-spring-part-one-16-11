package ru.geekbrains.dto;

import ru.geekbrains.entities.Product;

public class CartDTO {
    private final Product product;
    private final Long quantity;

    public CartDTO(Product product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }
}
