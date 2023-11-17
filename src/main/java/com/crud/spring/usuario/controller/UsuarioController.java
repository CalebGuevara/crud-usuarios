package com.crud.spring.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.spring.usuario.entidad.Usuario;
import com.crud.spring.usuario.service.UsuarioService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api")	// https://localhost:8080/api/usuarios
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/usuarios")
	public List<Usuario> obtenerTodosLosUsuarios() {
		return usuarioService.obtenerTodosLosUsuarios();
	}

	@PostMapping("/usuarios")
	public Usuario crearUsuario(@RequestBody Usuario usuario) {
		return usuarioService.crearUsuario(usuario);
	}

	@GetMapping("/usuarios/{id}")
	public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
		return usuarioService.obtenerUsuarioPorId(id);
	}

	@PutMapping("/usuarios/{id}")
	public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
		return usuarioService.actualizarUsuario(id, usuario);
	}

	@DeleteMapping("/usuarios/{id}")
	public void eliminarUsuario(@PathVariable Long id) {
		usuarioService.eliminarUsuario(id);
	}

}
