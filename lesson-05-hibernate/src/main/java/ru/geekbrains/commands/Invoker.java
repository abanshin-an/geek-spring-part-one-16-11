package ru.geekbrains.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Invoker {

    Map<String, Operation> operations;

    @Autowired
    public Invoker(List<Operation> operationList) {
        operations = operationList.stream()
                .collect(Collectors.toMap(Operation::getName, o -> o));
    }

    public void executeOperations() {
        Scanner scanner=new Scanner(System.in);
        while (true) {
            System.out.println("enter command:");
            String line = scanner.nextLine().trim().toUpperCase();
            Operation operation=operations.get(line);
            if (operation==null) {
                System.out.println("Unknown operation :" + line);
            }
            else {
                operation.execute();
                if (operation.exit())
                    break;
            }
        }

    }
}
