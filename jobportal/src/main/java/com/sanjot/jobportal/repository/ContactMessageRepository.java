package com.sanjot.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanjot.jobportal.entity.ContactMessage;

public interface ContactMessageRepository  extends JpaRepository<ContactMessage, Integer>  {

}
