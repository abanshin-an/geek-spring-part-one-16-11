package ru.geekbrains.commands;

import org.springframework.stereotype.Component;

@Component
public class OperationExit implements Operation {

    @Override
    public String getName() {
        return "EXIT";
    }

    @Override
    public void execute() {
        // do nothing, just exit
    }

    @Override
    public boolean exit() {
        return true;
    }

}
