package com.mazzama.research.multisourcedb.repository;

import com.mazzama.research.multisourcedb.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
}
