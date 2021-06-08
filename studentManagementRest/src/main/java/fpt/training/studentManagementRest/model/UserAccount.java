package fpt.training.studentManagementRest.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class UserAccount {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private  long id;
	@Column(name = "username")
	private String username;
	private String password;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id", referencedColumnName = "id")
	private Student student;
	@Column(name = "is_deleted", columnDefinition = "bit default 0")
	private boolean isDeleted;
	@Column(name = "is_enabled", columnDefinition = "bit default 0")
	private boolean enabled;
	
	public UserAccount(long id, String username, String password, Role role, Student student, boolean isDeleted) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.student = student;
		this.isDeleted = isDeleted;
	}

	public UserAccount() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@JsonBackReference
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", student=" + student + ", isDeleted=" + isDeleted + ", enabled=" + enabled + "]";
	}
}
