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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "class")
@JsonIgnoreProperties(  {"handler","hibernateLazyInitializer"} )
public class ClassEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
	private Subject subject;
	@Column(name = "class_time")
	@Temporal(TemporalType.DATE)
	private Date classTime;
	private String place;
	@Column(name = "is_deleted", columnDefinition = "bit default 0")
	private boolean isDeleted;
	@Column(name = "max_slots")
	private int maxSlots;
	@Transient
	private long availableSlots;
	@ManyToMany(mappedBy = "classList")
	private Set<Student> studentList;
	
	public ClassEntity(long id, Subject subject, Date classTime, String place, boolean isDeleted, int maxSlots) {
		super();
		this.id = id;
		this.subject = subject;
		this.classTime = classTime;
		this.place = place;
		this.isDeleted = isDeleted;
		this.maxSlots = maxSlots;
	}

	public ClassEntity(Subject subject, Date classTime, String place, boolean isDeleted, int maxSlots) {
		super();
		this.subject = subject;
		this.classTime = classTime;
		this.place = place;
		this.isDeleted = isDeleted;
		this.maxSlots = maxSlots;
	}

	public ClassEntity() {
		super();
		this.isDeleted = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Date getClassTime() {
		return classTime;
	}

	public void setClassTime(Date classTime) {
		this.classTime = classTime;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@JsonIgnore
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getMaxSlots() {
		return maxSlots;
	}

	public void setMaxSlots(int maxSlots) {
		this.maxSlots = maxSlots;
	}

	@JsonIgnore
	public long getAvailableSlots() {
		return availableSlots;
	}

	public void setAvailableSlots(long availableSlots) {
		this.availableSlots = availableSlots;
	}
	
	@JsonIgnore
	public Set<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(Set<Student> studentList) {
		this.studentList = studentList;
	}

	@Override
	public String toString() {
		return "ClassEntity [id=" + id + ", subject=" + subject + ", classTime=" + classTime + ", place=" + place
				+ ", isDeleted=" + isDeleted + ", maxSlots=" + maxSlots + "]";
	}
}
