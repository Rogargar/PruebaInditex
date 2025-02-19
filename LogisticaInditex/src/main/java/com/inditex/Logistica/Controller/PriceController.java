/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inditex.Logistica.Controller;

import com.inditex.Logistica.DTO.PriceDTO;
import com.inditex.Logistica.DTO.PriceResultDTO;
import com.inditex.Logistica.Exceptions.PriceNotFoundException;
import com.inditex.Logistica.Service.PriceService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rocio
 *
 */
@RestController
@RequestMapping("/price")
public class PriceController {

    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/find")
    public ResponseEntity<List<PriceResultDTO>> getPriceByDateProductAndBrand(
            @RequestParam("date") LocalDate date,
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Long brandId) {
        try {
            List<PriceResultDTO> priceDTOs = priceService.findByDateProductAndBrand(date, productId, brandId);
            return new ResponseEntity<>(priceDTOs, HttpStatus.OK);
        } catch (PriceNotFoundException e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<PriceDTO>> getAllPrices() {
        List<PriceDTO> prices = priceService.findAllPrices();
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceDTO> getPriceById(@PathVariable("id") Long id) {
        PriceDTO priceDTO = priceService.findPriceById(id);
        if (priceDTO != null) {
            return new ResponseEntity<>(priceDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PriceDTO> createPrice(@RequestBody PriceDTO priceDTO) {
        PriceDTO createdPrice = priceService.createPrice(priceDTO);
        return new ResponseEntity<>(createdPrice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceDTO> updatePrice(@PathVariable("id") Long id, @RequestBody PriceDTO priceDTO) {
        PriceDTO updatedPrice = priceService.updatePrice(id, priceDTO);
        if (updatedPrice != null) {
            return new ResponseEntity<>(updatedPrice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return ResponseEntity.ok("Price with ID " + id + " deleted successfully.");
    }

}
