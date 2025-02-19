/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.inditex.Logistica.Repository;

import com.inditex.Logistica.Entity.PriceEntity;
import com.inditex.Logistica.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rocio
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    
}
