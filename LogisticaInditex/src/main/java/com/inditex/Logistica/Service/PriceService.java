/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.inditex.Logistica.Service;

import com.inditex.Logistica.DTO.PriceDTO;
import com.inditex.Logistica.DTO.PriceResultDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author rocio
 */
public interface PriceService {

    List<PriceResultDTO> findByDateProductAndBrand(LocalDate applicationDate, Long idProduct, Long idBrand);

    List<PriceDTO> findAllPrices();

    PriceDTO findPriceById(Long id);

    PriceDTO createPrice(PriceDTO priceDTO);

    PriceDTO updatePrice(Long id, PriceDTO priceDTO);
    
    void deletePrice(Long id);

}
