package fpt.training.studentManagementRest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fpt.training.studentManagementRest.exception.ResourceNotFoundException;
import fpt.training.studentManagementRest.model.Role;
import fpt.training.studentManagementRest.model.UserAccount;
import fpt.training.studentManagementRest.model.request.ChangePasswordRequest;
import fpt.training.studentManagementRest.model.request.UserRequest;
import fpt.training.studentManagementRest.repository.UserRepository;
import fpt.training.studentManagementRest.utils.Validator;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserAccount getUser(String username) {
		Optional<UserAccount> user = userRepository.findByUsername(username);
		if (!user.isPresent()) {
			throw new ResourceNotFoundException("User " + username + " Not Found ");
		}
		return user.get();
	}

	public void createUser(UserRequest userRequest) {
		UserAccount user = new UserAccount();
		Optional<UserAccount> userByName = userRepository.findByUsername(userRequest.getUsername());
		if (userByName.isPresent()) {
			throw new IllegalArgumentException("Already have account");
		}
		if(Validator.validatePassword(userRequest.getPassword())) {
			throw new IllegalArgumentException("Password invalid");
		}
		user.setUsername(userRequest.getUsername());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setRole(new Role(userRequest.getRole()));

		userRepository.save(user);
	}
	
	public void createAccountForStudent(UserAccount userAccount) {
		userRepository.saveAndFlush(userAccount);
	}
	
	public void changePassword(ChangePasswordRequest request) {
		if(!request.getPassword().equals(request.getConfirmPassword()) || !Validator.validatePassword(request.getPassword())) {
			throw new IllegalArgumentException("Password invalid");
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		userRepository.changePassword(passwordEncoder.encode(request.getPassword()), username);
	}
}
