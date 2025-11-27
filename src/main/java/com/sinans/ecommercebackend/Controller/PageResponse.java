package com.sinans.ecommercebackend.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse <T>{
    private List<T> products;
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
}
