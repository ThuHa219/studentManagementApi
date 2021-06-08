package fpt.training.studentManagementRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.training.studentManagementRest.model.request.ChangePasswordRequest;
import fpt.training.studentManagementRest.model.request.UserRequest;
import fpt.training.studentManagementRest.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest) {
		userService.createUser(userRequest);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
		userService.changePassword(request);
		return ResponseEntity.ok().build();
	}
}
