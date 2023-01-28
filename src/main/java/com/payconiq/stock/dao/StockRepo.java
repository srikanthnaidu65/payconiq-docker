package com.payconiq.stock.dao;

import com.payconiq.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author srikanth
 * @since 28/01/2023
 * <p>
 * This is Stock Repository class to handle JPA operations on Stock Entity
 */
@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
}
