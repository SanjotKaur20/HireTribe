package com.sanjot.jobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanjot.jobportal.entity.Users;
import com.sanjot.jobportal.repository.UsersRepository;
import com.sanjot.jobportal.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final UsersRepository usersRepository;
	
	@Autowired
	public CustomUserDetailsService(UsersRepository usersRepository) {
		this.usersRepository=usersRepository;
	}
	@Override
	public UserDetails loadUserByUsername (String username)throws 
	UsernameNotFoundException{
		Users user= usersRepository.findByEmail(username).
	orElseThrow(()->new UsernameNotFoundException("Could not found the user"));
		return new CustomUserDetails(user);
	}

}
