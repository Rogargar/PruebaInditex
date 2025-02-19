/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.inditex.Logistica.Repository;

import com.inditex.Logistica.Entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rocio
 */
@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long>{
    

}
