package fpt.training.studentManagementRest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fpt.training.studentManagementRest.exception.ResourceNotFoundException;
import fpt.training.studentManagementRest.model.ClassEntity;
import fpt.training.studentManagementRest.model.Student;
import fpt.training.studentManagementRest.model.Subject;
import fpt.training.studentManagementRest.model.UserAccount;
import fpt.training.studentManagementRest.model.request.ClassRequest;
import fpt.training.studentManagementRest.repository.ClassRepository;

@Service
public class ClassService {

	@Autowired
	private ClassRepository classRepository;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private UserService userService;

	public List<ClassEntity> getAll() {
		return classRepository.findAll();
	}

	public ClassEntity getById(long id) {
		Optional<ClassEntity> classEntity = classRepository.findById(id);
		if (!classEntity.isPresent()) {
			throw new ResourceNotFoundException("Class " + id + " not found");
		}
		return classEntity.get();
	}

	public ClassEntity createClass(long subjectId, ClassRequest classRequest) {
		Subject subject = subjectService.getById(subjectId);
		ClassEntity classEntity = new ClassEntity();
		BeanUtils.copyProperties(classRequest, classEntity);
		classEntity.setSubject(subject);

		return classRepository.save(classEntity);
	}

	public ClassEntity updateClass(ClassRequest classRequest, long id) {

		Optional<ClassEntity> savedClassEntity = classRepository.findById(id);
		if (!savedClassEntity.isPresent()) {
			throw new ResourceNotFoundException("Class " + id + " not found");
		}

		ClassEntity classEntity = savedClassEntity.get();
		classEntity.setId(id);
		classEntity.setClassTime(classRequest.getClassTime());
		classEntity.setPlace(classRequest.getPlace());
		classEntity.setMaxSlots(classRequest.getMaxSlots());

		return classRepository.save(classEntity);
	}

	public void delete(long id) {
		Optional<ClassEntity> savedClassEntity = classRepository.findById(id);
		if (!savedClassEntity.isPresent()) {
			throw new ResourceNotFoundException("Class " + id + " not found");
		}

		classRepository.delete(id);
	}

	public List<ClassEntity> getEnrollClass() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAccount user = userService.getUser(authentication.getName());
		Student student = user.getStudent();
		if (student == null) {
			throw new ResourceNotFoundException("Student not found");
		}
		boolean check = true;
		List<ClassEntity> classList = new ArrayList<>();
		List<ClassEntity> allClass = classRepository.findAll();
		List<ClassEntity> classOfStudent = classRepository.findByStudentId(student.getId());
		System.out.println(classOfStudent.toString());
		for (ClassEntity c : allClass) {
			System.out.println(c.getStudentList().size());
			long availableSlots = c.getMaxSlots() - c.getStudentList().size(); // fix: ok
			if (availableSlots == 0) {
				check = false;
				break;
			}
			for (ClassEntity cl : classOfStudent) {
				if (c.getId() == cl.getId() || c.getSubject().getId() == cl.getSubject().getId()) {
					check = false;
					break;
				}
			}
			if (check == true) {
				System.out.println(c.toString());
				c.setAvailableSlots(availableSlots);
				classList.add(c);
			}
			check = true;
			if (calculateCreditNumber(student.getId()) >= student.getMaxCourseLoad()) {
				return new ArrayList<ClassEntity>();
			}
		}
		return classList;
	}

	private int calculateCreditNumber(long studentId) {
		int totalCreditNumber = 0;
		for (ClassEntity cl : classRepository.findByStudentId(studentId)) {
			totalCreditNumber += cl.getSubject().getCourseLoad();
		}
		return totalCreditNumber;
	}
}
