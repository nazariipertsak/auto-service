package com.example.autoservice.model;

public enum FavorStatus {
    PAID("paid"),
    NOT_PAID("not paid");

    private final String value;

    FavorStatus(String value) {
        this.value = value;
    }
}
