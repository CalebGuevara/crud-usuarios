package com.crud.spring.usuario.tests;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.crud.spring.usuario.exception.BadRequestException;

class BadRequestExceptionTest {
	
    @Test
    public void testDefaultConstructor() {
        BadRequestException exception = new BadRequestException();

        assertThat(exception).isInstanceOf(BadRequestException.class);
        assertThat(exception.getMessage()).isNull();
    }

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Solicitud incorrecta";
        BadRequestException exception = new BadRequestException(errorMessage);

        assertThat(exception).isInstanceOf(BadRequestException.class);
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

}
