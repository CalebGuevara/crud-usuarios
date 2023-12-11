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

import com.crud.spring.usuario.entity.User;
import com.crud.spring.usuario.service.UserService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/SEDE2")	// https://localhost:8080/SEDE2/users
public class UserController {

	@Autowired
	private UserService usuarioService;

	@GetMapping("/users")
	public List<User> obtenerTodosLosUsuarios() {
		return usuarioService.getAllUsers();
	}

	@PostMapping("/users")
	public User crearUsuario(@RequestBody User user) {
		return usuarioService.createUser(user);
	}

	@GetMapping("/users/{id}")
	public User obtenerUsuarioPorId(@PathVariable Long id) {
		return usuarioService.getUserById(id);
	}

	@PutMapping("/users/{id}")
	public User actualizarUsuario(@PathVariable Long id, @RequestBody User user) {
		return usuarioService.updateUser(id, user);
	}

	@DeleteMapping("/users/{id}")
	public void eliminarUsuario(@PathVariable Long id) {
		usuarioService.deleteUser(id);
	}

}
