package ru.geekbrains.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PurchaseDTO {
    private String name;
    private LocalDateTime dateCreation;
    private Long purchaseId;
    private BigDecimal price;
    private Long quantity;
    private BigDecimal amount;

    public PurchaseDTO() {
    }

    public PurchaseDTO(String name, LocalDateTime dateCreation, Long purchaseId,
                       BigDecimal price, Long quantity) {
        this.name = name;
        this.dateCreation = dateCreation;
        this.purchaseId = purchaseId;
        this.price = price;
        this.quantity = quantity;
        amount=price.multiply(new BigDecimal(quantity));
    }

    public PurchaseDTO(Object[] objects) {
        name = (String)objects[0];
        dateCreation=(((Timestamp)objects[1]).toLocalDateTime());
        purchaseId = ((BigInteger)objects[2]).longValue();
        price = (BigDecimal) objects[3];
        quantity = ((BigInteger)objects[2]).longValue();
        amount=price.multiply(new BigDecimal(quantity));
    }

    @Override
    public String toString() {
        return "PurchaseDTO{" +
                "name='" + name + '\'' +
                ", dateCreation=" + dateCreation +
                ", purchaseId=" + purchaseId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
