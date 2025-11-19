package com.yuki.tkxdpm_k17_06.ListProduct.RestfulAPI_Controller;

import com.yuki.tkxdpm_k17_06.ListProduct.Adapter.ListProductAdapter.InputBoundary;
import com.yuki.tkxdpm_k17_06.ListProduct.Control.ListProduct.ListProductOutputData;
import com.yuki.tkxdpm_k17_06.ListProduct.Presenter.ListProductPresent;
import com.yuki.tkxdpm_k17_06.ListProduct.Presenter.ListProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ListProductController  {
    private final InputBoundary inputBoundary;
    private final ListProductPresent present;

    @Autowired
    public ListProductController(InputBoundary inputBoundary, ListProductPresent present) {
        this.inputBoundary = inputBoundary;
        this.present = present;
    }

    @GetMapping("/products")
    public ResponseEntity<ListProductResponse> listProduct() {

        // Gửi request đến LoginInputBoundary
        ListProductOutputData outputData = inputBoundary.execute();
        
        // Presenter xử lý OutputData và chuyển đổi sang ViewModel
        present.present(outputData);
        
        // Trả về response cho client
        return ResponseEntity.ok(present.getResponse());
    }

}
