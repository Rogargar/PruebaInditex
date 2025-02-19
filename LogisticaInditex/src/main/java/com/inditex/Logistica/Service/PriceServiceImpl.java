/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inditex.Logistica.Service;

import com.inditex.Logistica.DTO.BrandDTO;
import com.inditex.Logistica.DTO.PriceDTO;
import com.inditex.Logistica.DTO.PriceResultDTO;
import com.inditex.Logistica.DTO.ProductDTO;
import com.inditex.Logistica.Entity.BrandEntity;
import com.inditex.Logistica.Entity.PriceEntity;
import com.inditex.Logistica.Entity.ProductEntity;
import com.inditex.Logistica.Exceptions.PriceNotFoundException;
import com.inditex.Logistica.Repository.BrandRepository;
import com.inditex.Logistica.Repository.PriceRepository;
import com.inditex.Logistica.Repository.ProductRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author rocio
 */
@Slf4j
@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    public PriceServiceImpl(PriceRepository priceRepository, ProductRepository productRepository, BrandRepository brandRepository) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<PriceResultDTO> findByDateProductAndBrand(LocalDate dateApplication, Long idProduct, Long idBrand) {

        if (dateApplication == null || idProduct == null || idBrand == null) {
            log.error("Parámetros de entrada nulos: fecha {}, producto ID {}, marca ID {}", dateApplication, idProduct, idBrand);
            throw new IllegalArgumentException("Los parámetros de fecha, producto y marca no pueden ser nulos.");
        }

        List<PriceEntity> priceEntities = priceRepository.findByDateProductAndBrand(dateApplication, idProduct, idBrand);

        if (!priceEntities.isEmpty()) {
            List<PriceResultDTO> priceDTOs = priceEntities.stream().map(price -> {
                // Mapeo de PricesEntity a PriceResultDTO
                return new PriceResultDTO(
                        price.getId(),
                        price.getProduct() != null ? new ProductDTO(price.getProduct().getId(), price.getProduct().getName()) : null,
                        price.getBrand() != null ? new BrandDTO(price.getBrand().getId(), price.getBrand().getName()) : null,
                        price.getPriceList(),
                        price.getStartDate(),
                        price.getEndDate(),
                        price.getPrice()
                );
            }).collect(Collectors.toList());

            log.info("Se encontraron precios para el producto con ID {} y marca con ID {}.", idProduct, idBrand);
            return priceDTOs;
        } else {
            log.warn("No se encontraron precios para el producto con ID {} y marca con ID {} en la fecha {}.", idProduct, idBrand, dateApplication);
            throw new PriceNotFoundException("No se encontró precio aplicable para el producto con ID " + idProduct
                    + " y la marca con ID " + idBrand + " en la fecha " + dateApplication);
        }
    }

    @Override
    public List<PriceDTO> findAllPrices() {
        return priceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PriceDTO findPriceById(Long id) {
        return priceRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Price not found with ID " + id));
    }

    @Override
    public PriceDTO createPrice(PriceDTO priceDTO) {
        PriceEntity priceEntity = convertToEntity(priceDTO);
        return convertToDTO(priceRepository.save(priceEntity));
    }

    @Override
    public PriceDTO updatePrice(Long id, PriceDTO priceDTO) {
        return priceRepository.findById(id)
                .map(existingPrice -> {
                    updateEntityFromDTO(existingPrice, priceDTO);
                    return convertToDTO(priceRepository.save(existingPrice));
                })
                .orElseThrow(() -> new RuntimeException("Price not found with ID " + id));
    }

    private void updateEntityFromDTO(PriceEntity priceEntity, PriceDTO priceDTO) {
        priceEntity.setStartDate(priceDTO.getStartDate());
        priceEntity.setEndDate(priceDTO.getEndDate());
        priceEntity.setPrice(priceDTO.getPrice());
        priceEntity.setPriceList(priceDTO.getPriceList());
        priceEntity.setPriority(priceDTO.getPriority());
        priceEntity.setCurrency(priceDTO.getCurrency());
    }

    @Override
    public void deletePrice(Long id) {
        if (priceRepository.existsById(id)) {
            priceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Price not found with ID " + id);
        }
    }

    private ProductDTO convertToProductDTO(ProductEntity product) {
        return new ProductDTO(
                product.getId(),
                product.getName() // Similar, asegúrate de incluir los campos que desees del producto
        );
    }

    private BrandDTO convertToBrandDTO(BrandEntity brand) {
        return new BrandDTO(
                brand.getId(),
                brand.getName() // Asegúrate de tener el nombre o los campos que desees incluir en el DTO
        );
    }

    private PriceDTO convertToDTO(PriceEntity priceEntity) {
        // Convertir las entidades Brand y Product a sus respectivos DTOs
        BrandDTO brandDTO = priceEntity.getBrand() != null ? convertToBrandDTO(priceEntity.getBrand()) : null;
        ProductDTO productDTO = priceEntity.getProduct() != null ? convertToProductDTO(priceEntity.getProduct()) : null;

        return new PriceDTO(
                priceEntity.getId(),
                brandDTO != null ? brandDTO : null, // Usamos los DTOs aquí para incluir más datos si es necesario
                productDTO != null ? productDTO : null,
                priceEntity.getStartDate(),
                priceEntity.getEndDate(),
                priceEntity.getPriceList(),
                priceEntity.getPriority(),
                priceEntity.getPrice(),
                priceEntity.getCurrency()
        );
    }

    private PriceEntity convertToEntity(PriceDTO priceDTO) {
        PriceEntity priceEntity = new PriceEntity();

        // Recuperamos las entidades Product y Brand a partir de sus IDs
        // Recuperamos las entidades Product y Brand a partir de sus IDs
        ProductEntity product = null;
        if (priceDTO.getProduct().getId() != null) {
            product = productRepository.findById(priceDTO.getProduct().getId()).orElse(null);
        }
        if (product == null) {
            // Si no se encuentra el producto, lo creamos
            product = new ProductEntity();
            product.setId(priceDTO.getProduct().getId());  // Asegúrate de asignar los campos necesarios
            product.setName(priceDTO.getProduct().getName());
            productRepository.save(product);
        }

        BrandEntity brand = null;
        if (priceDTO.getBrand().getId() != null) {
            brand = brandRepository.findById(priceDTO.getBrand().getId()).orElse(null);
        }
        if (brand == null) {
            // Si no se encuentra la marca, la creamos
            brand = new BrandEntity();
            brand.setId(priceDTO.getBrand().getId());  // Asegúrate de asignar los campos necesarios
            brand.setName(priceDTO.getBrand().getName());
            brandRepository.save(brand);
        }

        // Asignamos las entidades Product y Brand a la entidad PriceEntity
        priceEntity.setProduct(product);
        priceEntity.setBrand(brand);

        // Asignamos el resto de los campos
        priceEntity.setStartDate(priceDTO.getStartDate());
        priceEntity.setEndDate(priceDTO.getEndDate());
        priceEntity.setPrice(priceDTO.getPrice());
        priceEntity.setPriceList(priceDTO.getPriceList());
        priceEntity.setPriority(priceDTO.getPriority());
        priceEntity.setCurrency(priceDTO.getCurrency());

        return priceEntity;
    }

}
