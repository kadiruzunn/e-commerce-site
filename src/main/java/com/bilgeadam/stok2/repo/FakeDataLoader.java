package com.bilgeadam.stok2.repo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bilgeadam.stok2.entity.Category;
import com.bilgeadam.stok2.entity.Product;
import com.bilgeadam.stok2.entity.QuantityType;
import com.github.javafaker.Faker;

@Component
public class FakeDataLoader implements CommandLineRunner {

	private Category[] fakeCategories = {
			new Category(7, "Fake Kategori 1", null),
			new Category(8, "Fake Kategori 2", null),
			new Category(9, "Fake Kategori 3", null),
			new Category(10, "Fake Kategori 4", null),
	};
	
	@Override
	public void run(String... args) throws Exception {
		loadProducts();
		
	}
	
	private static final Random RANDOM = new Random();
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private Faker faker;
	
	private void loadProducts() {
		
		for (int i = 0; i < fakeCategories.length; i++) {
			fakeCategories[i] = categoryRepo.save(fakeCategories[i]);
		}
		
		int count = 0;
		
		if(productRepo.count() == 0) {
			for (int i = 0; i < 100; i++) {
				count++;
				productRepo.save(newProduct());
			}
		}
		
	}
	
	private Product newProduct() {
		final String name = faker.food().fruit();
		final Category cat = fakeCategories[RANDOM.nextInt(fakeCategories.length)];
		final LocalDate productionDate = LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault());
	
		return Product.builder()
				.name(name)
				.category(cat)
				.image("peynir.webp")
				.productionDate(productionDate)
				.quantity(RANDOM.nextDouble(1,100))
				.quantitytType(QuantityType.values()[RANDOM.nextInt(0,3)])
				.criticalStock(RANDOM.nextDouble(1,100))
				.build();
	}

}
