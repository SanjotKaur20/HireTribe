package com.sanjot.jobportal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sanjot.jobportal.entity.Users;
import com.sanjot.jobportal.entity.UsersType;

public class CustomUserDetails implements UserDetails {
	private final Users user;
	

	public CustomUserDetails(Users user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		UsersType usersType =user.getUserTypeId();
		List<SimpleGrantedAuthority> authorities=new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(usersType.getUserTypeName()));
		return authorities;
		// TODO Auto-generated method stub
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

}
