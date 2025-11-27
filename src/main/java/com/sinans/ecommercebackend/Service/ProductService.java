package com.sinans.ecommercebackend.Service;

import com.sinans.ecommercebackend.Controller.PageResponse;
import com.sinans.ecommercebackend.Controller.Product.ProductDTO;
import com.sinans.ecommercebackend.Controller.Product.ProductFilter;
import com.sinans.ecommercebackend.Mappers.DTOMappers.ProductMapper;
import com.sinans.ecommercebackend.Persistence.Product.ProductEntity;
import com.sinans.ecommercebackend.Persistence.Product.ProductRepository;
import com.sinans.ecommercebackend.Persistence.Product.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ProductMapper productMapper;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::EntityToDTO).toList();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDTO createProduct(ProductEntity productEntity) {
        ProductEntity productEntity1 = productRepository.save(productEntity);
        return productMapper.EntityToDTO(productEntity1);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDTO updateProduct(ProductEntity productEntity) {
        return productRepository.findById(productEntity.getId()).map(productEntity1 -> {
            Optional.ofNullable(productEntity.getName()).ifPresent(productEntity1::setName);
            Optional.ofNullable(productEntity.getPrice()).ifPresent(productEntity1::setPrice);
            Optional.ofNullable(productEntity.getDescription()).ifPresent(productEntity1::setDescription);
            Optional.ofNullable(productEntity.getQuantity()).ifPresent(productEntity1::setQuantity);
            ProductEntity productEntity2 = productRepository.save(productEntity1);
            return productMapper.EntityToDTO(productEntity2);
        }).orElseThrow(() -> new RuntimeException("could not update"));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }


    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::EntityToDTO);
    }

    public PageResponse<ProductDTO> searchProducts(ProductFilter filter, Pageable pageable){
        Specification<ProductEntity> spec = Specification.where(ProductSpecification.hasName(filter.getName()))
                .and(ProductSpecification.hasMinPrice(filter.getMinprice()))
                .and(ProductSpecification.hasMaxPrice(filter.getMaxprice()));
        Page<ProductEntity> productEntities = productRepository.findAll(spec, pageable);
        List<ProductDTO> productDTOS = productEntities.stream().map(productMapper::EntityToDTO).toList();
        return new PageResponse<>(
                productDTOS,
                productEntities.getNumber(),
                productEntities.getContent().size(),
                (int) productEntities.getTotalElements(),
                productEntities.getTotalPages()
        );

    }

}
