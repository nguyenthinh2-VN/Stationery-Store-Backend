package com.yuki.tkxdpm_k17_06.Presenter;

import com.yuki.tkxdpm_k17_06.Adapter.ListProductAdapter.OutputBoundary;
import com.yuki.tkxdpm_k17_06.Control.ListProduct.ListProductOutputData;
import com.yuki.tkxdpm_k17_06.Entity.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ListProductPresent implements OutputBoundary {

    @Getter
    private ListProductResponse response;
    private final ListProductViewModelMapper mapper;

    public ListProductPresent(ListProductViewModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void present(ListProductOutputData outputData) {
        // Nhận dữ liệu thô (Product) từ OutputData
        // Convert Product → ListProductViewModel thông qua Mapper
        List<ListProductViewModel> viewModels = outputData.getProducts().stream()
                .map(mapper::toViewModel)
                .collect(Collectors.toList());
        
        // Tạo Response wrapper từ ListProductViewModel đã chuyển đổi
        this.response = new ListProductResponse(
                true,
                "Products retrieved successfully",
                viewModels
        );
    }

}
