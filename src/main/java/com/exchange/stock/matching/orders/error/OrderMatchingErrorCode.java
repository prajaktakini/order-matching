package com.exchange.stock.matching.orders.error;

public enum OrderMatchingErrorCode {

    INVALID_ORDER_TYPE("Invalid order type. Valid types are BUY or SELL"),

    RECORD_NOT_FOUND("Record not found");
    private final String message;

    OrderMatchingErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
