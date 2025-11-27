package com.sinans.ecommercebackend.Persistence.Product;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<ProductEntity> hasName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
    public static Specification<ProductEntity> hasMinPrice(Double price) {
        return ((root, query, criteriaBuilder) -> price == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price));
    }
    public static Specification<ProductEntity> hasMaxPrice(Double price) {
        return ((root, query, criteriaBuilder) -> price == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), price));
    }
}
