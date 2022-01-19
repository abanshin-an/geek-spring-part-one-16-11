package ru.geekbrains;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.dao.DAO;
import ru.geekbrains.entities.Customer;
import ru.geekbrains.entities.Purchase;

@Configuration
public class Config {

    @Autowired
    private SessionFactory sessionFactory;

    @Bean
    public static SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    @Bean
    public DAO<Customer> customerDAO() {
        return new DAO<Customer>(sessionFactory, Customer.class);
    }

//    @Bean
//    public DAO<Product> productDAO() {
//        return new DAO<Product>(sessionFactory, Product.class);
//    }

    @Bean
    public DAO<Purchase> purchaseDAO() {
        return new DAO<Purchase>(sessionFactory,Purchase.class);
    }
}
