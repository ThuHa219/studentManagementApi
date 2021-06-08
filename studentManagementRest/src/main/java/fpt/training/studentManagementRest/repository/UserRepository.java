package fpt.training.studentManagementRest.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fpt.training.studentManagementRest.model.UserAccount;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long>{
	Optional<UserAccount> findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE userAccount SET password = :password WHERE username = :username", nativeQuery = true)
	public void changePassword(String password, String username);
}
