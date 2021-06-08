package fpt.training.studentManagementRest.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpt.training.studentManagementRest.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	@Query("FROM Subject WHERE isDeleted != 1")
	public List<Subject> findAll();
	
	@Query("FROM Subject WHERE id = :id AND isDeleted != 1")
	public Optional<Subject> findById(@Param("id") long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Subject SET is_deleted = 1 WHERE id = :id", nativeQuery = true)
	public void delete(@Param("id") long id);
}
