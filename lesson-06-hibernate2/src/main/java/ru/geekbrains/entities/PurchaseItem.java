package ru.geekbrains.entities;

import ru.geekbrains.dto.CartDTO;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="purchase_item")
public class PurchaseItem {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id" )
    private Product product;

    @Column(name="price", nullable = false )
//    @DecimalMin("0.01")
    private BigDecimal price;

    @Column(name="quantity", nullable = false )
    private Long quantity ;

    public Product getProduct() {
        return product;
    }

    public PurchaseItem(CartDTO cartItem) {
        product=cartItem.getProduct();
        quantity=cartItem.getQuantity();
        price=cartItem.getProduct().getPrice();
    }

    public PurchaseItem() {
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
