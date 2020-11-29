package com.simpsonn.mancala.controller;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simpsonn.mancala.model.response.ErrorResponse;

/**
 * gracefully handles errors which occur due to incorrect input by the consumer
 * of the service and returns them back as bad requests with a meaningful message
 */
@RestControllerAdvice
public class ErrorController {

	private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);
	
	/*
	 * send validation errors on path variables back as a bad request instead of an
	 * internal server error and tell the user what was wrong, also deals with
	 * illegal argument exceptions when players try to input a bad parameter or do
	 * something which breaks the rules of the game as those situations are thrown as
	 * exceptions
	 */
	@ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class})
	private ResponseEntity<ErrorResponse> handleValidationException(Exception e) {

		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		LOG.info("handled bad request error:" + e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}	
	
}
