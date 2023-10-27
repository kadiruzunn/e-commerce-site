package com.bilgeadam.stok2.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bilgeadam.stok2.dto.EmailDetails;

@Service("hotmail")
//@Primary
public class EmailUsingHotmail implements EmailService {

	@Override
	public void send(EmailDetails details) {
		// TODO Auto-generated method stub
		
	}
	

}
