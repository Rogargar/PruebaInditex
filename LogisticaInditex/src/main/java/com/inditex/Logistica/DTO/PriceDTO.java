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
public class PriceDTO {

    private Long id;
    private BrandDTO brand;
    private ProductDTO product;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long priceList;
    private Integer priority;
    private BigDecimal price;
    private String currency;

}
