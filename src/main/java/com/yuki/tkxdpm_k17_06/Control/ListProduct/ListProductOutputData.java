package com.yuki.tkxdpm_k17_06.Control.ListProduct;

import com.yuki.tkxdpm_k17_06.Entity.Product;

import java.util.List;

/**
 * Output Data chứa dữ liệu thô (ProductDTO) thay vì Product domain entity
 * Tuân thủ Clean Architecture: Use Case layer không expose Domain Entity ra ngoài
 */
public class ListProductOutputData {
    private final List<Product> products;

    public ListProductOutputData(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
