package fpt.training.studentManagementRest.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "fullName")
	private String fullName;
	private boolean gender;
	@Temporal(TemporalType.DATE)
	private Date dob;
	private String address;
	@Column(name = "is_deleted", columnDefinition = "bit default 0")
	private boolean isDeleted;
	@Column(name = "max_course_load")
	private int maxCourseLoad;
	@OneToOne(mappedBy = "student", cascade = CascadeType.MERGE)
	private UserAccount userAccount;
	@ManyToMany
	@JoinTable(name = "assignment", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "class_id"))
	private Set<ClassEntity> classList;

	public Student(long id, String fullName, boolean gender, Date dob, String address, boolean isDeleted,
			int maxCourseLoad) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.isDeleted = isDeleted;
		this.maxCourseLoad = maxCourseLoad;
	}

	public Student(String fullName, boolean gender, Date dob, String address, boolean isDeleted, int maxCourseLoad) {
		super();
		this.fullName = fullName;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.isDeleted = isDeleted;
		this.maxCourseLoad = maxCourseLoad;
	}

	public Student() {
		super();
		this.isDeleted = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	@JsonIgnore
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getMaxCourseLoad() {
		return maxCourseLoad;
	}

	public void setMaxCourseLoad(int maxCourseLoad) {
		this.maxCourseLoad = maxCourseLoad;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", fullName=" + fullName + ", gender=" + gender + ", dob=" + dob + ", place="
				+ address + ", isDeleted=" + isDeleted + ", maxCourseLoad=" + maxCourseLoad + "]";
	}
	
	@JsonManagedReference
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@JsonIgnore
	public Set<ClassEntity> getClassList() {
		return classList;
	}

	public void setClassList(Set<ClassEntity> classList) {
		this.classList = classList;
	}
}
