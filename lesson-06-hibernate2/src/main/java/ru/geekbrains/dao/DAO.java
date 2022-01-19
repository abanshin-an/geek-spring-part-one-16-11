package ru.geekbrains.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DAO<T> {
    protected final SessionFactory sf;
    private final String entityName;
    private final Class<T> type;
    private final DAOUtils aa;

    public DAO(SessionFactory sf, Class<T> type) {
        this.sf = sf;
        this.type = type;
        this.aa = new DAOUtils((Class) type);
        this.entityName = aa.getEntityName();
    }

      protected List<T> execute(Function<Session, List<T>> function) {
        try (Session session = sf.openSession()) {
            return function.apply(session);
        }
    }

    protected void executeWithTransaction(Consumer<Session> consumer) {
        try (Session session = sf.openSession()) {
            try {
                session.getTransaction().begin();
                consumer.accept(session);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        }
    }

    public List<T> findAll() {
        return execute(em -> em.createQuery("from " + entityName, type).getResultList());
    }

    public Optional<T> findById(long id) {
        return execute(session -> session.createQuery("from " + entityName + " where id=" + id, type)
                .setMaxResults(1).getResultList())
                .stream().findFirst();
    }

    public Optional<T> findOneByCriteria(String[][] parameters) {
        return execute(session -> session.createQuery(getCriteria(session, parameters))
                .setMaxResults(1).getResultList())
                .stream().findFirst();
    }

    public List<T> findAllByCriteria(String[][] parameters) {
        return execute(session -> session.createQuery(getCriteria(session, parameters)).getResultList());
    }

    private CriteriaQuery<T> getCriteria(Session session, String[][] parameters) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(type);
        Root<T> root = cr.from(type);
        cr.select(root);
        final List<Predicate> predicates = Arrays.stream(parameters).map(x -> cb.equal(root.get(x[0]), x[1])).collect(Collectors.toList());
        var w = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        cr.where(w);
        return cr;
    }

    public void save(List<T> list) {
        executeWithTransaction(x -> {
            list.forEach(i -> {
                if (aa.idIsNotNull(i)) {
                    x.merge(i);
                } else {
                    x.persist(i);
                }
            });
        });
    }

    public void save(T e) {
        executeWithTransaction(x -> {
            if (aa.idIsNotNull(e)) {
                x.merge(e);
            } else {
                x.persist(e);
            }
        });
    }

    public void delete(long id) {
        executeWithTransaction(session -> {
            T o = session.find(type, id);
            session.remove(o);
        });
    }

}
