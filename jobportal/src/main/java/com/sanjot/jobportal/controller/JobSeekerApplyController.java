package com.sanjot.jobportal.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.*;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sanjot.jobportal.entity.JobPostActivity;
import com.sanjot.jobportal.entity.JobSeekerApply;
import com.sanjot.jobportal.entity.JobSeekerProfile;
import com.sanjot.jobportal.entity.JobSeekerSave;
import com.sanjot.jobportal.entity.RecruiterProfile;
import com.sanjot.jobportal.entity.Users;
import com.sanjot.jobportal.services.EmailService;
import com.sanjot.jobportal.services.JobPostActivityService;
import com.sanjot.jobportal.services.JobSeekerApplyService;
import com.sanjot.jobportal.services.JobSeekerProfileService;
import com.sanjot.jobportal.services.JobSeekerSaveService;
import com.sanjot.jobportal.services.RecruiterProfileService;
import com.sanjot.jobportal.services.UsersService;

@Controller
public class JobSeekerApplyController {
	private final JobPostActivityService jobPostActivityService;
	private final UsersService usersService;
	private final JobSeekerApplyService jobSeekerApplyService;
	private final JobSeekerSaveService jobSeekerSaveService;
	private final RecruiterProfileService recruiterProfileService;
	private final JobSeekerProfileService jobSeekerProfileService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UsersService usersService,
			JobSeekerApplyService jobSeekerApplyService, JobSeekerSaveService jobSeekerSaveService,
			RecruiterProfileService recruiterProfileService, JobSeekerProfileService jobSeekerProfileService) {
		this.jobPostActivityService = jobPostActivityService;
		this.usersService = usersService;
		this.jobSeekerApplyService = jobSeekerApplyService;
		this.jobSeekerSaveService = jobSeekerSaveService;
		this.recruiterProfileService = recruiterProfileService;
		this.jobSeekerProfileService = jobSeekerProfileService;
	}

	@GetMapping("job-details-apply/{id}")
	public String display(@PathVariable("id") int id, Model model) {
		JobPostActivity jobDetails = jobPostActivityService.getOne(id);
		List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
		List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
				RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
				if (user != null) {
					model.addAttribute("applyList", jobSeekerApplyList);
	                model.addAttribute("country", user.getCountry());
	                model.addAttribute("state", user.getState());
	                model.addAttribute("city", user.getCity());
	                model.addAttribute("companyName", user.getCompany());


				}

			} else {
				JobSeekerProfile user = jobSeekerProfileService.getCurrentSeekerProfile();
				if (user != null) {
					boolean exists = false;
					boolean saved = false;
					for (JobSeekerApply jobSeekerApply : jobSeekerApplyList) {
						if (jobSeekerApply.getUserId().getUserAccountId() == user.getUserAccountId()) {

							exists = true;
							break;
						}
					}
					for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
						if (jobSeekerSave.getUserId().getUserAccountId() == user.getUserAccountId()) {

							saved = true;
							break;
						}
					}
					model.addAttribute("alreadyApplied", exists);
					model.addAttribute("alreadySaved", saved);
				}
			}
		}
		JobSeekerApply jobSeekerApply = new JobSeekerApply();
		model.addAttribute("applyJob", jobSeekerApply);

		model.addAttribute("jobDetails", jobDetails);
		model.addAttribute("users", usersService.getCurrentUserProfile());
		Object currentUserProfile = usersService.getCurrentUserProfile();
		model.addAttribute("user", currentUserProfile);

		return "job-details";
	}

	@PostMapping("job-details/apply/{id}")
	public String apply(@PathVariable("id") int id, JobSeekerApply jobSeekerApply) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Users user = usersService.findByEmail(currentUsername);
			Optional<JobSeekerProfile> jobseekerProfile = jobSeekerProfileService.getOne(user.getUserId());
			JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
			if (jobseekerProfile.isPresent() && jobPostActivity != null) {
				jobSeekerApply = new JobSeekerApply();
				jobSeekerApply.setUserId(jobseekerProfile.get());
				jobSeekerApply.setJob(jobPostActivity);
				jobSeekerApply.setApplyDate(new Date());

			} else {
				throw new RuntimeException("User not found");
			}
			jobSeekerApplyService.addNew(jobSeekerApply);
			
			String subject="Application Received:"+jobPostActivity.getJobTitle();
			String firstName = jobseekerProfile.isPresent() && jobseekerProfile.get().getFirstName() != null
				    ? jobseekerProfile.get().getFirstName()
				    : "Job Seeker";

				String lastName = jobseekerProfile.isPresent() && jobseekerProfile.get().getLastName() != null
				    ? jobseekerProfile.get().getLastName()
				    : "";

				String message = "Dear " + firstName + " " + lastName + ",\n\n" +
				    "Thank you for applying for the position of " + jobPostActivity.getJobTitle() + ".\n" +
				    "Your application has been received and is in the queue for review.\n\n" +
				    "We appreciate your interest and will get back to you soon.\n\n" +
				    "Best regards,\n" +
				    "The Hire Tribe Team";
		
			emailService.sendEmail(user.getEmail(),subject, message);
			
			
			Users recruiterUser = jobPostActivity.getPostedById();
			if(recruiterUser!=null) {
				String recruiterEmail=recruiterUser.getEmail();
				String recruiterSubject="New Application for Your Job Posting";

				String recruiterMessage = "Dear Recruiter,\n\n" +
				    "We are excited to inform you that a new candidate has applied for the position of *" + jobPostActivity.getJobTitle() + "* posted by you on Hire Tribe.\n\n" +
				    "Here are some details of the application:\n" +
				    "- **Candidate Name**: " + jobseekerProfile.get().getFirstName() + " " + jobseekerProfile.get().getLastName() + "\n" +
				    "- **Application Date**: " + LocalDate.now() + "\n" +
				    "- **Position**: " + jobPostActivity.getJobTitle() + "\n\n" +
				    "You can review the candidate's profile and application by logging into your dashboard at Hire Tribe.\n\n" +
				    "If you have any questions or need assistance, please contact us at hiretribe009@gmail.com.\n\n" +
				    "Best regards,\n" +
				    "The Hire Tribe Team";
				
				emailService.sendEmail(recruiterEmail, recruiterSubject, recruiterMessage);
				
			}
		}
		return "redirect:/dashboard/";
	}
	@GetMapping("/job-applied-seekers/{Id}")
	public String viewAppliedJobSeekers(@PathVariable("Id") int Id, Model model) {
		JobPostActivity jobDetails = jobPostActivityService.getOne(Id);

		List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
		model.addAttribute("applyList", jobSeekerApplyList);
        model.addAttribute("jobDetails", jobDetails);

		return "job-applied-seekers";
	}
	


}
