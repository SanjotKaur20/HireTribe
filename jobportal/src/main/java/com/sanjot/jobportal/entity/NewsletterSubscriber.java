package com.sanjot.jobportal.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "newsletter_subscribers") // Maps to your MySQL table
public class NewsletterSubscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

	public NewsletterSubscriber(Long id, String email) {
		this.id = id;
		this.email = email;
	}
	

	public NewsletterSubscriber() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "NewsletterSubscriber [id=" + id + ", email=" + email + "]";
	}
    

}
