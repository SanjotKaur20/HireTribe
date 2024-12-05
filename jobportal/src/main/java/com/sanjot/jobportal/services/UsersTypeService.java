package com.sanjot.jobportal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjot.jobportal.entity.UsersType;
import com.sanjot.jobportal.repository.UsersTypeRepository;
 @Service
public class UsersTypeService {
private final UsersTypeRepository usersTypeRepository;

@Autowired
public UsersTypeService(UsersTypeRepository usersTypeRepository) {
	this.usersTypeRepository = usersTypeRepository;
}
public List<UsersType>getAll(){
	return usersTypeRepository.findAll();
	}

}
