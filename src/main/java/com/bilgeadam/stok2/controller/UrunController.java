package com.bilgeadam.stok2.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bilgeadam.stok2.entity.Product;
import com.bilgeadam.stok2.service.CategoryService;
import com.bilgeadam.stok2.service.FileStorageService;
import com.bilgeadam.stok2.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UrunController {

	private final ProductService productService;
	private final CategoryService categoryService;
	private final FileStorageService fileService;

	static final String URUNLER = "urunler";
	static final String REDIRECT_URUNLER = "redirect:/urunler?pageNo=1&recordCount=10&sortBy=id&direction=asc&keyword=";
	static final String URUN_DETAY = "urundetay";
	static final String YENI_URUN = "yeniurun";
	static final String URUN_GUNCELLE = "urunguncelle";
	
	@GetMapping("/stokeksi/{id}")
	public String stoktanBirOlcuEksilt(@PathVariable Long id) {
		
		productService.stokEksilt(id);
		
		return REDIRECT_URUNLER;
	}
	
	@GetMapping("/stokarti/{id}")
	public String stoktanBirOlcuArttır(@PathVariable Long id) {
		
		productService.stokArttır(id);
		
		return REDIRECT_URUNLER;
	}

	@GetMapping("/urunguncelle/{id}")
	public String urunguncelle(@PathVariable Long id, Model model) {

		Product guncellenecekUrun = productService.idIleUrunGetir(id);

		Map<Integer, String> kategoriler = categoryService.kategoriMap();

		model.addAttribute("kategoriler", kategoriler);
		model.addAttribute("urun", guncellenecekUrun);
		model.addAttribute("secilenKategori", guncellenecekUrun.getCategory().getId());

		return URUN_GUNCELLE;
	}

	@PostMapping("/urunguncelle/{id}")
	public String urunguncelle(@PathVariable Long id, @Valid @ModelAttribute Product product, BindingResult result,
			Model model, @RequestParam("img") MultipartFile img) {

		if (result.hasErrors()) {

			Map<Integer, String> kategoriler = categoryService.kategoriMap();

			model.addAttribute("kategoriler", kategoriler);
			model.addAttribute("urun", product);
			model.addAttribute("secilenKategori", product.getCategory().getId());
			
			return URUN_GUNCELLE;
		}
		
		if(img.getOriginalFilename() != null) {
			try {
				fileService.save(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			product.setImage(img.getOriginalFilename());
		}
		
		productService.guncelle(id, product);
		
		return REDIRECT_URUNLER;
	}

	@GetMapping("/search")
	public String ara(String keyword) {
		return REDIRECT_URUNLER + keyword; // keyword=Lemon
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/urunler")
	public String urunler(Model model, @RequestParam Integer pageNo, @RequestParam Integer recordCount, 
			@RequestParam String sortBy,@RequestParam String direction, String keyword ) {
		pageNo = pageNo == null ? 1 : pageNo;
		recordCount = recordCount == null ? 10 : recordCount;
		sortBy = sortBy.isEmpty() ? "id" : sortBy;
		direction = direction.isEmpty()? "asc" : direction;
		
		//sortBy.equals("name") // NullPointerException
		model.addAttribute("nameLabel", "Adı");
		
		if("name".equals(sortBy)) {
			if("desc".equals(direction)) {
				model.addAttribute("nameLabel", "Adı ^");
				model.addAttribute("nameSortDirection", "asc");
			}else {
				model.addAttribute("nameLabel", "Adı v");
				model.addAttribute("nameSortDirection", "desc");
			}
		}
		
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("recordCount", recordCount);
		
		Page<Product> page = productService.urunler(pageNo, recordCount, sortBy, direction, keyword);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		model.addAttribute("keyword", keyword);
		
		List<Product> liste = page.getContent(); //productService.urunler(pageNo, recordCount, sortBy, direction);
		model.addAttribute("urunler", liste);
		return URUNLER;
	}

	@GetMapping("/urunsil/{id}")
	public String urunSil(@PathVariable Long id) {
		productService.sil(id);
		return REDIRECT_URUNLER;
	}

	@GetMapping("/urundetay/{id}")
	public String urunDetay(@PathVariable Long id, Model model) {
		Product p = productService.idIleUrunGetir(id);

		model.addAttribute("urun", p);
		return URUN_DETAY;
	}

	@GetMapping("/yeniurun") // ürün bilgilerinin girişi yapılacak sayfanın boş halde açılmasını sağlar
	public String yeniUrun(Model model) {

		Map<Integer, String> kategoriler = categoryService.kategoriMap();

		model.addAttribute("kategoriler", kategoriler);
		model.addAttribute("urun", new Product());

		return YENI_URUN;
	}

	@PostMapping("/yeniurun")
	public String yeniUrunKaydet(@Valid @ModelAttribute("urun") Product product, BindingResult result,
			@RequestParam("img") MultipartFile img, Model model) {
		if (result.hasErrors()) {
			Map<Integer, String> kategoriler = categoryService.kategoriMap();

			model.addAttribute("kategoriler", kategoriler);
			product.setImage(img.getOriginalFilename());
			model.addAttribute("img", img);
			model.addAttribute("urun", product);
			model.addAttribute("secilenKategori", product.getCategory().getId());
			return YENI_URUN;
		}

		try {
			fileService.save(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String resim = product.getImage();

		product.setImage(resim.substring(resim.lastIndexOf("\\") + 1));

		productService.kaydet(product);

		return REDIRECT_URUNLER;
	}

	@GetMapping("img/{fileName:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable String fileName) {

		Resource file = fileService.load(fileName);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
