
# 🏬 Stationery-Store-Backend

Dự án **Stationery Store Backend** được xây dựng theo **Clean Architecture**, tập trung vào khả năng mở rộng, dễ bảo trì và giảm sự phụ thuộc lẫn nhau giữa các tầng trong hệ thống.

---

## 🏛️ Kiến Trúc Tổng Thể

Dự án tuân theo **Clean Architecture**, với mục tiêu:

- 🔹 Tách biệt rõ ràng giữa **Business Logic**, **Domain**, **Use Case** và **Framework**
- 🔹 Giảm phụ thuộc giữa các module
- 🔹 Tăng khả năng mở rộng, dễ thay đổi công nghệ
- 🔹 Tối ưu khả năng **unit test**

---

## 🧱 Cấu Trúc Dự Án



## 🏷️ Design Patterns Đã Áp Dụng

### 1) 📦 DTO Pattern (Data Transfer Object)

Giúp truyền dữ liệu giữa các tầng mà **không lộ nội bộ Domain**.

```
Controller → InputBoundary → InputDTO → UseCase → Domain → OutputDTO → Presenter → ViewModel → Response
```

---

### 2) 🔌 Gateway / Repository Pattern

* Tầng UseCase chỉ biết **interface** (Gateway)
* Tầng Infrastructure **implements** interface này để làm việc với Database

```
UseCase → ProductRepository (Interface)
Infrastructure → ProductRepositoryImpl → DB
```

➡️ Đây là **Dependency Inversion** trong SOLID.

---

### 3) 🧱 3-Layer / 4-Layer theo Clean Architecture

| Tầng                    | Vai Trò              | Mô Tả                            |
| ----------------------- | -------------------- | -------------------------------- |
| **Domain**              | Business Rule        | Không phụ thuộc framework        |
| **Use Case**            | Application logic    | Điều khiển luồng nghiệp vụ       |
| **Interface / Adapter** | Giao tiếp người dùng | Controller, Presenter, ViewModel |
| **Infrastructure**      | Công nghệ            | DB, JPA, Spring, HTTP            |

➡️ **Domain + UseCase không phụ thuộc Web hoặc Database.**

---

### 4) 🎮 ECB Pattern (Entity - Control - Boundary)

| Vai Trò      | Vị Trí Trong Dự Án                                  |
| ------------ | --------------------------------------------------- |
| **Entity**   | `Entity/Product`                                    |
| **Control**  | `usecase/*`                                         |
| **Boundary** | `Adapter/InputBoundary`, `Presenter/OutputBoundary` |

ECB giúp code dễ test & dễ đọc.

---

## ✅ Áp Dụng Nguyên Lý SOLID

### 🔹 SRP — Single Responsibility Principle

* Mỗi class chỉ làm **một nhiệm vụ duy nhất**
* Không lẫn logic giữa Controller / UseCase / Repository

### 🔹 DIP — Dependency Inversion Principle

* UseCase **phụ thuộc interface**
* Không phụ thuộc vào framework / database

```
UseCase -> Repository Interface <- RepositoryImpl (DB)
```

---

## 🎯 Kết Quả Đạt Được

* ♻️ Dễ mở rộng — đổi DB MySQL → PostgreSQL → MongoDB không ảnh hưởng logic
* 💻 Dễ đổi giao diện — từ Web → Mobile → Desktop mà UseCase vẫn giữ nguyên
* 🧹 Code rõ ràng, dễ đọc, dễ onboarding thành viên mới
* ✅ Tối ưu kiểm thử **unit test** vì business logic không phụ thuộc framework

---

## 🚀 Nếu cần, mình có thể hỗ trợ thêm:

* Viết **Sequence Diagram** cho Use Case
* Thêm **Swagger API Docs**
* Tối ưu Dependency Injection

```

---

