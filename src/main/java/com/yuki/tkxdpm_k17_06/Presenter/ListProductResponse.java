package com.yuki.tkxdpm_k17_06.Presenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Response wrapper cho API endpoint
 * Chứa danh sách ViewModel và metadata
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListProductResponse {
    private boolean success;
    private String message;
    private List<ListProductViewModel> result;
}
