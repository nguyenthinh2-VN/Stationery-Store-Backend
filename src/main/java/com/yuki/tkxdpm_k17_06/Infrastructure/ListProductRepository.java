package com.yuki.tkxdpm_k17_06.Infrastructure;

import com.yuki.tkxdpm_k17_06.Entity.Product;

import java.util.List;

public interface ListProductRepository {
    public List<Product> findAll();
}
