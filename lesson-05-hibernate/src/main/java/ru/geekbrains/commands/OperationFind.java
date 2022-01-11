package ru.geekbrains.commands;

import org.springframework.stereotype.Component;
import ru.geekbrains.dao.ProductDAO;

import java.util.Scanner;

@Component
public class OperationFind implements Operation {
    @Override
    public String getName() {
        return "FIND";
    }

    @Override
    public void execute() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter id:");
        String line = scanner.nextLine().trim();
        System.out.println(ProductDAO.get().getEntityById(Long.parseLong(line)));
    }
}
