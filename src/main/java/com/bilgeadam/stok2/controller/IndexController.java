package com.bilgeadam.stok2.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	
	static final String INDEX = "index";
	static final String REDIRECT_INDEX = "redirect:/index";
	static final String ALT_SAYFA = "altsayfa";
	static final String REDIRECT = "redirect:/";
	
	@GetMapping("/") 
	public String index(Model model) {
		model.addAttribute("adsoyad", "İzzet bozoğlu");
		return INDEX;
	}
	
	@GetMapping("altsayfa") 
	public String altsayfa() {
		return ALT_SAYFA;
	}
	
	@GetMapping("locale") 
	public String locale(@RequestParam String language, HttpServletRequest request) {
		
		String referer = request.getHeader("referer"); 
		String refPage = referer.substring(referer.lastIndexOf("/") + 1);
		
		return REDIRECT + refPage;
	}

}
