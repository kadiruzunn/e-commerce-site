package com.bilgeadam.stok2.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bilgeadam.stok2.dto.CustomUserDetails;
import com.bilgeadam.stok2.entity.UserEntity;
import com.bilgeadam.stok2.repo.UserRespository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRespository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		UserEntity userEntity = repo.findByUsername(username).get();
		
		CustomUserDetails userDetails = new CustomUserDetails(userEntity);
		
		return userDetails;
	}

	public void save(@Valid UserEntity user) throws IllegalArgumentException{
		
	    Optional<UserEntity> existingUser = repo.findByEmail(user.getEmail());
	    
	    if(existingUser.isPresent()) {
	    	throw new IllegalArgumentException("Girilen email adresi ile daha önce sisteme kayıt yapılmıştır.");
	    }
		
		repo.save(user);
	}

}
