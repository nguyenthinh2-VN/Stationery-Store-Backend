package com.yuki.tkxdpm_k17_06.Control.ListProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object cho Product
 * Dùng để truyền dữ liệu từ Use Case (Control) sang Presenter
 * Không phụ thuộc vào Domain Entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private String imageUrl;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
