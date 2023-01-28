package com.payconiq.stock.service;

import com.payconiq.stock.dao.StockRepo;
import com.payconiq.stock.exception.StockInputException;
import com.payconiq.stock.exception.StockNotFoundException;
import com.payconiq.stock.model.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author srikanth
 * @since 28/01/2023
 * <p>
 * This is service class to handle stock operations
 */
@Service
public class StockServiceImpl implements StockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockRepo repo;

    @Override
    public List<Stock> getAllStocks(Integer pageNo, Integer pageSize, String sortBy) {
        LOGGER.debug("getAllStocks() method ---> pageNo: {}, pageSize: {}, sortBy: {}", pageNo, pageSize, sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Stock> pagedResult = repo.findAll(paging);
        return pagedResult.getContent();
    }

    @Override
    public Optional<Stock> getStock(Long id) {
        LOGGER.debug("getStock() method ---> id: {}", id);
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Stock saveStock(Stock stock) throws StockInputException {
        LOGGER.debug("saveStock() method ---> id: {}", stock.getId());
        Optional<Stock> optionalStock = repo.findById(stock.getId());
        if (optionalStock.isPresent()) {
            throw new StockInputException("Stock with Id: " + stock.getId() + " already exist");
        } else {
            repo.save(stock);
            return stock;
        }
    }

    @Override
    @Transactional
    public Stock updateStock(Stock stock, Long id) throws StockNotFoundException {
        LOGGER.debug("updateStock() method ---> id: {}", id);
        Stock stockToUpdate = repo.findById(id).orElseThrow(() -> new StockNotFoundException(id));
        stockToUpdate.setCurrentPrice(stock.getCurrentPrice());
        stockToUpdate.setLastUpdate(OffsetDateTime.now());
        repo.save(stockToUpdate);
        return stockToUpdate;
    }

    @Override
    public void deleteStock(Long id) throws StockNotFoundException {
        LOGGER.debug("deleteStock() method ---> id: {}", id);
        Stock stock = repo.findById(id).orElseThrow(() -> new StockNotFoundException(id));
        repo.delete(stock);
    }
}
