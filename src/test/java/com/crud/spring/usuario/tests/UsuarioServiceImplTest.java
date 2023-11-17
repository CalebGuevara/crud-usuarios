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
	public void retornarUsuariosTest() {
		Mockito.when(usuarioRepository.findAll())
				.thenReturn(Arrays.asList(new Usuario(1L, "John", "Doe", "john.doe@hotmail.com"),
						new Usuario(2L, "Jane", "Doe", "jane.doe@hotmail.com")));

		assertEquals(2, usuarioService.obtenerTodosLosUsuarios().size());
	}

	@Test
	public void retornarUsuarioPorIdTest() {
		Mockito.when(usuarioRepository.findById(1L))
				.thenReturn(Optional.of(new Usuario(1L, "John", "Doe", "john.doe@hotmail.com")));

		Usuario usuario = usuarioService.obtenerUsuarioPorId(1L);

		assertEquals("John", usuario.getNombre());
	}

	@Test
	public void crearUsuarioTest() {
		Usuario usuario = new Usuario();
		usuario.setNombre("John");
		usuario.setApellido("Doe");
		usuario.setCorreo("john.doe@hotmail.com");

		usuarioService.crearUsuario(usuario);

		verify(usuarioRepository, Mockito.times(1)).save(any(Usuario.class));
	}
	
	@Test
    public void retornarExcepcionPorNombreNullTest() {
        Usuario usuario = new Usuario();
        usuario.setApellido("Doe");
        usuario.setCorreo("john.doe@hotmail.com");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Nombre inválido");
    }
	
	@Test
    public void retornarExcepcionPorNombreVacioTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("");
        usuario.setApellido("Doe");
        usuario.setCorreo("john.doe@hotmail.com");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Nombre inválido");
    }
	
	@Test
    public void retornarExcepcionPorApellidoNullTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setCorreo("john.doe@hotmail.com");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Apellido inválido");
    }
	
	@Test
    public void retornarExcepcionPorApellidoVacioTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("");
        usuario.setCorreo("john.doe@hotmail.com");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Apellido inválido");
    }
	
	@Test
    public void retornarExcepcionPorCorreoNullTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Correo inválido");
    }
	
	@Test
    public void retornarExcepcionPorCorreoNoHotmailTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setCorreo("john.doe@hotmail");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Correo inválido");
    }
	
	@Test
    public void retornarExcepcionPorCorreoVacioTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setCorreo("");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> usuarioService.crearUsuario(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Correo inválido");
    }
	
	@Test
	public void actualizarUsuarioTest() {
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

		verify(usuarioRepository, Mockito.times(1)).save(eq(usuarioActualizado));
	}

	@Test
	public void eliminarUsuarioTest() {
		usuarioService.eliminarUsuario(1L);

		verify(usuarioRepository, Mockito.times(1)).deleteById(1L);
	}

}
