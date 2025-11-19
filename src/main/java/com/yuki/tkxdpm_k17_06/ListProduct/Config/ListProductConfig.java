package com.yuki.tkxdpm_k17_06.ListProduct.Config;

import com.yuki.tkxdpm_k17_06.ListProduct.Adapter.ListProductAdapter.InputBoundary;
import com.yuki.tkxdpm_k17_06.ListProduct.Control.ListProduct.ListProductControl;
import com.yuki.tkxdpm_k17_06.ListProduct.Infrastructure.ListProductRepository;
import com.yuki.tkxdpm_k17_06.ListProduct.Presenter.ListProductPresent;
import com.yuki.tkxdpm_k17_06.ListProduct.Presenter.ListProductViewModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListProductConfig {

    @Bean
    public ListProductViewModelMapper listProductViewModelMapper() {
        return new ListProductViewModelMapper();
    }

    @Bean
    public ListProductPresent listProductPresent(ListProductViewModelMapper mapper) {
        return new ListProductPresent(mapper);
    }

    @Bean
    public InputBoundary listProductInputBoundary(
            ListProductPresent presenter,
            ListProductRepository listProductRepository) {
        return new ListProductControl(presenter, listProductRepository);
    }
}
