package com.yuki.tkxdpm_k17_06.ListProduct.Infrastructure.JPA;

import com.yuki.tkxdpm_k17_06.ListProduct.Entity.Product;
import com.yuki.tkxdpm_k17_06.ListProduct.Infrastructure.ListProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SpringDataProductJpaRepositoryImpl implements ListProductRepository {
    private final SpringDataCrudRepository springDataCrudRepository;

    @Autowired
    public SpringDataProductJpaRepositoryImpl(SpringDataCrudRepository springDataCrudRepository) {
        this.springDataCrudRepository = springDataCrudRepository;
    }

    @Override
    public List<Product> findAll() {
        List<ProductJPA> productJPAList = springDataCrudRepository.findAll();
        // Convert ProductJPA to Product
        return productJPAList.stream()
                .map(this::convertToProduct)
                .collect(Collectors.toList());
    }
    
    private Product convertToProduct(ProductJPA productJPA) {
        Product product = new Product();
        product.setId(productJPA.getId());
        product.setName(productJPA.getName());
        product.setPrice(productJPA.getPrice());
        product.setDescription(productJPA.getDescription());
        product.setImageUrl(productJPA.getImageUrl());
        product.setStatus(productJPA.getStatus());
        product.setCreatedAt(productJPA.getCreatedAt());
        product.setUpdatedAt(productJPA.getUpdatedAt());
        return product;
    }
}
