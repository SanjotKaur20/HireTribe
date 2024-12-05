package com.sanjot.jobportal.controller;

import java.util.Objects;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sanjot.jobportal.entity.RecruiterProfile;
import com.sanjot.jobportal.entity.Users;
import com.sanjot.jobportal.repository.UsersRepository;
import com.sanjot.jobportal.services.RecruiterProfileService;
import com.sanjot.jobportal.services.UsersService;
import com.sanjot.jobportal.util.FileUploadUtil;

import org.springframework.util.StringUtils;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {
	private final UsersRepository usersRepository;
	private final RecruiterProfileService recruiterProfileService;
	private final UsersService usersService;
	
	@Autowired
	public RecruiterProfileController(UsersRepository usersRepository,RecruiterProfileService recruiterProfileService,UsersService usersService) {
		this.usersRepository = usersRepository;
		this.recruiterProfileService=recruiterProfileService;
		this.usersService=usersService;
	}
	@GetMapping("/")
	public String recruiterProfile(Model model) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Users users=usersRepository.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("Could not found user"));
		Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(users.getUserId());
		if(!recruiterProfile.isEmpty()) {
			model.addAttribute("profile",recruiterProfile.get());
			Object currentUserProfile=usersService.getCurrentUserProfile();
			model.addAttribute("user",currentUserProfile);

		}
        if (recruiterProfile.isPresent()) {
            RecruiterProfile profile = recruiterProfile.get();
            model.addAttribute("profile", profile);
            model.addAttribute("user", usersService.getCurrentUserProfile());
            
            // Add individual fields for sidebar display
            model.addAttribute("country", profile.getCountry());
            model.addAttribute("state", profile.getState());
            model.addAttribute("city", profile.getCity());
            model.addAttribute("companyName", profile.getCompany());
        }

		}
		return "recruiter_profile";
	}
	@PostMapping("/addNew")
	public String addNew(RecruiterProfile recruiterProfile,@RequestParam("image") MultipartFile multipartFile ,Model model) {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Users users=usersRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("Could not found user"));
			recruiterProfile.setUserId(users);
			recruiterProfile.setUserAccountId(users.getUserId());
		}
		model.addAttribute("profile",recruiterProfile);
		String fileName="";
		if(!multipartFile.getOriginalFilename().equals("")) {
			fileName=StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
			recruiterProfile.setProfilePhoto(fileName);
		}
		RecruiterProfile savedUser=recruiterProfileService.addNew(recruiterProfile);
		String uploadDir="photos/recruiter/"+savedUser.getUserAccountId();
		try {
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			}
		return "redirect:/dashboard/";
	}
	
	

}
