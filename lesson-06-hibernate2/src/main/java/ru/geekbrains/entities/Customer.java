package ru.geekbrains.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", unique=true, nullable = false )
    private String name;

    @OneToMany(mappedBy = "customer")
    private List<Purchase> purchases;

    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Purchase> getOrders() {
        return purchases;
    }

    public void setOrders(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
