package ru.geekbrains.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.dao.ProductDAO;

import java.util.Scanner;

@Component
public class OperationFind implements Operation {
    ProductDAO dao;
    @Autowired
    public OperationFind(ProductDAO dao) {
        this.dao=dao;
    }
    @Override
    public String getName() {
        return "FIND";
    }

    @Override
    public void execute() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter id:");
        String line = scanner.nextLine().trim();
        System.out.println(dao.getEntityById(Long.parseLong(line)));
    }
}
