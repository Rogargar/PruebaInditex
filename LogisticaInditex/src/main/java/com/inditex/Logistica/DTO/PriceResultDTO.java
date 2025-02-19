/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inditex.Logistica.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author rocio
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceResultDTO {
   
    private Long id;
    
    private ProductDTO product;
    
    private BrandDTO brandId;
    
    private Long priceList;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private BigDecimal price;

}
