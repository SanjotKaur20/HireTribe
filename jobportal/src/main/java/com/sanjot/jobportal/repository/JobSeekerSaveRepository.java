package com.sanjot.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sanjot.jobportal.entity.JobPostActivity;
import com.sanjot.jobportal.entity.JobSeekerProfile;
import com.sanjot.jobportal.entity.JobSeekerSave;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {
	List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

	List<JobSeekerSave> findByJob(JobPostActivity job);
	
	@Modifying
	@Query("DELETE FROM JobSeekerSave j WHERE j.job.id=:jobPostId")
	void deleteByJobPostId(@Param("jobPostId") int id);
}
