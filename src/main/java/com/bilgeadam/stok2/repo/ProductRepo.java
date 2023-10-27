package com.bilgeadam.stok2.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bilgeadam.stok2.entity.Product;
@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

	Page<Product> findAllByName(String keyword, Pageable pageable);

	Page<Product> findAllByNameLike(String keyword, Pageable pageable);

	Page<Product> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);


}
