package com.payconiq.stock.controller;

import java.util.List;

import com.payconiq.stock.exception.StockNotFoundException;
import com.payconiq.stock.model.Stock;
import com.payconiq.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author srikanth
 * @since 28/01/2023
 * <p>
 * This is REST controller class to handle Stock HTTP operations
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "/api")
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService service;

    @GetMapping(value = "/stocks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Stock>> getStocks(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        LOGGER.info("Fetching all stocks for pageNo: {}, with pageSize: {}, and sorting by: {}", pageNo, pageSize, sortBy);
        List<Stock> stocks = service.getAllStocks(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping(value = "/stocks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stock> getStock(@PathVariable(name = "id") Long id) {
        LOGGER.info("Fetching stock with Id: {}", id);
        return service.getStock(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new StockNotFoundException(id));
    }

    @PostMapping(value = "/stocks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stock> saveStock(@RequestBody Stock stock) {
        LOGGER.info("Saving stock with Id: {}", stock.getId());
        service.saveStock(stock);
        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/stocks/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stock> updatePartialStock(@RequestBody Stock stock,
                                                    @PathVariable(name = "id") Long id) {
        LOGGER.info("Updating stock with Id: {}", id);
        return ResponseEntity.ok(service.updateStock(stock, id));
    }

    @DeleteMapping(value = "/stocks/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable(name = "id") Long id) {
        LOGGER.info("Deleting stock with Id: {}", id);
        service.deleteStock(id);
        return ResponseEntity.ok("Stock deleted with Id: " + id);
    }
}
