package com.bilgeadam.stok2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bilgeadam.stok2.entity.Category;
import com.bilgeadam.stok2.repo.CategoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepo categoryRepo;
	
	public Map<Integer, String> kategoriMap(){
		
		List<Category> categories = categoryRepo.findAll();
		
		Map<Integer, String> categoryMap = new HashMap<>();
		
		for(Category c : categories) {
			categoryMap.put(c.getId(), c.getName());
		}
		
		return categoryMap;
	}

}
