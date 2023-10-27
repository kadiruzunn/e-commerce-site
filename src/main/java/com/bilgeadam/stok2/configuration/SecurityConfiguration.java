package com.bilgeadam.stok2.configuration;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.bilgeadam.stok2.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {
	
	@Autowired
	private final UserService service;
	
	public SecurityConfiguration(UserService service) {
		this.service = service;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorizeConfig -> {
			authorizeConfig.antMatchers("/").permitAll(); // anasayfaya(index.html) anonim kullanıcılar girebilsin
			authorizeConfig.antMatchers("/registration").permitAll();
			authorizeConfig.antMatchers("/error").permitAll();
			authorizeConfig.antMatchers("/favicon.ico").permitAll();
			authorizeConfig.anyRequest().authenticated(); // diğer sayfalar login olan kullanıcı girebilsin
		})
			
				.formLogin()// logout ve tekrar login olabilmek için
				.loginPage("/signin").permitAll()
				.loginProcessingUrl("/dologin").permitAll()
				.successHandler(new AuthenticationSuccessHandler() {
					
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
					
						Object principal = authentication.getPrincipal();

						
							UserDetails user = (UserDetails)principal;
							log.info("{} , {} sisteme giriş yaptı.",
									user.getUsername(), LocalDateTime.now());
						
							
						response.sendRedirect(request.getContextPath());
						
					}
				})
				.and()
				.logout(logout-> logout.logoutUrl("/logout")
						.addLogoutHandler(new LogoutHandler() {
							
							@Override
							public void logout(HttpServletRequest request, 
									HttpServletResponse response, 
									Authentication authentication) {
								
								if(authentication != null) { // kullanıcı login olduysa
									
									Object principal = authentication.getPrincipal();

									
										UserDetails user = (UserDetails)principal;
										log.info("{} , {} sistemden çıkış yaptı.",
												user.getUsername(), LocalDateTime.now());
									
								}
							}
						})
						.invalidateHttpSession(true)
						
						)
				.exceptionHandling()
				.accessDeniedPage("/accessdenied")
				.and()
				.build();

	}
	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public UserDetailsService userDetailService() {
//		
//		return new InMemoryUserDetailsManager(
//				User.builder()
//				.username("admin")
//				.password("{noop}123")
//				.authorities("ROLE_user")
//				.build()
//				);
//	}
}