package com.sanjot.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanjot.jobportal.entity.JobPostActivity;
import com.sanjot.jobportal.entity.JobSeekerApply;
import com.sanjot.jobportal.entity.JobSeekerProfile;

public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer>{
List<JobSeekerApply> findByUserId(JobSeekerProfile userId);
List<JobSeekerApply> findByJob(JobPostActivity job);
}
