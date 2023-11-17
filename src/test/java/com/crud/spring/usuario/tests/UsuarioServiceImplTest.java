package com.crud.spring.usuario.tests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.crud.spring.usuario.entidad.Usuario;
import com.crud.spring.usuario.exception.BadRequestException;
import com.crud.spring.usuario.repository.UsuarioRepository;
import com.crud.spring.usuario.service.UsuarioServiceImpl;

@SpringBootTest
public class UsuarioServiceImplTest {

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UsuarioServiceImpl usuarioService;

	@Test
	public void shouldReturnAllUsers() {
		Mockito.when(usuarioRepository.findAll())
				.thenReturn(Arrays.asList(new Usuario(1L, "John", "Doe", "john.doe@hotmail.com"),
						new Usuario(2L, "Jane", "Doe", "jane.doe@hotmail.com")));

		assertEquals(2, usuarioService.obtenerTodosLosUsuarios().size());
	}

	@Test
	public void shouldReturnUserById() {
		Mockito.when(usuarioRepository.findById(1L))
				.thenReturn(Optional.of(new Usuario(1L, "John", "Doe", "john.doe@hotmail.com")));

		Usuario usuario = usuarioService.obtenerUsuarioPorId(1L);

		assertEquals("John", usuario.getNombre());
	}

	@Test
	public void shouldCreateUser() {
		Usuario usuario = new Usuario();
		usuario.setNombre("John");
		usuario.setApellido("Doe");
		usuario.setCorreo("john.doe@hotmail.com");

		usuarioService.crearUsuario(usuario);

		// Verificar que el método save del repositorio fue llamado con el usuario
		// adecuado
		verify(usuarioRepository, Mockito.times(1)).save(any(Usuario.class));
	}
	
	@Test
    public void shouldThrowExceptionForNullName() {
        Usuario usuario = new Usuario();
        usuario.setApellido("Doe");
        usuario.setCorreo("john.doe@hotmail.com");

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(exception.getMessage()).isEqualTo("Nombre inválido");
    }
	
	@Test
    public void shouldThrowExceptionForEmptyName() {
        Usuario usuario = new Usuario();
        usuario.setNombre("");
        usuario.setApellido("Doe");
        usuario.setCorreo("john.doe@hotmail.com");

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(exception.getMessage()).isEqualTo("Nombre inválido");
    }
	
	@Test
    public void shouldThrowExceptionForNullApellido() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setCorreo("john.doe@hotmail.com");

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(exception.getMessage()).isEqualTo("Apellido inválido");
    }
	
	@Test
    public void shouldThrowExceptionForEmptyApellido() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("");
        usuario.setCorreo("john.doe@hotmail.com");

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(exception.getMessage()).isEqualTo("Apellido inválido");
    }
	
	@Test
    public void shouldThrowExceptionForNullEmail() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(exception.getMessage()).isEqualTo("Correo inválido");
    }
	
	@Test
    public void shouldThrowExceptionForNoHotmailEmail() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setCorreo("john.doe@hotmail");

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(exception.getMessage()).isEqualTo("Correo inválido");
    }
	
	@Test
    public void shouldThrowExceptionForEmptyEmail() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setCorreo("");

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(exception.getMessage()).isEqualTo("Correo inválido");
    }
	
	@Test
	public void shouldUpdateUser() {
		Usuario usuarioExistente = new Usuario();
		usuarioExistente.setId(1L);
		usuarioExistente.setNombre("John");
		usuarioExistente.setApellido("Doe");
		usuarioExistente.setCorreo("john.doe@hotmail.com");

		Usuario usuarioActualizado = new Usuario();
		usuarioActualizado.setId(1L);
		usuarioActualizado.setNombre("John");
		usuarioActualizado.setApellido("Doe");
		usuarioActualizado.setCorreo("john.maximo@hotmail.com");

		Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
		usuarioService.actualizarUsuario(1L, usuarioActualizado);

		// Verificar que el método save del repositorio fue llamado con el usuario
		// actualizado
		verify(usuarioRepository, Mockito.times(1)).save(eq(usuarioActualizado));
	}

	@Test
	public void shouldDeleteUser() {
		usuarioService.eliminarUsuario(1L);

		// Verificar que el método deleteById del repositorio fue llamado con el ID
		// correcto
		verify(usuarioRepository, Mockito.times(1)).deleteById(1L);
	}

}
