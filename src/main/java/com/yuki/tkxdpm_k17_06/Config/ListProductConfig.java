package com.yuki.tkxdpm_k17_06.Config;

import com.yuki.tkxdpm_k17_06.Adapter.ListProductAdapter.InputBoundary;
import com.yuki.tkxdpm_k17_06.Control.ListProduct.ListProductControl;
import com.yuki.tkxdpm_k17_06.Infrastructure.ListProductRepository;
import com.yuki.tkxdpm_k17_06.Presenter.ListProductPresent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListProductConfig {

    @Bean
    public ListProductPresent listProductPresent() {
        return new ListProductPresent();
    }

    @Bean
    public InputBoundary listProductInputBoundary(
            ListProductPresent presenter,
            ListProductRepository listProductRepository) {
        return new ListProductControl(presenter, listProductRepository);
    }
}
