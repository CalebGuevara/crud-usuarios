package com.crud.spring.usuario.service;

import java.util.List;

import com.crud.spring.usuario.entity.User;

public interface UserService {
	
	//List<Usuario> obtenerTodosLosUsuarios();
	
	List<User> getAllUsers();
	
	//Usuario obtenerUsuarioPorId(Long id);
	
	User getUserById(Long id);
	
	//Usuario crearUsuario(Usuario usuario);
	
	User createUser(User user);
	
	//Usuario actualizarUsuario(Long id, Usuario usuario);
	
	User updateUser(Long id, User user);
	
	//void eliminarUsuario(Long id);
	
	void deleteUser(Long id);
	
}
