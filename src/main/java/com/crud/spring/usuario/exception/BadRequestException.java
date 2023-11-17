package com.crud.spring.usuario.exception;

public class BadRequestException extends RuntimeException{

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
