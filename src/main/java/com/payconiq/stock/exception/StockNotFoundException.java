package com.payconiq.stock.exception;

/**
 * @author srikanth
 * @since 28/01/2023
 * <p>
 * Exception class to handle stock not found problems
 */
public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(Long id) {
        super("Could not find stock with Id: " + id);
    }
}
