package com.mazzama.research.multisourcedb.tenant.repository;

import com.mazzama.research.multisourcedb.tenant.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
