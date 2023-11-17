package com.crud.spring.usuario.tests;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.crud.spring.usuario.exception.NotFoundException;

class NotFoundExceptionTest {

	@Test
    public void testDefaultConstructor() {
        NotFoundException exception = new NotFoundException();

        assertThat(exception).isInstanceOf(NotFoundException.class);
        assertThat(exception.getMessage()).isNull();
    }

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Recurso no encontrado";
        NotFoundException exception = new NotFoundException(errorMessage);

        assertThat(exception).isInstanceOf(NotFoundException.class);
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }
    
}
