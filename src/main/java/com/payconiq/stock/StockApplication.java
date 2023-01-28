package com.payconiq.stock;

import com.payconiq.stock.dao.StockRepo;
import com.payconiq.stock.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;

/**
 * @author srikanth
 * @since 28/01/2023
 */
@SpringBootApplication
public class StockApplication implements CommandLineRunner {

    @Autowired
    private StockRepo stockRepo;

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    /**
     * This method is to load initial Stocks to database.
     * Required only for testing purpose. Disabled prior to production deployment.
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {
        Stock stock;
        for (long i = 1; i <= 1000; i++) {
            stock = new Stock();
            stock.setId(i);
            stock.setName("Stock-" + i);
            stock.setCurrentPrice(Math.random());
            stock.setLastUpdate(OffsetDateTime.now());
            stockRepo.save(stock);
        }
    }
}
