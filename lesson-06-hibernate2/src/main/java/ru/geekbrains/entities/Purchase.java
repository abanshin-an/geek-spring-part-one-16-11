package ru.geekbrains.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//@NamedQueries({
//        @NamedQuery(name = "purchaseByProduct",
//                query = "select :productName, pu.creationDate,pi.purchase.id,pi.price,pi.quantity from Purchase pu join PurchaseItem pi where Product.name=:productName"),
//        @NamedQuery(name = "purchaseByCustomer",
//                query = "select new ru.geekbrains.dto.PurchaseDTO(:customerName, pu.creationDate,pi.purchase.id,pi.price,pi.quantity) from Purchase pu join PurchaseItem pi where Customer.name=:customerName"),
//})
@Entity
@Table(name="purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "purchase",cascade=CascadeType.ALL, orphanRemoval=true)
    private List<PurchaseItem> purchaseItems;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public Purchase(Customer customer, List<PurchaseItem> purchaseItems) {
        this.customer = customer;
        this.purchaseItems = purchaseItems;
        this.purchaseItems.forEach(x->x.setPurchase(this));
    }

    public Purchase() {
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
        this.purchaseItems= purchaseItems;
        this.purchaseItems.forEach(x->x.setPurchase(this));
    }
}
