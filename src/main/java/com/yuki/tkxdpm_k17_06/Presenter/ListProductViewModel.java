package com.yuki.tkxdpm_k17_06.Presenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ViewModel đại diện cho 1 sản phẩm trong danh sách
 * Theo Clean Architecture: DTO để trình diễn dữ liệu cho UI/API
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListProductViewModel {
    private Long id;
    private String name;
    private Double price;
    private String imageUrl;
    private String description;
    private Boolean isSuccess;
    private String message;
    private String result;
}
