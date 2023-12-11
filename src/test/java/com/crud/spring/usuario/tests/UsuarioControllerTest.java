package com.crud.spring.usuario.tests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.crud.spring.usuario.controller.UserController;
import com.crud.spring.usuario.entity.User;
import com.crud.spring.usuario.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(UserController.class)
public class UsuarioControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void returnUsersTest() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(
                new User(1L, "John", "Doe", "john.doe@hotmail.com"),
                new User(2L, "Jane", "Doe", "jane.doe@hotmail.com")
        ));

        ResultActions resultado = mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));
    }
    
    @Test
    public void createUsersTest() throws Exception {
        User nUser = new User();
        nUser.setName("John");
        nUser.setLastName("Doe");
        nUser.setEmail("john.doe@hotmail.com");

        Mockito.when(userService.createUser(any(User.class))).thenReturn(nUser);

        ResultActions resultado = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(nUser)));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@hotmail.com"));
    }
	
    @Test
    public void updateUserTest() throws Exception {
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

        Mockito.when(userService.getUserById(1L)).thenReturn(userExistent);
        Mockito.when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        ResultActions resultado = mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedUser)));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.maximo@hotmail.com"));
    }
    
    @Test
    public void getUserByIdTest() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@hotmail.com");

        Mockito.when(userService.getUserById(1L)).thenReturn(user);

        ResultActions resultado = mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@hotmail.com"));
    }
    
    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(userService, Mockito.times(1)).deleteUser(1L);
    }
    
}
