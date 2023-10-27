package com.bilgeadam.stok2.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bilgeadam.stok2.entity.UserEntity;

public class CustomUserDetails implements UserDetails {
	
	private UserEntity userEntity;
	
	public CustomUserDetails(UserEntity user) {
		this.userEntity = user;
	}

	@Override
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<SimpleGrantedAuthority> yetkiler = new ArrayList<>();
		
		String roller = userEntity.getRoles(); // ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR, ROLE_EDITOR
		
		String[] arr = roller.split(",");
		
		for(String rol:arr) {
			yetkiler.add(new SimpleGrantedAuthority(rol));
		}
		
		return yetkiler;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userEntity.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
