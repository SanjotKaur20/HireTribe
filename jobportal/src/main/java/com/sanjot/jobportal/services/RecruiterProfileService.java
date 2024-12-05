package com.sanjot.jobportal.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanjot.jobportal.entity.RecruiterProfile;
import com.sanjot.jobportal.entity.Users;
import com.sanjot.jobportal.repository.RecruiterProfileRepository;
import com.sanjot.jobportal.repository.UsersRepository;

@Service
public class RecruiterProfileService {
	private final RecruiterProfileRepository recruiterRepository;
	private final UsersRepository usersRepository;

	@Autowired
	public RecruiterProfileService(RecruiterProfileRepository recruiterRepository, UsersRepository usersRepository) {
		this.recruiterRepository = recruiterRepository;
		this.usersRepository = usersRepository;
	}

	public Optional<RecruiterProfile> getOne(Integer id) {
		return recruiterRepository.findById(id);
	}

	public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
		return recruiterRepository.save(recruiterProfile);
	}

	public RecruiterProfile getCurrentRecruiterProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Users users = usersRepository.findByEmail(currentUsername)
					.orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
			Optional<RecruiterProfile> recruiterProfile = getOne(users.getUserId());
			return recruiterProfile.orElse(null);

		} else
			return null;
	}
}
