package com.yuki.tkxdpm_k17_06.ListProduct.Control.ListProduct;

import com.yuki.tkxdpm_k17_06.ListProduct.Adapter.ListProductAdapter.InputBoundary;
import com.yuki.tkxdpm_k17_06.ListProduct.Adapter.ListProductAdapter.OutputBoundary;
import com.yuki.tkxdpm_k17_06.ListProduct.Entity.Product;
import com.yuki.tkxdpm_k17_06.ListProduct.Infrastructure.ListProductRepository;

import java.util.List;

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

        // 2. Đóng gói vào ListProductOutputData
        ListProductOutputData outputData = new ListProductOutputData(products);

        // 3. Gọi Presenter để chuyển đổi và đẩy dữ liệu cho UI
        outputBoundary.present(outputData);

        // 4. Trả về ListProductOutputData (có thể dùng cho testing hoặc logging)
        return outputData;
    }
}
