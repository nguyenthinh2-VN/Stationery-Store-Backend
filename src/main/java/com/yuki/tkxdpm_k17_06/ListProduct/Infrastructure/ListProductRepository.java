package com.yuki.tkxdpm_k17_06.ListProduct.Infrastructure;

import com.yuki.tkxdpm_k17_06.ListProduct.Entity.Product;

import java.util.List;

public interface ListProductRepository {
    public List<Product> findAll();
}
