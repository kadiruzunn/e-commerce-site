package com.bilgeadam.stok2.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bilgeadam.stok2.entity.UserEntity;
import com.bilgeadam.stok2.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserService service;
	
	@GetMapping("/signin")
	public String giris() {	
		return "signin";
	}
	
	@PostMapping("/dologin")
	public String girisSagla() {	
		return "/";
	}
	
	@GetMapping("/accessdenied")
	public String ersimEngelendi() {	
		return "accessdenied";
	}
	
	@GetMapping("/registration")
	public String yeniKullanici(Model model) {
		
		model.addAttribute("user", new UserEntity());
		
		return "registration";
	}

	@PostMapping("/registration")
	public String yeniKullaniciKaydet(@Valid @ModelAttribute UserEntity user, BindingResult result, Model model,
			HttpSession session) {

		model.addAttribute("user",user);
		
		user.setUsername(user.getEmail());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER,");
		
		try {
			service.save(user);
		} catch (IllegalArgumentException e) {
			user.setPassword("");
			session.setAttribute("msg", e.getMessage());
			return "/registration";
		}
		
		return "/singin";
	}
}
