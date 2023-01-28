package com.payconiq.stock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stock.model.Stock;
import com.payconiq.stock.service.StockService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author srikanth
 * @since 28/01/2023
 * <p>
 * Junit test cases for Stock controller
 */
@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService service;

    @Test
    void testGetAllStock1() throws Exception {
        // Arrange
        Stock stock1 = new Stock(1L, "Stock-1", 100D, null);
        Stock stock2 = new Stock(2L, "Stock-2", 200D, null);
        Stock stock3 = new Stock(3L, "Stock-3", 300D, null);
        List<Stock> stocks = Arrays.asList(stock1, stock2, stock3);
        String expectedContent = "[{\"id\":1,\"name\":\"Stock-1\",\"currentPrice\":100.0,\"lastUpdate\":" + null + "}," +
                "{\"id\":2,\"name\":\"Stock-2\",\"currentPrice\":200.0,\"lastUpdate\":" + null + "}," +
                "{\"id\":3,\"name\":\"Stock-3\",\"currentPrice\":300.0,\"lastUpdate\":" + null + "}]";

        // Act and Assert
        when(service.getAllStocks(0, 10, "id")).thenReturn(stocks);
        mockMvc.perform(get("/api/stocks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(expectedContent))
                .andReturn();
    }

    @Test
    void testGetAllStock2() throws Exception {
        // Arrange
        List<Stock> stocks = List.of();
        String expectedContent = "[]";

        // Act and Assert
        when(service.getAllStocks(0, 10, "id")).thenReturn(stocks);
        mockMvc.perform(get("/api/stocks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(expectedContent))
                .andReturn();
    }

    @Test
    void testGetStock1() throws Exception {
        // Arrange
        Stock stock = new Stock(1L, "Stock-1", 100D, null);
        Optional<Stock> optionalStock = Optional.of(stock);
        String expectedContent = "{\"id\":1,\"name\":\"Stock-1\",\"currentPrice\":100.0,\"lastUpdate\":" + null + "}";

        // Act and Assert
        when(service.getStock(1L)).thenReturn(optionalStock);
        mockMvc.perform(get("/api/stocks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(expectedContent))
                .andReturn();
    }

    @Test
    void testGetStock2() throws Exception {
        // Arrange
        Optional<Stock> optionalStock = Optional.empty();
        String expectedContent = "Could not find stock with Id: " + 2;

        // Act and Assert
        when(service.getStock(2L)).thenReturn(optionalStock);
        mockMvc.perform(get("/api/stocks/2"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedContent))
                .andReturn();
    }

    @Test
    void testSaveStock() throws Exception {
        // Arrange
        Stock stock = new Stock(4L, "Stock-4", 400D, null);
        String expectedContent = "{\"id\":4,\"name\":\"Stock-4\",\"currentPrice\":400.0,\"lastUpdate\":" + null + "}";

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(stock);

        // Act and Assert
        when(service.saveStock(stock)).thenReturn(stock);
        mockMvc.perform(post("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedContent))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    @Test
    void testUpdatePartialStock() throws Exception {
        // Arrange
        Stock stock = new Stock(5L, "Stock-5", 500D, null);
        OffsetDateTime lastUpdate = OffsetDateTime.now();
        Stock updatedStock = new Stock(5L, "Stock-5", 600D, lastUpdate);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(stock);

        // Act and Assert
        when(service.updateStock(stock, 5L)).thenReturn(updatedStock);
        mockMvc.perform(patch("/api/stocks/5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testDeleteStock() throws Exception {
        // Arrange
        String expectedContent = "Stock deleted with Id: 6";

        // Act and Assert
        mockMvc.perform(delete("/api/stocks/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent))
                .andReturn();
    }
}
