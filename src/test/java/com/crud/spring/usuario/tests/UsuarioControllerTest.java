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

import com.crud.spring.usuario.controller.UsuarioController;
import com.crud.spring.usuario.entidad.Usuario;
import com.crud.spring.usuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void retornarUsuariosTest() throws Exception {
        Mockito.when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(Arrays.asList(
                new Usuario(1L, "John", "Doe", "john.doe@hotmail.com"),
                new Usuario(2L, "Jane", "Doe", "jane.doe@hotmail.com")
        ));

        ResultActions resultado = mockMvc.perform(get("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nombre").value("John"))
                .andExpect(jsonPath("$[1].nombre").value("Jane"));
    }
    
    @Test
    public void crearUsuariosTest() throws Exception {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("John");
        nuevoUsuario.setApellido("Doe");
        nuevoUsuario.setCorreo("john.doe@hotmail.com");

        Mockito.when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(nuevoUsuario);

        ResultActions resultado = mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(nuevoUsuario)));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("John"))
                .andExpect(jsonPath("$.apellido").value("Doe"))
                .andExpect(jsonPath("$.correo").value("john.doe@hotmail.com"));
    }
	
    @Test
    public void actualizarUsuarioTest() throws Exception {
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

        Mockito.when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(usuarioExistente);
        Mockito.when(usuarioService.actualizarUsuario(eq(1L), any(Usuario.class))).thenReturn(usuarioActualizado);

        ResultActions resultado = mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(usuarioActualizado)));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("john.maximo@hotmail.com"));
    }
    
    @Test
    public void obtenerUsuarioPorIdTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setCorreo("john.doe@hotmail.com");

        Mockito.when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(usuario);

        ResultActions resultado = mockMvc.perform(get("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON));

        resultado.andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("John"))
                .andExpect(jsonPath("$.apellido").value("Doe"))
                .andExpect(jsonPath("$.correo").value("john.doe@hotmail.com"));
    }
    
    @Test
    public void eliminarUsuarioTest() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(usuarioService, Mockito.times(1)).eliminarUsuario(1L);
    }
    
}
