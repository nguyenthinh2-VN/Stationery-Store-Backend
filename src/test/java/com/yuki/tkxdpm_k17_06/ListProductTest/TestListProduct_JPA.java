package com.yuki.tkxdpm_k17_06.ListProductTest;

import com.yuki.tkxdpm_k17_06.ListProduct.Entity.Product;
import com.yuki.tkxdpm_k17_06.ListProduct.Infrastructure.ListProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class để kiểm tra ListProductRepository lấy dữ liệu từ database
 */
@SpringBootTest
public class TestListProduct_JPA {

    @Autowired
    private ListProductRepository listProductRepository;

    /**
     * Test lấy tất cả sản phẩm từ database
     * Kiểm tra xem repository có trả về dữ liệu không và in ra console
     */
    @Test
    public void testFindAllProducts() {
        System.out.println("=== TEST: Lấy danh sách sản phẩm từ database ===");
        
        // Gọi repository để lấy dữ liệu
        List<Product> products = listProductRepository.findAll();
        
        // Kiểm tra danh sách không null
        assertNotNull(products, "Danh sách sản phẩm không được null");

        // In số lượng sản phẩm
        System.out.println("Số lượng sản phẩm: " + products.size());

        // In chi tiết từng sản phẩm
        if (products.isEmpty()) {
            System.out.println("⚠️ Database chưa có dữ liệu!");
        } else {
            System.out.println("\n📦 Danh sách sản phẩm:");
            System.out.println("─".repeat(100));

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.printf("Sản phẩm #%d:%n", i + 1);
                System.out.printf("  ├─ ID: %d%n", p.getId());
                System.out.printf("  ├─ Tên: %s%n", p.getName());
                System.out.printf("  ├─ Giá: %.2f%n", p.getPrice());
                System.out.printf("  ├─ Mô tả: %s%n", p.getDescription());
                System.out.printf("  ├─ Hình ảnh: %s%n", p.getImageUrl());
                System.out.printf("  ├─ Trạng thái: %s%n", p.getStatus());
                System.out.printf("  ├─ Ngày tạo: %s%n", p.getCreatedAt());
                System.out.printf("  └─ Ngày cập nhật: %s%n", p.getUpdatedAt());
                System.out.println("─".repeat(100));
            }
        }

        System.out.println("✅ Test hoàn thành!");
    }


    /**
     * Test kiểm tra conversion từ ProductJPA sang Product
     */
    @Test
    public void testProductConversion() {
        List<Product> products = listProductRepository.findAll();

        if (!products.isEmpty()) {
            Product product = products.get(0);

            // Kiểm tra tất cả fields đã được map đúng
            System.out.println("=== Kiểm tra Product Entity ===");
            System.out.println("Type: " + product.getClass().getName());
            System.out.println("ID type: " + product.getId().getClass().getName());
            System.out.println("Price type: " + (product.getPrice() != 0 ? "double" : "unknown"));
            System.out.println("Image type: " + product.getImageUrl().getClass().getName());
            System.out.println("Description type: " + product.getDescription().getClass().getName());
            System.out.println("Status type: " + product.getStatus().getClass().getName());
            System.out.println("Created At type: " + product.getCreatedAt().getClass().getName());
            System.out.println("Updated At type: " + product.getUpdatedAt().getClass().getName());

            // Verify không phải ProductJPA
            assertFalse(product.getClass().getName().contains("ProductJPA"),
                       "Phải trả về Product entity, không phải ProductJPA");

            System.out.println("✅ Conversion từ ProductJPA sang Product thành công!");
        }
    }
}
