package com.exchange.stock.matching.orders.error;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class OrderMatchingExceptionHandler {

    @ExceptionHandler(value = OrderMatchingException.class)
    public ResponseEntity<ErrorResponse> handleOrderMatchingException(HttpServletRequest request,
                                                                      OrderMatchingException exception) {

        // Construct error response
        ErrorResponse response = new ErrorResponse(exception.getHttpStatus().value(), exception.getErrors());
        return new ResponseEntity<>(response, exception.getHttpStatus());

    }

    @Data
    public static class ErrorResponse implements Serializable {
        private int status;

        private List<ErrorDetail> errors = new ArrayList<>();

        public ErrorResponse() {
        }

        public ErrorResponse(final int status,
                             final List<ErrorDetail> errors) {
            this.status = status;
            this.errors = errors;
        }
    }
}
