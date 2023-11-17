package com.crud.spring.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.spring.usuario.exception.*;
import com.crud.spring.usuario.entidad.Usuario;
import com.crud.spring.usuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public List<Usuario> obtenerTodosLosUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario crearUsuario(Usuario usuario) {
		if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
			throw new BadRequestException("Nombre inválido");
		}
		if (usuario.getApellido() == null || usuario.getApellido().isEmpty()) {
			throw new BadRequestException("Apellido inválido");
		}
		if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty() || !usuario.getCorreo().contains("@hotmail.com")) {
			throw new BadRequestException("Correo inválido");
		}
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario actualizarUsuario(Long id, Usuario usuario) {
		usuario.setId(id);
		return usuarioRepository.save(usuario);
	}

	@Override
	public void eliminarUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}

	@Override
	public Usuario obtenerUsuarioPorId(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
}
