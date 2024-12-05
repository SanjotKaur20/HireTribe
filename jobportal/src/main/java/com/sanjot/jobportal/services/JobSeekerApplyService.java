package com.sanjot.jobportal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sanjot.jobportal.entity.JobPostActivity;
import com.sanjot.jobportal.entity.JobSeekerApply;
import com.sanjot.jobportal.entity.JobSeekerProfile;
import com.sanjot.jobportal.repository.JobSeekerApplyRepository;

@Service
public class JobSeekerApplyService {
	private final JobSeekerApplyRepository jobSeekerApplyRepository;

	public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
		this.jobSeekerApplyRepository = jobSeekerApplyRepository;
	}
	public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId){
		return jobSeekerApplyRepository.findByUserId(userAccountId);
	}
	public List<JobSeekerApply> getJobCandidates(JobPostActivity job){
		return jobSeekerApplyRepository.findByJob(job);
	}
	public void addNew(JobSeekerApply jobSeekerApply) {
		jobSeekerApplyRepository.save(jobSeekerApply);
	}
	
	

}
