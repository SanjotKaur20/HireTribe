
package com.sanjot.jobportal.services;
import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanjot.jobportal.entity.JobSeekerProfile;
import com.sanjot.jobportal.entity.RecruiterProfile;
import com.sanjot.jobportal.entity.Users;
import com.sanjot.jobportal.repository.JobSeekerProfileRepository;
import com.sanjot.jobportal.repository.RecruiterProfileRepository;
import com.sanjot.jobportal.repository.UsersRepository;

@Service
public  class UsersService{
    private final UsersRepository usersRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository,RecruiterProfileRepository recruiterProfileRepository,JobSeekerProfileRepository jobSeekerProfileRepository,PasswordEncoder passwordEncoder){
    	
        this.usersRepository=usersRepository;
        this.jobSeekerProfileRepository=jobSeekerProfileRepository;
        this.recruiterProfileRepository=recruiterProfileRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser=usersRepository.save(users);
        int userTypeId=users.getUserTypeId().getUserTypeId();
        if(userTypeId==1) {
        	recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else {
        	jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        	
        }
        return savedUser;
    }
    public Optional <Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }
    public Object getCurrentUserProfile() {
    	Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    	
    	if(!(authentication instanceof AnonymousAuthenticationToken)) {
    		String username=authentication.getName();
    		Users users = usersRepository.findByEmail(username)
    				.orElseThrow(()->new UsernameNotFoundException("Could not found "+ "user"));
    		int userId =users.getUserId();
    		
    		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
    			RecruiterProfile recruiterProfile=recruiterProfileRepository.findById(userId)
    					.orElse(new RecruiterProfile());
    			return recruiterProfile;
    			
    		}else {
    			JobSeekerProfile jobSeekerProfile=jobSeekerProfileRepository.findById(userId)
    					.orElse(new JobSeekerProfile());
    			return jobSeekerProfile;
    		}
    	}
    	return null;
    }
    public Users getCurrentUser() {
Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    	
    	if(!(authentication instanceof AnonymousAuthenticationToken)) {
    		String username=authentication.getName();
    		Users users = usersRepository.findByEmail(username)
    				.orElseThrow(()->new UsernameNotFoundException("Could not found "+ "user"));
    	return users;
    	}
    	return null;
    }
    public Users findByEmail(String currentUsername) {
    	return usersRepository.findByEmail(currentUsername)
    			.orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
    public void updatePassword(String email,String newPassword) {
    	Optional<Users> userOptional=usersRepository.findByEmail(email);
    	if(userOptional.isPresent()) {
    		Users user=userOptional.get();
    		user.setPassword(passwordEncoder.encode(newPassword));
    		usersRepository.save(user);
    		
    	}else {
    		throw new UsernameNotFoundException("User with email"+email+"not found");
    	}
    }
}