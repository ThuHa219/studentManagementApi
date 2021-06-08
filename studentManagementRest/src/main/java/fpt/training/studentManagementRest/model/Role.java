package fpt.training.studentManagementRest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String roleName;
	@Column(name = "is_deleted", columnDefinition = "bit default 0")
	private boolean isDeleted;
	
	public Role(long id, String roleName, boolean isDeleted) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.isDeleted = isDeleted;
	}
	
	public Role(String roleName) {
		this.roleName = roleName;
	}

	public Role() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
