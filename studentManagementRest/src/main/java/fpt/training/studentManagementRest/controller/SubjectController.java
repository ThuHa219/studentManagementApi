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
import org.springframework.web.bind.annotation.RestController;

import fpt.training.studentManagementRest.model.Subject;
import fpt.training.studentManagementRest.model.request.SubjectRequest;
import fpt.training.studentManagementRest.service.SubjectService;

@RestController
@RequestMapping(value = "/api/subjects")
public class SubjectController {
	@Autowired
	private SubjectService subjectService;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Subject>>> getAll() {
		List<EntityModel<Subject>> subjects = subjectService.getAll().stream()
				.map(subject -> EntityModel.of(subject,
						linkTo(methodOn(SubjectController.class).getById(subject.getId())).withSelfRel(),
						linkTo(methodOn(SubjectController.class).getAll()).withRel("subjects")))
				.collect(Collectors.toList());
		return ResponseEntity
				.ok(CollectionModel.of(subjects, linkTo(methodOn(SubjectController.class).getAll()).withSelfRel()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Subject>> getById(@PathVariable long id) {
		Subject subject = subjectService.getById(id);
		return ResponseEntity
				.ok(EntityModel.of(subject, linkTo(methodOn(SubjectController.class).getById(id)).withSelfRel(),
						linkTo(methodOn(SubjectController.class).getAll()).withRel("subjects")));
	}

	@PostMapping
	public ResponseEntity<EntityModel<Subject>> createSubject(@RequestBody SubjectRequest subjectRequest) {
		Subject savedSubject = subjectService.createSubject(subjectRequest);
		EntityModel<Subject> subject = EntityModel.of(savedSubject,
				linkTo(methodOn(SubjectController.class).getById(savedSubject.getId())).withSelfRel(),
				linkTo(methodOn(SubjectController.class).getAll()).withRel("subjects"));
		return ResponseEntity.ok(subject);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Subject>> updateSubject(@PathVariable long id,
			@RequestBody SubjectRequest subjectRequest) {
		Subject updatedSubject = subjectService.updateSubject(id, subjectRequest);
		return ResponseEntity.ok(
				EntityModel.of(updatedSubject, linkTo(methodOn(SubjectController.class).getById(id)).withSelfRel(),
								linkTo(methodOn(SubjectController.class).getAll()).withRel("subjects")));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable long id) {
		subjectService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
