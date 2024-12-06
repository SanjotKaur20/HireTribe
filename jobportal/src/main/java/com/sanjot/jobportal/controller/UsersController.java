package com.sanjot.jobportal.controller;

import java.io.IOException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.BaseColor;

import com.itextpdf.text.Chunk;

import com.itextpdf.text.Document;

import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Element;

import com.itextpdf.text.Font;

import com.itextpdf.text.FontFactory;

import com.itextpdf.text.ListItem;

import com.itextpdf.text.PageSize;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.ListItem;
import com.sanjot.jobportal.entity.JobSeekerProfile;
import com.sanjot.jobportal.entity.NewsletterSubscriber;
import com.sanjot.jobportal.entity.RecruiterProfile;
import com.sanjot.jobportal.entity.Skills;
import com.sanjot.jobportal.entity.Users;
import com.sanjot.jobportal.entity.UsersType;
import com.sanjot.jobportal.repository.NewsletterSubscriberRepository;
import com.sanjot.jobportal.repository.UsersRepository;
import com.sanjot.jobportal.services.EmailService;
import com.sanjot.jobportal.services.JobSeekerProfileService;
import com.sanjot.jobportal.services.OtpService;
import com.sanjot.jobportal.services.RecruiterProfileService;
import com.sanjot.jobportal.services.UsersService;
import com.sanjot.jobportal.services.UsersTypeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UsersController {

	private final UsersTypeService usersTypeService;
	private final UsersService usersService;
	private final UsersRepository usersRepository;
	private final JobSeekerProfileService jobSeekerProfileService;
	private final RecruiterProfileService recruiterProfileService;

	@Autowired
	private OtpService otpService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private NewsletterSubscriberRepository subscriberRepository;

	@Autowired
	public UsersController(UsersTypeService usersTypeService, UsersService usersService,
			UsersRepository usersRepository, JobSeekerProfileService jobSeekerProfileService,
			RecruiterProfileService recruiterProfileService) {
		this.usersTypeService = usersTypeService;
		this.usersService = usersService;
		this.usersRepository = usersRepository;
		this.jobSeekerProfileService = jobSeekerProfileService;
		this.recruiterProfileService = recruiterProfileService;
	}

	@GetMapping("/register")
	public String register(Model model) {
		List<UsersType> usersTypes = usersTypeService.getAll();
		model.addAttribute("getAllTypes", usersTypes);
		model.addAttribute("user", new Users());
		return "register";
	}

	@PostMapping("/register/new")
	public String userRegistration(@Valid Users users, @RequestParam("email") String email, Model model) {
		Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
		String pass = users.getPassword();
		if (!(pass.contains("@") || pass.contains("#") || pass.contains("$") || pass.contains("&") || pass.contains("*")
				|| pass.contains("%"))) {
			model.addAttribute("error", "Password is must contain special character ");
			List<UsersType> usersTypes = usersTypeService.getAll();
			model.addAttribute("getAllTypes", usersTypes);
			model.addAttribute("user", new Users());
			return "register";

		}
		if (!(pass.contains("0") || pass.contains("1") || pass.contains("2") || pass.contains("3") || pass.contains("4")
				|| pass.contains("5") || pass.contains("6") || pass.contains("7") || pass.contains("8")
				|| pass.contains("9"))) {
			model.addAttribute("error", "Password must contain atleast one number ");
			List<UsersType> usersTypes = usersTypeService.getAll();
			model.addAttribute("getAllTypes", usersTypes);
			model.addAttribute("user", new Users());
			return "register";

		}

		String[] upperCaseAlphabets = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		boolean containsUpperCase = false;

		for (String letter : upperCaseAlphabets) {
			if (pass.contains(letter)) {
				containsUpperCase = true;
				break; // Exit loop once an uppercase letter is found
			}
		}

		if (optionalUsers.isPresent()) {
			model.addAttribute("error", "Email already registered !\n\n Try to login or register with new email!");
			List<UsersType> usersTypes = usersTypeService.getAll();
			model.addAttribute("getAllTypes", usersTypes);
			model.addAttribute("user", new Users());
			return "register";
		}
		String otp = otpService.generateOtp(email);
		emailService.sendOtpEmail(email, otp);

		model.addAttribute("message", "An OTP has been sent to your email address.");
		model.addAttribute("email", email);
		model.addAttribute("user", users);
		return "verify-otp2";
	}

	@PostMapping("/validate-otp2")
	public String validateOtpRegister(@RequestParam("email") String email, @RequestParam("otp1") String otp1,
			@RequestParam("otp2") String otp2, @RequestParam("otp3") String otp3, @RequestParam("otp4") String otp4,
			@RequestParam("otp5") String otp5, @RequestParam("otp6") String otp6,
			@RequestParam("password") String password, Model model) {
		String otp = otp1 + otp2 + otp3 + otp4 + otp5 + otp6;
		boolean isValid = otpService.validateOtp(email, otp);

		if (!isValid) {
			model.addAttribute("error", "Invalid OTP. Please try again.");
			model.addAttribute("email", email);
			return "verify-otp2";
		}
		model.addAttribute("email", email);
		Users users = new Users();
		users.setEmail(email);
		users.setPassword(password);

		usersService.addNew(users);
		return "redirect:/login";

	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "invalid email or password .Please try again");
		}
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/";
	}

	@GetMapping("/forget")
	public String forget() {
		return "forget-pass";
	}

	@PostMapping("/forget")
	public String sendOtp(@RequestParam("email") String email, Model model) {
		Optional<Users> userOptional = usersService.getUserByEmail(email);
		if (userOptional.isEmpty()) {
			model.addAttribute("error", "Email not found in our records");
			return "forget-pass";
		}
		String otp = otpService.generateOtp(email);
		emailService.sendOtpEmail(email, otp);

		model.addAttribute("message", "An OTP has been sent to your email address.");
		model.addAttribute("email", email);

		return "verify-otp";
	}

	@PostMapping("/validate-otp")
	public String validateOtp(@RequestParam("email") String email, @RequestParam("otp1") String otp1,
			@RequestParam("otp2") String otp2, @RequestParam("otp3") String otp3, @RequestParam("otp4") String otp4,
			@RequestParam("otp5") String otp5, @RequestParam("otp6") String otp6, Model model) {
		String otp = otp1 + otp2 + otp3 + otp4 + otp5 + otp6;
		boolean isValid = otpService.validateOtp(email, otp);

		if (!isValid) {
			model.addAttribute("error", "Invalid OTP. Please try again.");
			model.addAttribute("email", email);
			return "verify-otp";
		}
		model.addAttribute("email", email);
		return "reset-password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword, Model model) {
		if (email == null || email.isEmpty() || password == null || password.isEmpty() || confirmPassword == null
				|| confirmPassword.isEmpty()) {
			model.addAttribute("error", "All fields are required.");
			return "reset-password";
		}

		if (!password.equals(confirmPassword)) {
			model.addAttribute("error", "Passwords do not match.");
			return "reset-password";
		}

		try {
			usersService.updatePassword(email, password);
			model.addAttribute("message", "Your password has been reset successfully. Please log in.");
			return "login";
		} catch (UsernameNotFoundException e) {
			model.addAttribute("error", "No account found with the provided email: " + email);
			return "reset-password";
		} catch (Exception e) {
			model.addAttribute("error", "An unexpected error occurred. Please try again later.");
			return "reset-password";
		}
	}

	@GetMapping("/change-password")
	public String changePasswordPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			Users user = usersRepository.findByEmail(authentication.getName())
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));

			// Check for Job Seeker profile
			Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getOne(user.getUserId());
			if (jobSeekerProfile.isPresent()) {
				JobSeekerProfile seekerProfile = jobSeekerProfile.get();
				if (seekerProfile.getSkills().isEmpty()) {
					seekerProfile.setSkills(new ArrayList<>(List.of(new Skills())));
				}
				model.addAttribute("profile", seekerProfile);
				model.addAttribute("skills", seekerProfile.getSkills());
			}

			// Check for Recruiter profile
			Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user.getUserId());
			if (recruiterProfile.isPresent()) {
				RecruiterProfile profile = recruiterProfile.get();
				model.addAttribute("profile", profile);
				model.addAttribute("country", profile.getCountry());
				model.addAttribute("state", profile.getState());
				model.addAttribute("city", profile.getCity());
				model.addAttribute("companyName", profile.getCompany());
			}

			// Add general user details
			Object currentUserProfile = usersService.getCurrentUserProfile();
			model.addAttribute("user", currentUserProfile);
		}
		return "change-password";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
			Model model, Principal principal) {
		String email = principal.getName();
		Users user = usersService.getUserByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not Found"));
		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			model.addAttribute("error", "Current password is incorrect.");
			return "change-password";
		}
		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("error", "Your password confirmation does not match.");
			return "change-password";
		}
		usersService.updatePassword(email, newPassword);
		model.addAttribute("message", "Your password has been changed successfully");

		return "redirect:/dashboard/";
	}

	// Newsletter Subscription
	@PostMapping("/newsletter/subscribe")
	public String subscribeToNewsletter(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
		// Validate the email address format
		if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
			redirectAttributes.addFlashAttribute("error", "Invalid email address.");
			return "redirect:/Thankyou"; // Redirect to the Thankyou page with error message
		}

		// Check if the email is already subscribed
		if (subscriberRepository.existsByEmail(email)) {
			redirectAttributes.addFlashAttribute("error", "You are already subscribed.");
			return "redirect:/Thankyou"; // Redirect to the Thankyou page with error message
		}

		try {
			// Save email to the MySQL table
			NewsletterSubscriber subscriber = new NewsletterSubscriber();
			subscriber.setEmail(email);
			subscriberRepository.save(subscriber);

			// Send a confirmation email
			String subject = "Newsletter Subscription Confirmation";
			String message = "Dear User,\n\nThank you for subscribing to the Hire Tribe newsletter!\n\n"
					+ "We are thrilled to have you on board. By subscribing, you will receive regular updates on:\n\n"
					+ "- The latest job postings across various industries.\n"
					+ "- Insights on how to improve your resume and ace interviews.\n"
					+ "- Exclusive updates about upcoming job fairs and career events.\n\n"
					+ "At Hire Tribe, our mission is to connect job seekers with their dream opportunities and provide tools to support their career journey.\n\n"
					+ "Stay tuned, and we hope you find our newsletters helpful!\n\n"
					+ "Best regards,\nThe Hire Tribe Team";
			emailService.sendEmail(email, subject, message);

			// Redirect to the Thankyou page with success message
			redirectAttributes.addFlashAttribute("success", "Subscription successful! Check your email.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to process your request. Please try again.");
		}

		return "redirect:/Thankyou"; // Redirect to the Thankyou page
	}

	@GetMapping("/generate-resume")
	public String showForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			Users user = usersRepository.findByEmail(authentication.getName())
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));

			// Check for Job Seeker profile
			Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getOne(user.getUserId());
			if (jobSeekerProfile.isPresent()) {
				JobSeekerProfile seekerProfile = jobSeekerProfile.get();
				if (seekerProfile.getSkills().isEmpty()) {
					seekerProfile.setSkills(new ArrayList<>(List.of(new Skills())));
				}
				model.addAttribute("profile", seekerProfile);
				model.addAttribute("skills", seekerProfile.getSkills());
			}

			// Check for Recruiter profile
			Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user.getUserId());
			if (recruiterProfile.isPresent()) {
				RecruiterProfile profile = recruiterProfile.get();
				model.addAttribute("profile", profile);
				model.addAttribute("country", profile.getCountry());
				model.addAttribute("state", profile.getState());
				model.addAttribute("city", profile.getCity());
				model.addAttribute("companyName", profile.getCompany());
			}

			// Add general user details
			Object currentUserProfile = usersService.getCurrentUserProfile();
			model.addAttribute("user", currentUserProfile);
		}

		return "resume-form"; // Return the Thymeleaf form
	}

	@PostMapping("/generate-resume")
	public void generateStyledResume(@RequestParam("templateId") int templateId, // Add template ID
			@RequestParam("name") String name, @RequestParam("jobTitle") String jobTitle,
			@RequestParam("address") String address, @RequestParam("city") String city,
			@RequestParam("email") String email, @RequestParam("phone") String phone,
			@RequestParam("summary") String summary, @RequestParam("skillName") List<String> skillName,
			@RequestParam("skillExperience") List<String> skillExperience,
			@RequestParam("skillLevel") List<String> skillLevel, @RequestParam("projectName") List<String> projectName,
			@RequestParam("projectTime") List<String> projectTime,
			@RequestParam("projectDescription") List<String> projectDescription,
			@RequestParam("experienceTitle") List<String> experienceTitles,
			@RequestParam("experienceDuration") List<String> experienceDurations,
			@RequestParam("experienceDescription") List<String> experienceDescriptions,
			@RequestParam("educationInstitution") List<String> educationInstitutions,
			@RequestParam("educationYear") List<String> educationYears,
			@RequestParam("educationDegree") List<String> educationDegrees,
			@RequestParam("educationDescription") List<String> educationDescriptions, HttpServletResponse response)
			throws DocumentException, IOException {

		// Set response content type to PDF
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"resume.pdf\"");

		// Initialize Document and PdfWriter
		Document document = new Document(PageSize.A4, 36, 36, 72, 72);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		// Apply selected template based on templateId
		if (templateId == 1) {
			// Generate resume with Template 1 style
			generateTemplate1(document, name, jobTitle, address, city, email, phone, summary, skillName,
					skillExperience, skillLevel, projectName, projectTime, projectDescription, experienceTitles,
					experienceDurations, experienceDescriptions, educationInstitutions, educationYears,
					educationDegrees, educationDescriptions);
		} else if (templateId == 2) {
			// Generate resume with Template 2 style
			generateTemplate2(document, name, jobTitle, address, city, email, phone, summary, skillName,
					skillExperience, skillLevel, projectName, projectTime, projectDescription, experienceTitles,
					experienceDurations, experienceDescriptions, educationInstitutions, educationYears,
					educationDegrees, educationDescriptions);
		} else if (templateId == 3) {
			generateTemplate3(document, name, jobTitle, address, city, email, phone, summary, skillName,
					skillExperience, skillLevel, projectName, projectTime, projectDescription, experienceTitles,
					experienceDurations, experienceDescriptions, educationInstitutions, educationYears,
					educationDegrees, educationDescriptions);
		}

		document.close();
	}

	private void generateTemplate3(Document document, String name, String jobTitle, String address, String city,
			String email, String phone, String summary, List<String> skillName, List<String> skillExperience,
			List<String> skillLevel, List<String> projectName, List<String> projectTime,
			List<String> projectDescription, List<String> experienceTitles, List<String> experienceDurations,
			List<String> experienceDescriptions, List<String> educationInstitutions, List<String> educationYears,
			List<String> educationDegrees, List<String> educationDescriptions) throws DocumentException {
		// Fonts
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(34, 139, 34)); // Dodger blue
		Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(34, 139, 34)); // Forest
																											// green
		Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

		// Header
		Paragraph header = new Paragraph(name + "\n" + jobTitle, headerFont);
		header.setAlignment(Element.ALIGN_CENTER);
		header.setSpacingAfter(10);
		document.add(header);

		// Contact Information
		Paragraph contactInfo = new Paragraph("📍 " + address + ", " + city + "\n✉️ " + email + "\n📞 " + phone,
				textFont);
		contactInfo.setAlignment(Element.ALIGN_CENTER);
		contactInfo.setSpacingAfter(20);
		document.add(contactInfo);

		// Create Two Columns
		PdfPTable columns = new PdfPTable(2);
		columns.setWidthPercentage(100);
		columns.setWidths(new int[] { 1, 2 }); // Adjust column ratio as needed

		// Left Column: Skills and Education
		PdfPCell leftColumn = new PdfPCell();
		leftColumn.setBorder(PdfPCell.NO_BORDER);

		// Skills Section
		leftColumn.addElement(new Paragraph("Skills", sectionFont)); // Green heading
		for (int i = 0; i < skillName.size(); i++) {
			leftColumn.addElement(new Paragraph(
					"• " + skillName.get(i) + " (" + skillExperience.get(i) + ", Level: " + skillLevel.get(i) + ")",
					textFont));
		}
		leftColumn.addElement(Chunk.NEWLINE);

		// Education Section
		leftColumn.addElement(new Paragraph("Education", sectionFont)); // Green heading
		for (int i = 0; i < educationInstitutions.size(); i++) {
			leftColumn.addElement(new Paragraph("• " + educationDegrees.get(i) + " at " + educationInstitutions.get(i)
					+ " (" + educationYears.get(i) + ")", textFont));
			leftColumn.addElement(new Paragraph(educationDescriptions.get(i), textFont));
		}
		columns.addCell(leftColumn);

		// Right Column: Summary, Projects, and Experience
		PdfPCell rightColumn = new PdfPCell();
		rightColumn.setBorder(PdfPCell.NO_BORDER);

		// Profile Summary Section
		rightColumn.addElement(new Paragraph("Profile Summary", sectionFont)); // Green heading
		rightColumn.addElement(new Paragraph(summary, textFont));
		rightColumn.addElement(Chunk.NEWLINE);

		// Projects Section
		rightColumn.addElement(new Paragraph("Projects", sectionFont)); // Green heading
		for (int i = 0; i < projectName.size(); i++) {
			rightColumn
					.addElement(new Paragraph("• " + projectName.get(i) + " (" + projectTime.get(i) + ")", textFont));
			rightColumn.addElement(new Paragraph(projectDescription.get(i), textFont));
		}
		rightColumn.addElement(Chunk.NEWLINE);

		// Professional Experience Section
		rightColumn.addElement(new Paragraph("Professional Experience", sectionFont)); // Green heading
		for (int i = 0; i < experienceTitles.size(); i++) {
			rightColumn.addElement(
					new Paragraph("• " + experienceTitles.get(i) + " (" + experienceDurations.get(i) + ")", textFont));
			rightColumn.addElement(new Paragraph(experienceDescriptions.get(i), textFont));
		}
		columns.addCell(rightColumn);

		// Add Columns to Document
		document.add(columns);

		// Close Document
		document.close();
	}

	private void generateTemplate2(Document document, String name, String jobTitle, String address, String city,
			String email, String phone, String summary, List<String> skillName, List<String> skillExperience,
			List<String> skillLevel, List<String> projectName, List<String> projectTime,
			List<String> projectDescription, List<String> experienceTitles, List<String> experienceDurations,
			List<String> experienceDescriptions, List<String> educationInstitutions, List<String> educationYears,
			List<String> educationDegrees, List<String> educationDescriptions) throws DocumentException {

		// Header (Name and Contact Info)
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, new BaseColor(0, 51, 102));
		Font subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);

		Paragraph header = new Paragraph(name, headerFont);
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);

		Paragraph contactInfo = new Paragraph(address + " | " + city + " | " + email + " | " + phone, subHeaderFont);
		contactInfo.setAlignment(Element.ALIGN_CENTER);
		contactInfo.setSpacingAfter(10);
		document.add(contactInfo);

		// Divider Line
		LineSeparator line = new LineSeparator();
		line.setLineColor(BaseColor.BLACK);
		document.add(new Chunk(line));

		// Professional Summary Section
		Font sectionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new BaseColor(0, 51, 102));
		Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

		document.add(new Paragraph("Professional Summary", sectionTitleFont));
		document.add(new Paragraph(summary, bodyFont));
		document.add(Chunk.NEWLINE);

		// Work History Section
		document.add(new Paragraph("Work History", sectionTitleFont));
		for (int i = 0; i < experienceTitles.size(); i++) {
			Paragraph jobTitles = new Paragraph(experienceTitles.get(i),
					FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
			jobTitles.setSpacingBefore(10);
			document.add(jobTitles);

			Paragraph jobDuration = new Paragraph(experienceDurations.get(i), subHeaderFont);
			document.add(jobDuration);

			com.itextpdf.text.List experienceDetails = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
			experienceDetails.setListSymbol("\u2022");
			for (String detail : experienceDescriptions.get(i).split("\n")) {
				experienceDetails.add(new ListItem(detail, bodyFont));
			}
			document.add(experienceDetails);
		}

		// Education Section
		document.add(new Paragraph("Education", sectionTitleFont));
		for (int i = 0; i < educationInstitutions.size(); i++) {
			Paragraph institution = new Paragraph(educationInstitutions.get(i),
					FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
			institution.setSpacingBefore(10);
			document.add(institution);

			Paragraph degree = new Paragraph(educationDegrees.get(i) + " (" + educationYears.get(i) + ")",
					subHeaderFont);
			document.add(degree);

			document.add(new Paragraph(educationDescriptions.get(i), bodyFont));
			document.add(Chunk.NEWLINE);
		}

		document.add(Chunk.NEWLINE);
		com.itextpdf.text.List skillsList = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
		skillsList.setListSymbol("\u2022");
		document.add(new Paragraph("Skills", sectionTitleFont));
		for (int i = 0; i < skillName.size(); i++) {
			document.add(new Paragraph(
					skillName.get(i) + " - " + skillExperience.get(i) + " years (" + skillLevel.get(i) + ")",
					bodyFont));
		}
		document.add(Chunk.NEWLINE);

		// Footer
		document.add(Chunk.NEWLINE);
		Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY);
		Paragraph footer = new Paragraph("End of Resume", footerFont);
		footer.setAlignment(Element.ALIGN_CENTER);
		document.add(footer);

		document.close();
	}

	private void generateTemplate1(Document document, String name, String jobTitle, String address, String city,
			String email, String phone, String summary, List<String> skillName, List<String> skillExperience,
			List<String> skillLevel, List<String> projectName, List<String> projectTime,
			List<String> projectDescription, List<String> experienceTitles, List<String> experienceDurations,
			List<String> experienceDescriptions, List<String> educationInstitutions, List<String> educationYears,
			List<String> educationDegrees, List<String> educationDescriptions) throws DocumentException {

		// Header (Name and Contact Info)
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
		Font subHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);

		Paragraph header = new Paragraph(name, headerFont);
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);

		Paragraph contactInfo = new Paragraph(address + " | " + city + " | " + email + " | " + phone, subHeaderFont);
		contactInfo.setAlignment(Element.ALIGN_CENTER);
		contactInfo.setSpacingAfter(10);
		document.add(contactInfo);

		// Professional Summary Section
		Font sectionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
		Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);

		document.add(new Paragraph("Professional Summary", sectionTitleFont));
		LineSeparator line = new LineSeparator();
		line.setLineColor(BaseColor.BLACK);
		document.add(new Chunk(line));
		document.add(new Paragraph(summary, bodyFont));
		document.add(Chunk.NEWLINE);

		// Skills Section
		document.add(new Paragraph("Skills", sectionTitleFont));
		document.add(new Chunk(line)); // Add line separator after heading
		for (int i = 0; i < skillName.size(); i++) {
			document.add(new Paragraph(
					skillName.get(i) + " - " + skillExperience.get(i) + " years (" + skillLevel.get(i) + ")",
					bodyFont));
		}
		document.add(Chunk.NEWLINE);

		// Projects Section
		document.add(new Paragraph("Projects", sectionTitleFont));
		document.add(new Chunk(line)); // Add line separator after heading
		for (int i = 0; i < projectName.size(); i++) {
			Paragraph project = new Paragraph(projectName.get(i) + " (" + projectTime.get(i) + ")", bodyFont);
			project.setSpacingBefore(10);
			document.add(project);
			document.add(new Paragraph(projectDescription.get(i), bodyFont));
		}
		document.add(Chunk.NEWLINE);

		// Work History Section
		document.add(new Paragraph("Work History", sectionTitleFont));
		document.add(new Chunk(line)); // Add line separator after heading
		for (int i = 0; i < experienceTitles.size(); i++) {
			Paragraph jobTitles = new Paragraph(experienceTitles.get(i),
					FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
			jobTitles.setSpacingBefore(10);
			document.add(jobTitles);

			Paragraph jobDuration = new Paragraph(experienceDurations.get(i), subHeaderFont);
			document.add(jobDuration);

			com.itextpdf.text.List experienceDetails = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
			experienceDetails.setListSymbol("\u2022");
			for (String detail : experienceDescriptions.get(i).split("\n")) {
				experienceDetails.add(new ListItem(detail, bodyFont));
			}
			document.add(experienceDetails);
		}
		document.add(Chunk.NEWLINE);

		// Education Section
		document.add(new Paragraph("Education", sectionTitleFont));
		document.add(new Chunk(line)); // Add line separator after heading
		for (int i = 0; i < educationInstitutions.size(); i++) {
			Paragraph institution = new Paragraph(educationInstitutions.get(i),
					FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
			institution.setSpacingBefore(10);
			document.add(institution);

			Paragraph degree = new Paragraph(educationDegrees.get(i) + " (" + educationYears.get(i) + ")",
					subHeaderFont);
			document.add(degree);

			document.add(new Paragraph(educationDescriptions.get(i), bodyFont));
		}
		document.add(Chunk.NEWLINE);

		// Footer
		document.add(Chunk.NEWLINE);
		Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.GRAY);
		Paragraph footer = new Paragraph("End of Resume", footerFont);
		footer.setAlignment(Element.ALIGN_CENTER);
		document.add(footer);

		document.close();
	}

}
