package com.payconiq.stock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author srikanth
 * @since 28/01/2023
 * <p>
 * Rest controller advice to handle all kinds of stock exceptions
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(StockNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleStockNotFoundException(StockNotFoundException e) {
        LOGGER.error("Stock Not Found Exception: {}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(StockInputException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleStockInputException(StockInputException e) {
        LOGGER.error("Stock Input Exception: {}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        LOGGER.error("Exception: {}", e.getMessage());
        return e.getMessage();
    }
}
