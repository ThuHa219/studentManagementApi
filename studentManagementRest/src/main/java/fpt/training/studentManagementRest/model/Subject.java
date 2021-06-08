package fpt.training.studentManagementRest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(length = 50, name = "subject_name")
	private String subjectName;
	@Column(name = "course_load")
	private int courseLoad;
	@Column(name = "is_deleted", columnDefinition = "bit default 0")
	private boolean isDeleted;
	
	public Subject(long id, String subjectName, int courseLoad, boolean isDeleted) {
		super();
		this.id = id;
		this.subjectName = subjectName;
		this.courseLoad = courseLoad;
		this.isDeleted = isDeleted;
	}

	public Subject(String subjectName, int courseLoad, boolean isDeleted) {
		super();
		this.subjectName = subjectName;
		this.courseLoad = courseLoad;
		this.isDeleted = isDeleted;
	}

	public Subject() {
		super();
		this.isDeleted = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getCourseLoad() {
		return courseLoad;
	}

	public void setCourseLoad(int courseLoad) {
		this.courseLoad = courseLoad;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Override
	public String toString() {
		return "Subject [id=" + id + ", subjectName=" + subjectName + ", courseLoad=" + courseLoad + ", isDeleted="
				+ isDeleted + "]";
	}
}
