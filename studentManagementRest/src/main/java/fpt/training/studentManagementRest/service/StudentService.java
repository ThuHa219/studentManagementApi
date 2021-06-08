package fpt.training.studentManagementRest.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fpt.training.studentManagementRest.exception.ResourceNotFoundException;
import fpt.training.studentManagementRest.model.Student;
import fpt.training.studentManagementRest.model.UserAccount;
import fpt.training.studentManagementRest.model.request.StudentRequest;
import fpt.training.studentManagementRest.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private UserService userService;

	public List<Student> getAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAccount user = userService.getUser(authentication.getName());
		System.out.println(user.toString());
		if(user.getRole().getRoleName().equalsIgnoreCase("STUDENT")) {
			Student student = user.getStudent();
			if(student == null) {
				throw new ResourceNotFoundException("Student not found");
			}
			return Arrays.asList(student);
		}
		return studentRepository.findAll();
	}

	public Student getById(long id) {
		Optional<Student> student = studentRepository.findById(id);
		if (!student.isPresent()) {
			throw new ResourceNotFoundException("Student " + id + " not found");
		}

		return student.get();
	}

	public Student createStudent(StudentRequest studentRequest) {
		Student student = new Student();
		BeanUtils.copyProperties(studentRequest, student);

		return studentRepository.save(student);
	}

	public Student updateStudent(long id, StudentRequest studentRequest) {
		Optional<Student> savedStudent = studentRepository.findById(id);
		if (!savedStudent.isPresent()) {
			throw new ResourceNotFoundException("Student " + id + " not found");
		}

		Student student = new Student();
		student.setId(id);
		student.setAddress(studentRequest.getAddress());
		student.setDob(studentRequest.getDob());
		student.setFullName(studentRequest.getFullName());
		student.setGender(studentRequest.isGender());
		student.setMaxCourseLoad(studentRequest.getMaxCourseLoad());

		return studentRepository.save(student);

	}

	public void deleteStudent(long id) {
		Optional<Student> student = studentRepository.findById(id);
		if (!student.isPresent()) {
			throw new ResourceNotFoundException("Student " + id + " not found");
		}

		studentRepository.delete(id);
	}

	public void createAccountForStudent(long studentId, String username) {
		Student student = getById(studentId);
		UserAccount user = userService.getUser(username);
		
		user.setStudent(student);
		userService.createAccountForStudent(user);
	}

	public void enroll(long classId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAccount user = userService.getUser(authentication.getName());
		System.out.println(user.toString());
		if(user.getStudent() == null) {
			throw new ResourceNotFoundException("Student not found");
		}
		studentRepository.enroll(user.getStudent().getId(), classId);
	}
	
	public void unenroll(long classId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAccount user = userService.getUser(authentication.getName());
		if(user.getStudent() == null) {
			throw new ResourceNotFoundException("Student not found");
		}
		studentRepository.enroll(user.getStudent().getId(), classId);
	}
}
