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

import fpt.training.studentManagementRest.model.ClassEntity;
import fpt.training.studentManagementRest.model.request.ClassRequest;
import fpt.training.studentManagementRest.service.ClassService;

@RestController
@RequestMapping(value = "/api")
public class ClassController {

	@Autowired
	private ClassService classService;;

	@GetMapping("/classes")
	public ResponseEntity<CollectionModel<EntityModel<ClassEntity>>> getAll() {
		List<ClassEntity> classes = classService.getAll();

		List<EntityModel<ClassEntity>> classList = classes.stream()
				.map(classEntity -> EntityModel.of(classEntity,
						linkTo(methodOn(ClassController.class).getById(classEntity.getId())).withSelfRel(),
						linkTo(methodOn(ClassController.class).getAll()).withRel("classes")))
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(CollectionModel.of(classList, linkTo(methodOn(ClassController.class).getAll()).withSelfRel()));
	}
	
	@GetMapping("/classes/enroll")
	public ResponseEntity<CollectionModel<EntityModel<ClassEntity>>> getEnrollClass() {
		List<ClassEntity> classes = classService.getEnrollClass();

		List<EntityModel<ClassEntity>> classList = classes.stream()
				.map(classEntity -> EntityModel.of(classEntity,
						linkTo(methodOn(ClassController.class).getById(classEntity.getId())).withSelfRel(),
						linkTo(methodOn(ClassController.class).getAll()).withRel("classes")))
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(CollectionModel.of(classList, linkTo(methodOn(ClassController.class).getAll()).withSelfRel()));
	}

	@GetMapping("/classes/{id}")
	public ResponseEntity<EntityModel<ClassEntity>> getById(@PathVariable("id") long id) {
		ClassEntity classEntity = classService.getById(id);

		return ResponseEntity
				.ok(EntityModel.of(classEntity, linkTo(methodOn(ClassController.class).getById(id)).withSelfRel(),
						linkTo(methodOn(ClassController.class).getAll()).withRel("classes")));
	}

	@PostMapping("/subjects/{subjectId}/classes")
	public ResponseEntity<EntityModel<ClassEntity>> createClass(@PathVariable("subjectId") long subjectId,
			@RequestBody ClassRequest classRequest) {

		ClassEntity classEntity = classService.createClass(subjectId, classRequest);

		return ResponseEntity.ok(EntityModel.of(classEntity,
				linkTo(methodOn(ClassController.class).getById(classEntity.getId())).withSelfRel(),
				linkTo(methodOn(ClassController.class).getAll()).withRel("classes"),
				linkTo(methodOn(SubjectController.class).getAll()).withRel("subjects"),
				linkTo(methodOn(SubjectController.class).getById(subjectId)).withRel(subjectId + "/subject")));
	}

	@PutMapping("/classes/{id}")
	public ResponseEntity<EntityModel<ClassEntity>> updateController(@PathVariable("id") long id,
			@RequestBody ClassRequest classRequest) {
		ClassEntity classEntity = classService.updateClass(classRequest, id);
		
		return ResponseEntity.ok(EntityModel.of(classEntity,
				linkTo(methodOn(ClassController.class).getById(classEntity.getId())).withSelfRel(),
				linkTo(methodOn(ClassController.class).getAll()).withRel("classes")));
	}
	
	@DeleteMapping("/classes/{id}")
	public ResponseEntity<?> deleteClass(@PathVariable("id") long id) {
		classService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
