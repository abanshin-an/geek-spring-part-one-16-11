package ru.geekbrains.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.geekbrains.entities.Product;

@Component
public class ProductDAO extends DAO<Product> {

    public ProductDAO(SessionFactory sf) {
        super(sf, Product.class);
    }
    public Product findByName(String name) {
        return findOneByCriteria(new String[][]{{"name",name}}).orElseThrow();
    }
}
