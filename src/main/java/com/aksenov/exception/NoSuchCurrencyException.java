package com.aksenov.exception;

public class NoSuchCurrencyException extends Exception {
    @Override
    public String getMessage() {
        return "There is no such currency";
    }
}
