package com.yuki.tkxdpm_k17_06.Infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCrudRepository extends JpaRepository<ProductJPA, Long> {
}
