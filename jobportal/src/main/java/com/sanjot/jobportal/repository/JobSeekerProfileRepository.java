package com.sanjot.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanjot.jobportal.entity.JobSeekerProfile;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer> {

}
