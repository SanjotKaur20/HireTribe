package com.sanjot.jobportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sanjot.jobportal.services.CustomUserDetailsService;


@Configuration
public class WebSecurityConfig {
	private final CustomUserDetailsService customUserDetailsService;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	
	public WebSecurityConfig(CustomUserDetailsService customUserDetailsService,CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
		this.customUserDetailsService = customUserDetailsService;
		this.customAuthenticationSuccessHandler=customAuthenticationSuccessHandler;
	}
	private final String [] publicUrl= {"/",
			"/global-search/**",
			"/register",
			"/register/**",
			"/webjars/**",
			"/resources/**",
			"/assets/**",
			"/image/**",
			"/AboutUs/**",
			"/ContactUs/**",
			"/forget",
			"/validate-otp",
			"/reset-password",
			"/newsletter/subscribe",
			"/Thankyou",
			"/TwentyFour",
			"/feature",
			"PageResumeBuilder",
			"/TechStartUp",
			"/Companies",
			"/TermService",
			"/Privacy",
			"/static/**",
			"/css/**",
			"/summernote/**",
			"/js/**",
			"/*.css",
			"/*.js",
			"/*js.map",
			
			"/fonts**","/favicon.ico","/resources/**","/error"};
	
	
@Bean
protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
	http.authorizeHttpRequests(auth->{
		auth.requestMatchers(publicUrl).permitAll();
		auth.anyRequest().authenticated();
		
	});
	http.authenticationProvider(authenticationProvider());
	http.formLogin(form-> form.loginPage("/login").permitAll()
	.successHandler(customAuthenticationSuccessHandler)
	.failureUrl("/login?error=true"))
	.logout(logout->{
		logout.logoutUrl("/logout/");
		logout.logoutSuccessUrl("/");
	})
	.csrf(Customizer.withDefaults())
	.csrf(csrf->csrf.disable());
	return http.build();
	}
public AuthenticationProvider authenticationProvider() {
	DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
	authenticationProvider.setPasswordEncoder(passwordEncoder());
	authenticationProvider.setUserDetailsService(customUserDetailsService);
	
return authenticationProvider;	
}
@Bean
public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}
}
