package com.crud.spring.usuario.tests;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.crud.spring.usuario.exception.BadRequestException;

class BadRequestExceptionTest {
	
    @Test
    public void testConstructor() {
        BadRequestException excepcion = new BadRequestException();

        assertThat(excepcion).isInstanceOf(BadRequestException.class);
        assertThat(excepcion.getMessage()).isNull();
    }

    @Test
    public void testConstructorConMensaje() {
        String error = "Solicitud incorrecta";
        BadRequestException excepcion = new BadRequestException(error);

        assertThat(excepcion).isInstanceOf(BadRequestException.class);
        assertThat(excepcion.getMessage()).isEqualTo(error);
    }

}
