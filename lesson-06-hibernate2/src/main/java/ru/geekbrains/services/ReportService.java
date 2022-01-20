package ru.geekbrains.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.PurchaseDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final SessionFactory sf;

    @Autowired
    public ReportService(SessionFactory sf) {
        this.sf = sf;
    }

    public List<String> customerReport(String customerName) {
        List<String> list = new LinkedList<>();
        list.add("Report for Customer " + customerName);
        list.add("");
        List<String> list1 = findOrderByCustomer(customerName).stream().map(PurchaseDTO::toString).collect(Collectors.toList());
        if (list1.size() == 0) {
            list.add("No purchases found");
        } else
            list.addAll(list1);
        return list;
    }

    public List<String> salesReport(String productName) {
        List<String> list = new LinkedList<>();
        list.add("Report for Product " + productName);
        list.add("");
        List<String> list1 = findOrderByProduct(productName).stream().map(Objects::toString).collect(Collectors.toList());//.stream().map(PurchaseDTO::toString).collect(Collectors.toList());
        if (list1.size() == 0) {
            list.add("No purchases found");
        } else
            list.addAll(list1);
        return list;

    }

    public List<PurchaseDTO> findOrderByProduct(String productName) {
        try (Session session = sf.openSession()) {
            Query query = session.createNativeQuery("select pr.name, pu.creation_date dateCreation, pi.purchase_id purchaseid,pi.price,pi.quantity \n"
                            + " from purchase pu \n"
                            + " join purchase_item pi on (pu.id=pi.purchase_id) \n"
                            + " join product pr on (pr.id=pi.product_id) \n"
                            + " where pr.name=:productName")
                    .setParameter("productName", productName);
            List<PurchaseDTO> l = (List<PurchaseDTO>) query.list().stream().map(o -> new PurchaseDTO((Object[]) o)).collect(Collectors.toList());
            return l;
        }
    }

    public List<PurchaseDTO> findOrderByCustomer(String customerName) {
        try (Session session = sf.openSession()) {
            Query query = session.createNativeQuery("select c.name, pu.creation_date dateCreation, pi.purchase_id purchaseid,pi.price,pi.quantity \n"
                            + " from purchase pu \n"
                            + " join purchase_item pi on (pu.id=pi.purchase_id) \n"
                            + " join customer c on (c.id=pu.customer_id) \n"
                            + " where c.name=:customerName")
                    .setParameter("customerName", customerName);
            List<PurchaseDTO> l = (List<PurchaseDTO>) query.list().stream().map(o -> new PurchaseDTO((Object[]) o)).collect(Collectors.toList());
            return l;
        }
    }
}