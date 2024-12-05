package com.sanjot.jobportal.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
public class JobPostActivity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer JobPostId;

@ManyToOne
@JoinColumn(name="postedById",referencedColumnName = "userId")
private Users postedById;

@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
@JoinColumn(name = "jobLocationId",referencedColumnName = "Id")
private JobLocation jobLocationId;

@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
@JoinColumn(name = "jobCompanyId",referencedColumnName = "Id")
private JobCompany jobCompanyId;

@Transient
private Boolean isActive;

@Transient
private Boolean isSaved;

@Length(max=10000)
private String descriptionOfJob;
private String JobType;
private String salary;
private String remote;

@DateTimeFormat(pattern = "dd-MM-YYYY")
private Date postedDate;
private String JobTitle;
public JobPostActivity() {
}
public JobPostActivity(Integer jobPostId, Users postedById, JobLocation jobLocationId, JobCompany jobCompanyId,
		Boolean isActive, Boolean isSaved, @Length(max = 10000) String descriptionOfJob, String jobType, String salary,
		String remote, Date postedDate, String jobTitle) {
	JobPostId = jobPostId;
	this.postedById = postedById;
	this.jobLocationId = jobLocationId;
	this.jobCompanyId = jobCompanyId;
	this.isActive = isActive;
	this.isSaved = isSaved;
	this.descriptionOfJob = descriptionOfJob;
	JobType = jobType;
	this.salary = salary;
	this.remote = remote;
	this.postedDate = postedDate;
	JobTitle = jobTitle;
}
public Integer getJobPostId() {
	return JobPostId;
}
public void setJobPostId(Integer jobPostId) {
	JobPostId = jobPostId;
}
public Users getPostedById() {
	return postedById;
}
public void setPostedById(Users postedById) {
	this.postedById = postedById;
}
public JobLocation getJobLocationId() {
	return jobLocationId;
}
public void setJobLocationId(JobLocation jobLocationId) {
	this.jobLocationId = jobLocationId;
}
public JobCompany getJobCompanyId() {
	return jobCompanyId;
}
public void setJobCompanyId(JobCompany jobCompanyId) {
	this.jobCompanyId = jobCompanyId;
}
public Boolean getIsActive() {
	return isActive;
}
public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
}
public Boolean getIsSaved() {
	return isSaved;
}
public void setIsSaved(Boolean isSaved) {
	this.isSaved = isSaved;
}
public String getDescriptionOfJob() {
	return descriptionOfJob;
}
public void setDescriptionOfJob(String descriptionOfJob) {
	this.descriptionOfJob = descriptionOfJob;
}
public String getJobType() {
	return JobType;
}
public void setJobType(String jobType) {
	JobType = jobType;
}
public String getSalary() {
	return salary;
}
public void setSalary(String salary) {
	this.salary = salary;
}
public String getRemote() {
	return remote;
}
public void setRemote(String remote) {
	this.remote = remote;
}
public Date getPostedDate() {
	return postedDate;
}
public void setPostedDate(Date postedDate) {
	this.postedDate = postedDate;
}
public String getJobTitle() {
	return JobTitle;
}
public void setJobTitle(String jobTitle) {
	JobTitle = jobTitle;
}
@Override
public String toString() {
	return "JobPostActivity [JobPostId=" + JobPostId + ", postedById=" + postedById + ", jobLocationId=" + jobLocationId
			+ ", jobCompanyId=" + jobCompanyId + ", isActive=" + isActive + ", isSaved=" + isSaved
			+ ", descriptionOfJob=" + descriptionOfJob + ", JobType=" + JobType + ", salary=" + salary + ", remote="
			+ remote + ", postedDate=" + postedDate + ", JobTitle=" + JobTitle + "]";
}







}

