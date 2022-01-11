package ru.geekbrains.dao;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ProductDAO {
    static ProductDAO productDAO;

    public static ProductDAO get(){
        if (productDAO==null) {
            productDAO=new ProductDAO();
        }
        return productDAO;
    }

    private EntityManagerFactory entityManagerFactory;

    private ProductDAO() {
        entityManagerFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public List<Product> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Product> list;
        try {
            list= entityManager.createQuery("SELECT a FROM Product a", Product.class).getResultList();
        } finally {
            entityManager.close();
        }
        return list;
    }

    public Product getEntityById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Product product;
        try {
            product= entityManager.find( Product.class, id );
        } finally {
            entityManager.close();
        }
        return product;
    }

    public Product update(Product entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        return entity;
    }

    public void delete(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Product p = entityManager.find(Product.class, id );
            entityManager.remove( p );
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public Product create(Product entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        return entity;
    }
}
