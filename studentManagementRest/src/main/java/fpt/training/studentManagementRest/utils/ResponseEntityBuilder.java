package fpt.training.studentManagementRest.utils;

import org.springframework.http.ResponseEntity;

import fpt.training.studentManagementRest.model.ApiException;

public class ResponseEntityBuilder {
	
	public static ResponseEntity<Object> build(ApiException apiException) {
		return new ResponseEntity<Object>(apiException, apiException.getStatus());
	}
}
