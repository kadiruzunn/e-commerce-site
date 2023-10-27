package com.bilgeadam.stok2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// POJO
public class EmailDetails {
	
	private String to;
	private String subject;
	private String body;

}
