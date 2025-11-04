package com.yuki.tkxdpm_k17_06.Control.ListProduct;

import com.yuki.tkxdpm_k17_06.Adapter.ListProductAdapter.InputBoundary;
import com.yuki.tkxdpm_k17_06.Adapter.ListProductAdapter.OutputBoundary;
import com.yuki.tkxdpm_k17_06.Entity.Product;
import com.yuki.tkxdpm_k17_06.Infrastructure.ListProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListProductControl implements InputBoundary {
    private OutputBoundary outputBoundary;
    private ListProductRepository listProductRepository;


    public ListProductControl(OutputBoundary outputBoundary, ListProductRepository listProductRepository) {
        this.outputBoundary = outputBoundary;
        this.listProductRepository = listProductRepository;
    }

    @Override
    public ListProductOutputData execute() {
        // 1. Lấy danh sách Product từ Repository (đã convert từ ProductJPA)
        List<Product> products = listProductRepository.findAll();
        
        // 2. Convert Product (Domain) → ProductDTO
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        // 3. Đóng gói vào ListProductOutputData
        ListProductOutputData outputData = new ListProductOutputData(productDTOs);
        
        // 4. Gọi Presenter để chuyển đổi và đẩy dữ liệu cho UI
        outputBoundary.present(outputData);
        
        // 5. Trả về ListProductOutputData (có thể dùng cho testing hoặc logging)
        return outputData;
    }
    
    /**
     * Convert Product domain entity sang ProductDTO
     * Use Case chịu trách nhiệm convert để không expose Domain Entity ra ngoài
     */
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}
