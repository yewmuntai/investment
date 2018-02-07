package com.ym.investment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ym.investment.exception.InvestmentException;
import com.ym.investment.exception.NotFoundException;
import com.ym.investment.dto.ErrorDTO;

/**
 * To handle error situations from CRUD service. When the CRUD Service is unable to perform it's action correctly, it will throw an exception, which will be handled here.
 *
 */
@ControllerAdvice
public class ErrorHandler {
	/**
	 * When there's a problem with a request, the InvestmentException may be thrown. This will return the BAD_REQUEST status
	 * @param e The Exception thrown
	 * @return ResponseEntity that contains the ErrorDTO
	 */
	@ExceptionHandler(InvestmentException.class)
	public ResponseEntity<ErrorDTO> schException(Exception e) {
		return new ResponseEntity<>(makeDTO(e), HttpStatus.BAD_REQUEST);
	}

	/**
	 * When the request is for a particular instance of a domain, and the instance cannot be found, the NotFoundException may be thrown. This will return the NOT_FOUND status
	 * @param e The Exception thrown
	 * @return ResponseEntity that contains the ErrorDTO
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDTO> nfException(Exception e) {
		return new ResponseEntity<>(makeDTO(e), HttpStatus.NOT_FOUND);
	}

	/**
	 * Any other exception that occurs, probably unexpected system error. This will return the INTERNAL_SERVER_ERROR status
	 * @param e The Exception thrown
	 * @return ResponseEntity that contains the ErrorDTO
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> exception(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>(makeDTO(e), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ErrorDTO makeDTO(Exception e) {
		return new ErrorDTO(e.getMessage());
	}
}
