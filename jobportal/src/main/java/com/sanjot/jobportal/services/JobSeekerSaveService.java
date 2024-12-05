package com.sanjot.jobportal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjot.jobportal.entity.JobPostActivity;
import com.sanjot.jobportal.entity.JobSeekerProfile;
import com.sanjot.jobportal.entity.JobSeekerSave;
import com.sanjot.jobportal.repository.JobSeekerSaveRepository;

@Service
public class JobSeekerSaveService {
	private final JobSeekerSaveRepository jobSeekerSaveRepository;

	@Autowired
	public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
		this.jobSeekerSaveRepository = jobSeekerSaveRepository;
	}

	public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {

		return jobSeekerSaveRepository.findByUserId(userAccountId);
	}

	public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
		return jobSeekerSaveRepository.findByJob(job);
	}

	public void addNew(JobSeekerSave jobSeekerSave) {
		jobSeekerSaveRepository.save(jobSeekerSave);
	}
}
