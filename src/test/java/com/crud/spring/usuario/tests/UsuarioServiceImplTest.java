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

import com.crud.spring.usuario.entity.User;
import com.crud.spring.usuario.exception.BadRequestException;
import com.crud.spring.usuario.repository.UserRepository;
import com.crud.spring.usuario.service.UserServiceImpl;

@SpringBootTest
public class UsuarioServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void returnUsersTest() {
		Mockito.when(userRepository.findAll())
				.thenReturn(Arrays.asList(new User(1L, "John", "Doe", "john.doe@hotmail.com"),
						new User(2L, "Jane", "Doe", "jane.doe@hotmail.com")));

		assertEquals(2, userService.getAllUsers().size());
	}

	@Test
	public void returnUserByIdTest() {
		Mockito.when(userRepository.findById(1L))
				.thenReturn(Optional.of(new User(1L, "John", "Doe", "john.doe@hotmail.com")));

		User user = userService.getUserById(1L);

		assertEquals("John", user.getName());
	}

	@Test
	public void createUserTest() {
		User user = new User();
		user.setName("John");
		user.setLastName("Doe");
		user.setEmail("john.doe@hotmail.com");

		userService.createUser(user);

		verify(userRepository, Mockito.times(1)).save(any(User.class));
	}
	
	@Test
    public void returnExceptionByNameNullTest() {
        User user = new User();
        user.setLastName("Doe");
        user.setEmail("john.doe@hotmail.com");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> userService.createUser(user));

        assertThat(excepcion.getMessage()).isEqualTo("Invalid Name");
    }
	
	@Test
    public void returnExceptionByNameEmptyTest() {
        User usuario = new User();
        usuario.setName("");
        usuario.setLastName("Doe");
        usuario.setEmail("john.doe@hotmail.com");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> userService.createUser(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Invalid Name");
    }
	
	@Test
    public void returnExceptionByLastNameNullTest() {
        User user = new User();
        user.setName("John");
        user.setEmail("john.doe@hotmail.com");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> userService.createUser(user));

        assertThat(excepcion.getMessage()).isEqualTo("Invalid Last Name");
    }
	
	@Test
    public void returnExceptionByLastNameEmptyTest() {
        User user = new User();
        user.setName("John");
        user.setLastName("");
        user.setEmail("john.doe@hotmail.com");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> userService.createUser(user));

        assertThat(excepcion.getMessage()).isEqualTo("Invalid Last Name");
    }
	
	@Test
    public void returnExceptionByEmailNullTest() {
        User usuario = new User();
        usuario.setName("John");
        usuario.setLastName("Doe");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> userService.createUser(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Invalid Email");
    }
	
	@Test
    public void returnExceptionByEmailNoHotmailTest() {
		User usuario = new User();
        usuario.setName("John");
        usuario.setLastName("Doe");
        usuario.setEmail("john.doe@hotmail");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> userService.createUser(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Invalid Email");
    }
	
	@Test
    public void returnExceptionByEmailEmptyTest() {
        User usuario = new User();
        usuario.setName("John");
        usuario.setLastName("Doe");
        usuario.setEmail("");

        BadRequestException excepcion = assertThrows(BadRequestException.class,
                () -> userService.createUser(usuario));

        assertThat(excepcion.getMessage()).isEqualTo("Invalid Email");
    }
	
	@Test
	public void updateUserTest() {
		User userExistent = new User();
		userExistent.setId(1L);
		userExistent.setName("John");
		userExistent.setLastName("Doe");
		userExistent.setEmail("john.doe@hotmail.com");

		User updatedUser = new User();
		updatedUser.setId(1L);
		updatedUser.setName("John");
		updatedUser.setLastName("Doe");
		updatedUser.setEmail("john.maximo@hotmail.com");

		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userExistent));
		userService.updateUser(1L, updatedUser);

		verify(userRepository, Mockito.times(1)).save(eq(updatedUser));
	}

	@Test
	public void deleteUserTest() {
		userService.deleteUser(1L);

		verify(userRepository, Mockito.times(1)).deleteById(1L);
	}

}
