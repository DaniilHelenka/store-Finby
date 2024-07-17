package com.gmail.daniilhelenka98.store.repository;

import com.gmail.daniilhelenka98.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}