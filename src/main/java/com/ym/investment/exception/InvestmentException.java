package com.ym.investment.exception;

public class InvestmentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvestmentException() {
		super();
	}

	public InvestmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvestmentException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvestmentException(String message) {
		super(message);
	}

	public InvestmentException(Throwable cause) {
		super(cause);
	}

}
