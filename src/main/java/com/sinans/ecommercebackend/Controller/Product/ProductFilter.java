package com.sinans.ecommercebackend.Controller.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter {
    private String name;
    private Double minprice;
    private Double maxprice;
}
