package com.bilgeadam.stok2.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bilgeadam.stok2.entity.UserEntity;

public interface UserRespository extends JpaRepository<UserEntity, Integer> {

	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findByEmail(String email);

}
