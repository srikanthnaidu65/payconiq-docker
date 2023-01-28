package com.payconiq.stock.exception;

/**
 * @author srikanth
 * @since 28/01/2023
 * <p>
 * Exception class to handle any invalid stock inputs
 */
public class StockInputException extends RuntimeException {

    public StockInputException(String msg) {
        super(msg);
    }
}
