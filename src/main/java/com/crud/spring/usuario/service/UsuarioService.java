package com.crud.spring.usuario.service;

import java.util.List;

import com.crud.spring.usuario.entidad.Usuario;

public interface UsuarioService {
	
	List<Usuario> obtenerTodosLosUsuarios();
	
	Usuario obtenerUsuarioPorId(Long id);
	
	Usuario crearUsuario(Usuario usuario);
	
	Usuario actualizarUsuario(Long id, Usuario usuario);
	
	void eliminarUsuario(Long id);
	
}
