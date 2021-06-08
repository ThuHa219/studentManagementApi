package fpt.training.studentManagementRest.exception.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fpt.training.studentManagementRest.exception.ResourceNotFoundException;
import fpt.training.studentManagementRest.model.ApiException;
import fpt.training.studentManagementRest.utils.ResponseEntityBuilder;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
		List<String> msg = new ArrayList<>();
		msg.add(e.getMessage());
		
		ApiException exception = new ApiException(LocalDateTime.now(),
									HttpStatus.BAD_REQUEST,
									"Resource Not Found",
									msg);
		
		return ResponseEntityBuilder.build(exception);
	}
}
