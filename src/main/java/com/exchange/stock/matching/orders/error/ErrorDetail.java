package com.exchange.stock.matching.orders.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
public class ErrorDetail implements Serializable {

    private static final String ERROR_DOMAIN = "ORDER_MATCHING";

    private String code;

    private String message;

    private String detail;

    private String domain;

    public ErrorDetail() {
        this.domain = ERROR_DOMAIN;
    }

    public String toExpandedString() {
        return "ErrorDetail{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
