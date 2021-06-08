package fpt.training.studentManagementRest.model.request;

import java.util.Date;

public class StudentRequest {
	
	private String fullName;
	private boolean gender;
	private Date dob;
	private String address;
	private int maxCourseLoad;

	public StudentRequest(String fullName, boolean gender, Date dob, String address, int maxCourseLoad) {
		super();
		this.fullName = fullName;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.maxCourseLoad = maxCourseLoad;
	}

	public StudentRequest() {
		super();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getMaxCourseLoad() {
		return maxCourseLoad;
	}

	public void setMaxCourseLoad(int maxCourseLoad) {
		this.maxCourseLoad = maxCourseLoad;
	}
}
