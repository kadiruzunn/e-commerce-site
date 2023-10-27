package com.bilgeadam.stok2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bilgeadam.stok2.dto.EmailDetails;

@Service("gmail")
public class EmailUsingGmail implements EmailService{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender;
	
	@Override
	public void send(EmailDetails details) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(sender);
		mail.setTo(details.getTo());
		mail.setSubject(details.getSubject());
		mail.setText(details.getBody());
		
		javaMailSender.send(mail);
		
	}

}
