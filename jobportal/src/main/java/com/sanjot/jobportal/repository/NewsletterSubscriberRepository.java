package com.sanjot.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sanjot.jobportal.entity.NewsletterSubscriber;

public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriber, Long> {
    boolean existsByEmail(String email); // Check for duplicate emails
}
