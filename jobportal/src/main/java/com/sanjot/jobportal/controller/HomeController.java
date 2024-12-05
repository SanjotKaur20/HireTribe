package com.sanjot.jobportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		return "index";
	}
	@GetMapping("/AboutUs")
	public String AboutUs() {
		return "AboutUs";
	}
	@GetMapping("/Thankyou")
	public String showThankyouPage() {
	    return "Thankyou"; // This should match the filename of the Thymeleaf template
	}
	@GetMapping("/TwentyFour")
	public String AlltymSupport() {
	    return "TwentyFour"; // This should match the filename of the Thymeleaf template
	}
	@GetMapping("/feature")
	public String feature() {
		return "feature";
	}

	@GetMapping("/PageResumeBuilder")
	public String FeatureResume() {
		return "PageResumeBuilder";
	}
	@GetMapping("/TechStartUp")
	public String TechStack() {
		return "TechStartUp";
	}
	@GetMapping("/Companies")
	public String Company() {
		return "Companies";
	}
	@GetMapping("/TermService")
	public String Term() {
		return "TermService";
	}
	@GetMapping("/Privacy")
	public String Privacy() {
		return "Privacy";
	}


	


}
