package fpt.training.studentManagementRest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.training.studentManagementRest.model.Student;
import fpt.training.studentManagementRest.model.request.StudentRequest;
import fpt.training.studentManagementRest.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/students")
	public ResponseEntity<CollectionModel<EntityModel<Student>>> getAll() {

		List<Student> savedStudent = studentService.getAll();
		System.out.println(savedStudent.toString());
		List<EntityModel<Student>> students = savedStudent.stream()
				.map(student -> EntityModel.of(student,
						linkTo(methodOn(StudentController.class).getAll()).withRel("students"),
						linkTo(methodOn(StudentController.class).getById(student.getId())).withSelfRel()))
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(CollectionModel.of(students, linkTo(methodOn(StudentController.class).getAll()).withSelfRel()));
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<EntityModel<Student>> getById(@PathVariable long id) {
		Student student = studentService.getById(id);

		return ResponseEntity
				.ok(EntityModel.of(student, linkTo(methodOn(StudentController.class).getAll()).withRel("students"),
						linkTo(methodOn(StudentController.class).getById(student.getId())).withSelfRel()));
	}

	@PostMapping("/students")
	public ResponseEntity<EntityModel<Student>> createStudent(@RequestBody StudentRequest studentRequest) {
		Student student = studentService.createStudent(studentRequest);

		return ResponseEntity
				.ok(EntityModel.of(student, linkTo(methodOn(StudentController.class).getAll()).withRel("students"),
						linkTo(methodOn(StudentController.class).getById(student.getId())).withSelfRel()));
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<EntityModel<Student>> updateStudent(@PathVariable long id,
			@RequestBody StudentRequest studentRequest) {
		Student student = studentService.updateStudent(id, studentRequest);

		return ResponseEntity
				.ok(EntityModel.of(student, linkTo(methodOn(StudentController.class).getAll()).withRel("students"),
						linkTo(methodOn(StudentController.class).getById(student.getId())).withSelfRel()));
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable long id) {
		studentService.deleteStudent(id);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/students/{studentId}/user/{username}")
	public ResponseEntity<?> createAccountForStudent(@PathVariable("studentId") long studentId,
			@PathVariable("username") String username) {
		studentService.createAccountForStudent(studentId, username);

		return ResponseEntity
				.ok().build();
	}

	@GetMapping("/students/enroll")
	public ResponseEntity<?> enroll(@RequestParam long classId) {
		studentService.enroll(classId);

		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/students/unenroll")
	public ResponseEntity<?> unenroll(@RequestParam long classId) {
		studentService.unenroll(classId);
		
		return ResponseEntity.ok().build();
	}
}
