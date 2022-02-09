package ru.geekbrains.service.dto;

import java.time.LocalDateTime;

public class ErrorDto {
    private String message;
    private LocalDateTime dateTime ;

    public ErrorDto(){
        dateTime = LocalDateTime.now();
    }

    public ErrorDto(String message) {
        this();
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
