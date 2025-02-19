/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inditex.Logistica.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.Logistica.Controller.PriceController;
import com.inditex.Logistica.DTO.BrandDTO;
import com.inditex.Logistica.DTO.PriceResultDTO;
import com.inditex.Logistica.DTO.ProductDTO;
import com.inditex.Logistica.Service.PriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author rocio
 */
@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test1() throws Exception {
        // Se envía la fecha sin la hora (ya que se parsea a LocalDate)
        String dateParam = "2020-06-14";
        String timeParam = "10:00:00"; // parámetro extra solo para documentar la hora

        PriceResultDTO expectedDTO = new PriceResultDTO(
                1L,
                new ProductDTO(35455L, "Producto X"),
                new BrandDTO(1L, "ZARA"),
                1L,
                LocalDate.of(2020, 6, 14),
                LocalDate.of(2020, 12, 31),
                new BigDecimal("35.50")
        );

        when(priceService.findByDateProductAndBrand(LocalDate.parse(dateParam), 35455L, 1L))
                .thenReturn(Collections.singletonList(expectedDTO));

        mockMvc.perform(get("/price/find")
                .param("date", dateParam)
                .param("time", timeParam) // parámetro adicional (ignorado por el controlador)
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].priceList").value(1))
                .andExpect(jsonPath("$[0].price").value(35.50))
                .andExpect(jsonPath("$[0].brandId.id").value(1))
                .andExpect(jsonPath("$[0].brandId.name").value("ZARA"));
    }

    @Test
    public void test2() throws Exception {
        String dateParam = "2020-06-14";
        String timeParam = "16:00:00";

        PriceResultDTO expectedDTO = new PriceResultDTO(
                2L,
                new ProductDTO(35455L, "Producto X"),
                new BrandDTO(1L, "ZARA"),
                2L,
                LocalDate.of(2020, 6, 14),
                LocalDate.of(2020, 6, 14), // Asumimos vigencia solo ese día
                new BigDecimal("25.45")
        );

        when(priceService.findByDateProductAndBrand(LocalDate.parse(dateParam), 35455L, 1L))
                .thenReturn(Collections.singletonList(expectedDTO));

        mockMvc.perform(get("/price/find")
                .param("date", dateParam)
                .param("time", timeParam)
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].priceList").value(2))
                .andExpect(jsonPath("$[0].price").value(25.45))
                .andExpect(jsonPath("$[0].brandId.id").value(1))
                .andExpect(jsonPath("$[0].brandId.name").value("ZARA"));
    }

    @Test
    public void test3() throws Exception {
        String dateParam = "2020-06-14";
        String timeParam = "21:00:00";

        PriceResultDTO expectedDTO = new PriceResultDTO(
                1L,
                new ProductDTO(35455L, "Producto X"),
                new BrandDTO(1L, "ZARA"),
                1L,
                LocalDate.of(2020, 6, 14),
                LocalDate.of(2020, 12, 31),
                new BigDecimal("35.50")
        );

        when(priceService.findByDateProductAndBrand(LocalDate.parse(dateParam), 35455L, 1L))
                .thenReturn(Collections.singletonList(expectedDTO));

        mockMvc.perform(get("/price/find")
                .param("date", dateParam)
                .param("time", timeParam)
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].priceList").value(1))
                .andExpect(jsonPath("$[0].price").value(35.50))
                .andExpect(jsonPath("$[0].brandId.id").value(1))
                .andExpect(jsonPath("$[0].brandId.name").value("ZARA"));
    }

    @Test
    public void test4() throws Exception {
        String dateParam = "2020-06-15";
        String timeParam = "10:00:00";

        PriceResultDTO expectedDTO = new PriceResultDTO(
                3L,
                new ProductDTO(35455L, "Producto X"),
                new BrandDTO(1L, "ZARA"),
                3L,
                LocalDate.of(2020, 6, 15),
                LocalDate.of(2020, 6, 15),
                new BigDecimal("30.50")
        );

        when(priceService.findByDateProductAndBrand(LocalDate.parse(dateParam), 35455L, 1L))
                .thenReturn(Collections.singletonList(expectedDTO));

        mockMvc.perform(get("/price/find")
                .param("date", dateParam)
                .param("time", timeParam)
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].priceList").value(3))
                .andExpect(jsonPath("$[0].price").value(30.50))
                .andExpect(jsonPath("$[0].brandId.id").value(1))
                .andExpect(jsonPath("$[0].brandId.name").value("ZARA"));
    }

    @Test
    public void test5() throws Exception {
        String dateParam = "2020-06-16";
        String timeParam = "21:00:00";

        PriceResultDTO expectedDTO = new PriceResultDTO(
                4L,
                new ProductDTO(35455L, "Producto X"),
                new BrandDTO(1L, "ZARA"),
                4L,
                LocalDate.of(2020, 6, 16),
                LocalDate.of(2020, 12, 31),
                new BigDecimal("38.95")
        );

        when(priceService.findByDateProductAndBrand(LocalDate.parse(dateParam), 35455L, 1L))
                .thenReturn(Collections.singletonList(expectedDTO));

        mockMvc.perform(get("/price/find")
                .param("date", dateParam)
                .param("time", timeParam)
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].priceList").value(4))
                .andExpect(jsonPath("$[0].price").value(38.95))
                .andExpect(jsonPath("$[0].brandId.id").value(1))
                .andExpect(jsonPath("$[0].brandId.name").value("ZARA"));
    }
}
