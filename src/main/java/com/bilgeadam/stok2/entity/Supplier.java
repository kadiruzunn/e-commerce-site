package com.bilgeadam.stok2.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="suppliers")
public class Supplier extends Person{
	
	private BigDecimal debt;

}
