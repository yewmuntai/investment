package com.ym.investment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ym.investment.exception.InvestmentException;
import com.ym.investment.exception.NotFoundException;
import com.ym.investment.dto.ErrorDTO;

@ControllerAdvice
public class ErrorHandler {
	@ExceptionHandler(InvestmentException.class)
	public ResponseEntity<ErrorDTO> schException(Exception e) {
		return new ResponseEntity<>(makeDTO(e), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDTO> nfException(Exception e) {
		return new ResponseEntity<>(makeDTO(e), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> exception(Exception e) {
		return new ResponseEntity<>(makeDTO(e), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ErrorDTO makeDTO(Exception e) {
		return new ErrorDTO(e.getMessage());
	}
}
