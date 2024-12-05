package com.sanjot.jobportal.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjot.jobportal.entity.IRecruiterJobs;
import com.sanjot.jobportal.entity.JobCompany;
import com.sanjot.jobportal.entity.JobLocation;
import com.sanjot.jobportal.entity.JobPostActivity;
import com.sanjot.jobportal.entity.RecruiterJobsDto;
import com.sanjot.jobportal.repository.JobPostActivityRepository;
import com.sanjot.jobportal.repository.JobSeekerSaveRepository;

import jakarta.transaction.Transactional;

@Service
public class JobPostActivityService {
	private final JobPostActivityRepository jobPostActivityRepository;
	private final JobSeekerSaveRepository jobSeekerSaveRepository;

	@Autowired
	public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository,JobSeekerSaveRepository jobSeekerSaveRepository) {
		this.jobPostActivityRepository = jobPostActivityRepository;
		this.jobSeekerSaveRepository=jobSeekerSaveRepository;
	}

	public JobPostActivity addNew(JobPostActivity jobPostActivity) {
		return jobPostActivityRepository.save(jobPostActivity);
	}

	public List<RecruiterJobsDto> getRecruiterJobs(int recruiter) {
		List<IRecruiterJobs> recruiterJobsDtos = jobPostActivityRepository.getRecruiterJobs(recruiter);
		List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();

		for (IRecruiterJobs rec : recruiterJobsDtos) {
			JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
			JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
			recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJob_post_id(),
					rec.getJob_title(), loc, comp));

		}
		return recruiterJobsDtoList;

	}

	public JobPostActivity getOne(int id) {
		return jobPostActivityRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found"));
	}
    
	@Transactional
	public void deletejobById(int id) {
		jobSeekerSaveRepository.deleteByJobPostId(id);
		jobPostActivityRepository.deleteById(id);
	}

	public List<JobPostActivity> getAll() {
		return jobPostActivityRepository.findAll();
	}

	public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote,
			LocalDate searchDate) {
		return Objects.isNull(searchDate) ? jobPostActivityRepository.searchWithoutDate(job, location, remote, type)
				: jobPostActivityRepository.search(job, location, remote, type, searchDate);

	}
	
}
