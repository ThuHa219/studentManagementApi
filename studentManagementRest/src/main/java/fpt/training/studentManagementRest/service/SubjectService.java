package fpt.training.studentManagementRest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.training.studentManagementRest.exception.ResourceNotFoundException;
import fpt.training.studentManagementRest.model.Subject;
import fpt.training.studentManagementRest.model.request.SubjectRequest;
import fpt.training.studentManagementRest.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	public List<Subject> getAll() {
		return subjectRepository.findAll();
	}

	public Subject getById(long id) {
		Optional<Subject> subject = subjectRepository.findById(id);
		if (!subject.isPresent()) {
			throw new ResourceNotFoundException("Subject " + id + " Not Found ");
		}
		return subject.get();
	}

	public Subject createSubject(SubjectRequest subjectRequest) {
		Subject subject = new Subject();
		BeanUtils.copyProperties(subjectRequest, subject);
		return subjectRepository.save(subject);
	}

	public Subject updateSubject(long id, SubjectRequest subjectRequest) {
		Optional<Subject> subject = subjectRepository.findById(id);
		if (!subject.isPresent()) {
			throw new ResourceNotFoundException("Subject " + id + " Not Found ");
		}
		Subject updatedSubject = subject.get();
		updatedSubject.setId(id);
		updatedSubject.setSubjectName(subjectRequest.getSubjectName());
		updatedSubject.setCourseLoad(subjectRequest.getCourseLoad());

		return subjectRepository.save(updatedSubject);
	}
	
	public void delete(long id) {
		Optional<Subject> subject = subjectRepository.findById(id);
		if (!subject.isPresent()) {
			throw new ResourceNotFoundException("Subject " + id + " Not Found ");
		}
		
		subjectRepository.delete(id);
	}

}
