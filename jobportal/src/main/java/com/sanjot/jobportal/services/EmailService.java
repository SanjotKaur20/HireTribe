package com.sanjot.jobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	public void sendOtpEmail(String toEmail, String otp) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Your OTP Code");
		message.setText("Your OTP code is:" + otp);

		mailSender.send(message);
	}

	public void sendEmail(String to, String subject, String text) {
		// TODO Auto-generated method stub
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);
	}

}
