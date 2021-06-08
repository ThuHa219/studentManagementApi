package fpt.training.studentManagementRest.model.request;

public class SubjectRequest {
	
	private String subjectName;
	private int courseLoad;
	
	public SubjectRequest(String subjectName, int courseLoad) {
		super();
		this.subjectName = subjectName;
		this.courseLoad = courseLoad;
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
}
