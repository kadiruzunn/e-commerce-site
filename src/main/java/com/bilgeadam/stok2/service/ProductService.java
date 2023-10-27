package com.bilgeadam.stok2.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bilgeadam.stok2.dto.EmailDetails;
import com.bilgeadam.stok2.entity.Product;
import com.bilgeadam.stok2.repo.ProductRepo;

import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class ProductService {

	@Autowired
	private ProductRepo productRepo;
	@Autowired
	@Qualifier("gmail")
	private EmailService emailService;

	public List<Product> urunler() {

		return productRepo.findAll();

	}

	public void sil(Long id) {

		productRepo.deleteById(id);

	}

	public Product idIleUrunGetir(Long id) {

		return productRepo.findById(id).get();

	}

	public Product ekle(Product product) {

		return productRepo.save(product);
	}

	public void kaydet(Product product) {

		productRepo.save(product);

	}

	public void guncelle(Long id, @Valid Product product) {

		Product varolanUrun = idIleUrunGetir(id);

		varolanUrun.setCategory(product.getCategory());
		varolanUrun.setCriticalStock(product.getCriticalStock());
		varolanUrun.setImage(product.getImage());
		varolanUrun.setName(product.getName());
		varolanUrun.setProductionDate(product.getProductionDate());
		varolanUrun.setQuantity(product.getQuantity());
		varolanUrun.setQuantitytType(product.getQuantitytType());

		productRepo.save(varolanUrun);

	}

	public List<Product> urunler(int pageNo, int recordCount) {

		Pageable pageable = PageRequest.of(pageNo, recordCount);

		return productRepo.findAll(pageable).get().toList();
	}

	public List<Product> urunler(int pageNo, int recordCount, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, recordCount, Sort.by(sortBy));

		return productRepo.findAll(pageable).get().toList();
	}

	public Page<Product> urunler(int pageNo, int recordCount, String sortBy, String direction) {
		Pageable pageable = PageRequest.of(pageNo - 1, recordCount,
				Sort.by(("desc".equals(direction) ? Direction.DESC : Direction.ASC), sortBy));

		return productRepo.findAll(pageable);
	}

	public Page<Product> urunler(Integer pageNo, Integer recordCount, String sortBy, String direction, String keyword) {
		Pageable pageable = PageRequest.of(pageNo - 1, recordCount,
				Sort.by(("desc".equals(direction) ? Direction.DESC : Direction.ASC), sortBy));

		if (keyword.isEmpty()) {
			return productRepo.findAll(pageable);
		} else {
			// return productRepo.findAllByName(keyword, pageable);
			// keyword = "%" + keyword + "%";
			// return productRepo.findAllByNameLike(keyword, pageable);

			return productRepo.findAllByNameContainingIgnoreCase(keyword, pageable);
		}
	}

	public void stokEksilt(Long id) {

		Product varolanUrun = idIleUrunGetir(id);

		if (varolanUrun.getQuantity() - 1 >= 0) {
			varolanUrun.setQuantity(varolanUrun.getQuantity() - 1);

			productRepo.save(varolanUrun);
			
			
			if(varolanUrun.getQuantity() < varolanUrun.getCriticalStock()) {
				EmailDetails emailDetails = EmailDetails.builder()
						.to("icbozoglu@gmail.com")
						.subject("Kritik Stok Uyarısı")
						.body(String.format("%s isimli ürünün stok miktarı, kritik stok sınınırı olan %.2f altına düştü.",
								varolanUrun.getName(), varolanUrun.getCriticalStock()))
						.build();
				
				emailService.send(emailDetails);
				
			}
		}
	}

	public void stokArttır(Long id) {
		Product varolanUrun = idIleUrunGetir(id);
		varolanUrun.setQuantity(varolanUrun.getQuantity() + 1);

		productRepo.save(varolanUrun);
	}

//	public ProductService(ProductRepo productRepo) {
//		this.productRepo = productRepo;
//	}

}
