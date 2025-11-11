package com.yuki.tkxdpm_k17_06.Presenter;

import com.yuki.tkxdpm_k17_06.Entity.Product;

/**
 * Mapper chuyên trách nhiệm chuyển đổi Product → ListProductViewModel
 * Tuân thủ Single Responsibility Principle
 */
public class ListProductViewModelMapper {
    
    /**
     * Chuyển đổi từ Product entity sang ViewModel để hiển thị UI
     */
    public ListProductViewModel toViewModel(Product product) {
        ListProductViewModel viewModel = new ListProductViewModel();
        viewModel.setId(String.valueOf(product.getId()));
        viewModel.setName(product.getName());
        viewModel.setPrice(String.valueOf(product.getPrice()));
        viewModel.setImageUrl(product.getImageUrl());
        viewModel.setDescription(product.getDescription());
        viewModel.setIsSuccess("true");
        viewModel.setMessage("Product data");
        viewModel.setResult("OK");
        return viewModel;
    }
}
