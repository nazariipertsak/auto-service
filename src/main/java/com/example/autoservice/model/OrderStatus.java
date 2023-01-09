package com.example.autoservice.model;

public enum OrderStatus {
    ACCEPTED("accepted"),
    PROCESSING("processing"),
    SUCCESS("success"),
    FAILURE("failure"),
    PAID("paid");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
