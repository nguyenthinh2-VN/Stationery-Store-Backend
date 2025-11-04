package com.yuki.tkxdpm_k17_06.Presenter;

import com.yuki.tkxdpm_k17_06.Adapter.ListProductAdapter.OutputBoundary;
import com.yuki.tkxdpm_k17_06.Control.ListProduct.ListProductOutputData;
import com.yuki.tkxdpm_k17_06.Control.ListProduct.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ListProductPresent implements OutputBoundary {

    private ListProductResponse response;

    @Override
    public void present(ListProductOutputData outputData) {
        // Convert ProductDTO → ListProductViewModel
        List<ListProductViewModel> viewModels = outputData.getProducts().stream()
                .map(this::convertToViewModel)
                .collect(Collectors.toList());
        
        // Tạo Response wrapper từ ListProductViewModel đã chuyển đổi
        this.response = new ListProductResponse(
                true,
                "Products retrieved successfully",
                viewModels
        );
    }
    
    /**
     * Chuyển đổi từ ProductDTO sang ViewModel
     * Presenter chỉ biết về DTO, không biết về Domain Entity
     */
    private ListProductViewModel convertToViewModel(ProductDTO dto) {
        ListProductViewModel viewModel = new ListProductViewModel();
        viewModel.setId(dto.getId());
        viewModel.setName(dto.getName());
        viewModel.setPrice(dto.getPrice());
        viewModel.setImageUrl(dto.getImageUrl());
        viewModel.setDescription(dto.getDescription());
        viewModel.setIsSuccess(true);
        viewModel.setMessage("Product data");
        viewModel.setResult("OK");
        return viewModel;
    }
    
    public ListProductResponse getResponse() {
        return response;
    }
}
