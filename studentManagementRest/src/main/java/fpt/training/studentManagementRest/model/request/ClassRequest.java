package fpt.training.studentManagementRest.model.request;

import java.util.Date;

public class ClassRequest {
	
	private Date classTime;
	private String place;
	private int maxSlots;
	
	public ClassRequest(Date classTime, String place, int maxSlots) {
		super();
		this.classTime = classTime;
		this.place = place;
		this.maxSlots = maxSlots;
	}

	public ClassRequest() {
		super();
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

	public int getMaxSlots() {
		return maxSlots;
	}

	public void setMaxSlots(int maxSlots) {
		this.maxSlots = maxSlots;
	}
}
