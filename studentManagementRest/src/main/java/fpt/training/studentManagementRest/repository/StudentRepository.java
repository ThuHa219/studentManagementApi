package fpt.training.studentManagementRest.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpt.training.studentManagementRest.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Query("FROM Student s WHERE s.isDeleted != 1")
	public List<Student> findAll();
	
	@Query("FROM Student s Where id = :id AND s.isDeleted != 1")
	public Optional<Student> findById(@Param("id") long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Student SET is_deleted = 1 WHERE id = :id", nativeQuery = true)
	public void delete(@Param("id") long id);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO assignment(student_id, class_id) VALUES(:studentId, :classId);", nativeQuery = true)
	public void enroll(@Param("studentId") long studentId, @Param("classId") long classId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM assignment WHERE student_id = :studentId AND class_id = :classId", nativeQuery = true)
	public void unenroll(@Param("studentId") long studentId, @Param("classId") long classId);
}
