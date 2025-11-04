# Stationery-Store-Backend

🏛️ MÔ TẢ KIẾN TRÚC DỰ ÁN

Dự án được xây dựng theo Clean Architecture, với mục tiêu:

Tách biệt rõ ràng giữa Business Logic, Domain, Use Case và Framework.

Giảm phụ thuộc, dễ bảo trì, dễ thay đổi công nghệ.

Đảm bảo tính mở rộng trong tương lai.

🔹 Cấu trúc tổng thể theo Clean Architecture
src/main/java/com.yuki.tkxdpm_k17_06
│
├── Entity
│   └── Product.java
│
├── usecase
│   ├── Addproduct
│       └── AddProduct.java
│   └── ListProduct
│       └── ListProductControl.java
│       └── ProductDTO.java
│       └── ListProductOutputdata.java
│
├── Adapter
│   ├── Addproduct
│       └── AddProductAdapter
│   ├── ListProductAdapter
│       └── InputBoundary.java
│       └── OutputBoundary.java
│  
├── infrastructure
│   └── repository
│       └── jpa
│           ├── ProductJPA.java
│           ├── SpringDataCrudRepository.java
│           └── SpringDataProductJpaRepositoryImpl.java
│
└── web
    ├── controller
        └── ListProductController.java
    └── presenter
        ├── ListProductPresenter.java
        ├── ListProductResponse.java
        └── ListProductViewModel.java

🏷️ CÁC DESIGN PATTERN ĐÃ ÁP DỤNG
1. DTO Pattern (Data Transfer Object)

DTO dùng để truyền dữ liệu từ tầng này sang tầng khác mà không làm lộ cấu trúc Domain.

Tránh việc Controller trả trực tiếp Entity → đảm bảo bảo mật và tính ổn định.

Controller → InputBoundary → InputData → UseCase → Domain → OutputData → OutputBoundary → Controller → Response

2. Gateway Pattern

Tầng usecase chỉ biết interface Gateway (hoặc Repository Interface).

Tầng infrastructure implements Gateway để kết nối Database, API, file…
→ Điều này đảo ngược phụ thuộc (Dependency Inversion).

UseCase → (ProductRepository Interface)  
Infrastructure → ProductRepositoryImpl implements ProductRepository

3. Mẫu 3 Tầng (và 4 tầng theo CA)

Nhiều tài liệu gọi là 3 Layer Architecture, nhưng trong Clean Architecture thực tế là 4 tầng:

Tầng	Vai trò
Domain (Entity / Business Rule)	Quy tắc nghiệp vụ cốt lõi, không phụ thuộc framework
Use Case (Application Logic)	Triển khai luồng nghiệp vụ sử dụng Domain
Interface (Adapter / Web Layer)	Controller, ViewModel, REST API
Infrastructure	Database, Framework, Tools

=> Domain & UseCase không phụ thuộc Web hay DB.

4. ECB Pattern (Entity - Control - Boundary)
Vai trò	Tương ứng trong dự án
Entity	Tầng domain/entity
Control	usecase/interactor
Boundary	input/ output DTO, Gateway

ECB chính là nền của Clean Architecture → rõ trách nhiệm + dễ test.

✅ Áp dụng nguyên lý SOLID
S (Single Responsibility Principle)

Mỗi class chỉ làm 1 nhiệm vụ duy nhất.

Controller chỉ xử lý HTTP.

UseCase chỉ xử lý nghiệp vụ.

Repository chỉ xử lý lưu trữ.
→ Không lẫn logic vào nhau.

D (Dependency Inversion Principle)

Tầng cao (Use Case) phụ thuộc vào interface, không phụ thuộc tầng thấp (DB / Framework).

Do đó Use Case test được đơn vị (Unit Test) mà không cần DB.

UseCase -> ProductRepository (Interface) <- ProductRepositoryImpl (Infrastructure)

🎯 KẾT QUẢ ĐẠT ĐƯỢC

Mã nguồn dễ mở rộng, có thể thay:

Database khác (MySQL → PostgreSQL → MongoDB) mà không sửa logic

Giao diện Web sang Mobile API, Desktop API mà Use Case không đổi

Giảm rủi ro đảo lộn kiến trúc theo thời gian.

Dễ bảo trì, dễ test, dễ onboard người mới.
