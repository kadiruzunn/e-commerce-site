package com.bilgeadam.stok2.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="personnel")
public class Personel extends Person{
	
	private Department department;

}
