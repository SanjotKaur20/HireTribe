package com.sanjot.jobportal.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanjot.jobportal.entity.JobSeekerProfile;
import com.sanjot.jobportal.entity.Skills;
import com.sanjot.jobportal.entity.Users;
import com.sanjot.jobportal.repository.UsersRepository;
import com.sanjot.jobportal.services.JobSeekerProfileService;
import com.sanjot.jobportal.services.UsersService;
import com.sanjot.jobportal.util.FileDownloadUtil;
import com.sanjot.jobportal.util.FileUploadUtil;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {
	private final JobSeekerProfileService jobSeekerProfileService;
	private final UsersRepository usersRepository;
	private final UsersService usersService;

	@Autowired
	public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService, UsersRepository usersRepository,
			UsersService usersService) {
		this.jobSeekerProfileService = jobSeekerProfileService;
		this.usersRepository = usersRepository;
		this.usersService = usersService;
	}

	@GetMapping("/")
	public String jobSeekerProfile(Model model) {
		JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Skills> skills = new ArrayList<>();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			Users user = usersRepository.findByEmail(authentication.getName())
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));

			Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
			if (seekerProfile.isPresent()) {
				jobSeekerProfile = seekerProfile.get();
				if (jobSeekerProfile.getSkills().isEmpty()) {
					skills.add(new Skills());
					jobSeekerProfile.setSkills(skills);
				}
			}

			model.addAttribute("skills", skills);
			model.addAttribute("profile", jobSeekerProfile);
		}
		Object currentUserProfile = usersService.getCurrentUserProfile();
		model.addAttribute("user", currentUserProfile);

		return "job-seeker-profile";
	}

	@PostMapping("/addNew")
	public String addNew(JobSeekerProfile jobSeekerProfile, @RequestParam("image") MultipartFile image,
			@RequestParam("pdf") MultipartFile pdf, RedirectAttributes redirectAttributes, Model model) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				Users user = usersRepository.findByEmail(authentication.getName())
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));

				jobSeekerProfile.setUserId(user);
				jobSeekerProfile.setUserAccountId(user.getUserId());
			}

			if (jobSeekerProfile.getSkills() == null) {
				jobSeekerProfile.setSkills(new ArrayList<>());
			}
			List<Skills> cleanedSkills = jobSeekerProfile.getSkills().stream()
					.filter(skill -> skill.getName() != null && !skill.getName().isEmpty())
					.collect(Collectors.toList());
			jobSeekerProfile.setSkills(cleanedSkills);

			model.addAttribute("profile", jobSeekerProfile);
			model.addAttribute("skills", jobSeekerProfile.getSkills());

			for (Skills skill : jobSeekerProfile.getSkills()) {
				skill.setJobSeekerProfile(jobSeekerProfile);
			}

			String imageName = "";
			String resumeName = "";

			if (!Objects.equals(image.getOriginalFilename(), "")) {
				imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
				jobSeekerProfile.setProfilePhoto(imageName);
			}
			if (!Objects.equals(pdf.getOriginalFilename(), "")) {
				resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
				jobSeekerProfile.setResume(resumeName);
			}

			jobSeekerProfileService.addNew(jobSeekerProfile);

			String uploadDir = "photos/candidate/" + jobSeekerProfile.getUserAccountId();
			if (!Objects.equals(image.getOriginalFilename(), "")) {
				FileUploadUtil.saveFile(uploadDir, imageName, image);
			}
			if (!Objects.equals(pdf.getOriginalFilename(), "")) {
				FileUploadUtil.saveFile(uploadDir, resumeName, pdf);
			}

			// Add a success message
			redirectAttributes.addFlashAttribute("successMessage", "Your profile has been saved successfully!");
		} catch (Exception e) {
			// Add an error message
			redirectAttributes.addFlashAttribute("errorMessage",
					"An error occurred while saving your profile. Please try again.");
		}

		// Redirect to the success page
		return "redirect:/job-seeker-profile/success";
	}

	@GetMapping("/success")
	public String showSuccessPage() {
		return "success"; // The view name for the success page
	}

	@GetMapping("/{id}")
	public String candidateProfile(@PathVariable("id") int id, Model model) {
		Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(id);
		model.addAttribute("profile", seekerProfile.get());
		return "job-seeker-profile";

	}

	@GetMapping("/downloadResume")
	public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String fileName,
			@RequestParam(value = "userID") String userId) {

		FileDownloadUtil fileDownloadUtil = new FileDownloadUtil();
		Resource resource = null;
		try {
			resource = fileDownloadUtil.getFileResource("photos/candidate/" + userId, fileName);

		} catch (IOException io) {
			return ResponseEntity.badRequest().build();
		}

		if (resource == null) {
			return new ResponseEntity<>("File not Found", HttpStatus.NOT_FOUND);

		}
		String contentType = "application/octet-stream";
		String headerValue = "attachment;filename=\"" + resource.getFilename() + "\"";
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);

	}

	@GetMapping("/viewResume")
	public ResponseEntity<?> viewResume(@RequestParam("fileName") String fileName, @RequestParam("userID") int userId) {
		String filePath = "photos/candidate/" + userId + "/" + fileName;

		try {
			Path path = Paths.get(filePath);
			Resource resource = new UrlResource(path.toUri());
			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_PDF)
						.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + fileName + "\"")
						.body(resource);
			}

			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resume not Found");

			}

		} catch (IOException io) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file");
		}
	}
	
}