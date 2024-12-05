package com.sanjot.jobportal.entity;

import java.beans.Transient;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="job_seeker_profile")
public class JobSeekerProfile {
	@Id
	private Integer userAccountId;
	@OneToOne
	@JoinColumn(name="user_account_id")
	@MapsId
	private Users userId;
	private String firstName;
	private String lastName;
	private String city;
	private String state;
	private String country;
	private String workAuthorization;
	private String employmentType;
	private String resume;
	@Column(nullable=true,length=64)
	private String profilePhoto;
	
	@OneToMany(targetEntity=Skills.class,cascade=CascadeType.ALL,mappedBy="jobSeekerProfile")
	private List<Skills> skills;
	
	public JobSeekerProfile(Users userId) {
		this.userId=userId;
	}
	
	public JobSeekerProfile() {
	}

	public JobSeekerProfile(Integer userAccountId, Users userId, String firstName, String lastName, String city,
			String state, String country, String workAuthorization, String employmentType, String profilePhoto,
			List<Skills> skills,String resume) {
		this.userAccountId = userAccountId;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.country = country;
		this.workAuthorization = workAuthorization;
		this.employmentType = employmentType;
		this.profilePhoto = profilePhoto;
		this.skills = skills;
		this.resume=resume;
	}

	public Integer getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Integer userAccountId) {
		this.userAccountId = userAccountId;
	}

	public Users getUserId() {
		return userId;
	}

	public void setUserId(Users userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWorkAuthorization() {
		return workAuthorization;
	}

	public void setWorkAuthorization(String workAuthorization) {
		this.workAuthorization = workAuthorization;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public List<Skills> getSkills() {
		return skills;
	}

	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}
	

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}
	@Transient
	public String getPhotosImagePath() {
		if(profilePhoto==null || userAccountId==null)
			return null;
		return "/photos/candidate/"+userAccountId+"/"+profilePhoto;
	}

	@Override
	public String toString() {
		return "JobSeekerProfile [userAccountId=" + userAccountId + ", userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", workAuthorization=" + workAuthorization + ", employmentType=" + employmentType + ", resume="
				+ resume + ", profilePhoto=" + profilePhoto + "]";
	}

	

}