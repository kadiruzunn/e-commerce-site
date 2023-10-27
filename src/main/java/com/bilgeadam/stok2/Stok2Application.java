package com.bilgeadam.stok2;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bilgeadam.stok2.entity.Category;
import com.bilgeadam.stok2.entity.Product;
import com.bilgeadam.stok2.entity.QuantityType;
import com.bilgeadam.stok2.repo.CategoryRepo;
import com.bilgeadam.stok2.repo.ProductRepo;

@SpringBootApplication
public class Stok2Application {

	public static void main(String[] args) {
		SpringApplication.run(Stok2Application.class, args);
	}
	
	@Bean
	CommandLineRunner runner(ProductRepo productRepo, CategoryRepo categoryRepo) {
		
		return args ->{
			
			Category c1 = Category.builder()
					.name("Yiyecek")
					.build();
			
			//categoryRepo.save(c1);
			
			Category c2 = Category.builder()
					.name("İçecek")
					.build();
			
			//categoryRepo.save(c2);
			
			Category c3 = Category.builder()
					.name("Temizlik Malzemesi")
					.build();
			
			//categoryRepo.save(c3);
			
			Product p1 = Product.builder()
					.name("Peynir")
					.image("peynir.webp")
					.category(c1)
					.quantity(10.0) // double > Double
					.quantitytType(QuantityType.KG)
					.criticalStock(5.0)
					.productionDate(LocalDate.of(2023, Month.AUGUST, 4))
					.build();
			
			Product p2 = Product.builder()
					.name("Zeytinyağı")
					.image("zeytin.webp")
					.category(c1)
					.quantity(50.0) // double > Double
					.quantitytType(QuantityType.LITRE)
					.criticalStock(15.0)
					.productionDate(LocalDate.of(2023, Month.APRIL, 3))
					.build();
			
			//productRepo.save(p1);
			//productRepo.save(p2);
			
			//productRepo.saveAll(Arrays.asList(p1,p2));
			
			//productRepo.saveAll(List.of(p1,p2));
			
		};
		
	}

}
