package com.bilgeadam.stok2.repo;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bilgeadam.stok2.entity.Category;
@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
//	@Query("SELECT DISTINCT c.id,c.name FROM Category c")
//	public Map<Integer, String> getCategoryMap();
	
}
