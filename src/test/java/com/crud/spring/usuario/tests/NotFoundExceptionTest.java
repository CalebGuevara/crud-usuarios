package com.crud.spring.usuario.tests;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.crud.spring.usuario.exception.NotFoundException;

class NotFoundExceptionTest {

	@Test
    public void testConstructor() {
        NotFoundException excepcion = new NotFoundException();

        assertThat(excepcion).isInstanceOf(NotFoundException.class);
        assertThat(excepcion.getMessage()).isNull();
    }

	@Test
    public void testConstructorConMensaje() {
        String error = "Recurso no encontrado";
        NotFoundException excepcion = new NotFoundException(error);

        assertThat(excepcion).isInstanceOf(NotFoundException.class);
        assertThat(excepcion.getMessage()).isEqualTo(error);
    }
    
}
