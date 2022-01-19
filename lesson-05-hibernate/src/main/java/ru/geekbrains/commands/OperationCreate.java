package ru.geekbrains.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.dao.ProductDAO;
import ru.geekbrains.entity.Product;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class OperationCreate implements Operation {
    ProductDAO dao;
    @Autowired
    public OperationCreate(ProductDAO dao) {
        this.dao=dao;
    }

    @Override
    public String getName() {
        return "CREATE";
    }

    @Override
    public void execute() {
        Scanner scanner=new Scanner(System.in);
        Product p=new Product();
        System.out.println("enter name:");
        p.setName(scanner.nextLine().trim());
        System.out.println("enter description:");
        p.setDescription(scanner.nextLine().trim());
        System.out.println("enter price:");
        String line = scanner.nextLine().trim();
        if (line.length()>0 && line.matches("\\d+(?:\\.\\d+)?|\\.\\d+"))
            p.setPrice(new BigDecimal(line));
        else
            p.setPrice(BigDecimal.ZERO);
        dao.create(p);
    }
}
