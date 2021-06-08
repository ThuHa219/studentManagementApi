package fpt.training.studentManagementRest.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpt.training.studentManagementRest.model.ClassEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

	@Query("FROM ClassEntity c WHERE c.subject.isDeleted != 1 AND isDeleted != 1")
	public List<ClassEntity> findAll();

	@Query("FROM ClassEntity c WHERE id = :id AND isDeleted != 1 AND c.subject.isDeleted != 1")
	public Optional<ClassEntity> findById(@Param("id") long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE class SET is_deleted = 1 WHERE id = :id", nativeQuery = true)
	public void delete(@Param("id") long id);
	
	@Query(value = "SELECT DISTINCT * FROM class c JOIN assignment a ON c.id = a.class_id WHERE student_id = :studentId AND is_deleted != 1;"
			, nativeQuery = true)
	List<ClassEntity> findByStudentId(long studentId);

}
