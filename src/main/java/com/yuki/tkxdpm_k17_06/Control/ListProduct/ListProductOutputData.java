package com.yuki.tkxdpm_k17_06.Control.ListProduct;

import lombok.Getter;

import java.util.List;

/**
 * Output Data chứa ProductDTO thay vì Product domain entity
 * Tuân thủ Clean Architecture: Use Case layer không expose Domain Entity ra ngoài
 */
public class ListProductOutputData {
    private final List<ProductDTO> products;

    public ListProductOutputData(List<ProductDTO> products) {
        this.products = products;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }
}
