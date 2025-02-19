/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.inditex.Logistica.Repository;

import com.inditex.Logistica.Entity.PriceEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rocio
 */
@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p "
            + "INNER JOIN p.product prod "
            + "INNER JOIN p.brand b "
            + "WHERE prod.id = :idProduct "
            + "AND b.id = :idBrand "
            + "AND :dateApplication BETWEEN p.startDate AND p.endDate "
            + "ORDER BY p.priority DESC")
    List<PriceEntity> findByDateProductAndBrand(@Param("dateApplication") LocalDate dateApplication,
            @Param("idProduct") Long idProduct,
            @Param("idBrand") Long idBrand);

}
