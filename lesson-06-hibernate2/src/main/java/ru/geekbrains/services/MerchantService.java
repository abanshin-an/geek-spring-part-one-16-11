package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.CartDTO;
import ru.geekbrains.dao.DAO;
import ru.geekbrains.entities.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchantService {

    private final DAO<Purchase> purchaseDAO;

    @Autowired
    public MerchantService(DAO<Purchase> purchaseDAO) {
        this.purchaseDAO=purchaseDAO;
    }

    public void sell(Customer customer, CartDTO[] cart){
        List<PurchaseItem> purchaseItems = Arrays.stream(cart).map(PurchaseItem::new).collect(Collectors.toList());
        Purchase purchase = new Purchase(customer, purchaseItems);
        purchaseDAO.save(purchase);
    }

    public void addPrice(Product product, BigDecimal addingPrice) {
        product.setPrice(product.getPrice().add(addingPrice));
    }
}
