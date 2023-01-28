package com.payconiq.stock.service;

import com.payconiq.stock.model.Stock;

import java.util.List;
import java.util.Optional;

/**
 * @author srikanth
 * @since 28/01/2023
 * <p>
 * This is service class to handle stock operations
 */
public interface StockService {

    List<Stock> getAllStocks(Integer pageNo, Integer pageSize, String sortBy);

    Optional<Stock> getStock(Long id);

    Stock saveStock(Stock stock);

    Stock updateStock(Stock stock, Long id);

    void deleteStock(Long id);
}
