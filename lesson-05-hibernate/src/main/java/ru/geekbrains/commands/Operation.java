package ru.geekbrains.commands;

public interface Operation {
    String getName();

    void execute();
    default  boolean exit() {return false;}
}
