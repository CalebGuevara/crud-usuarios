package com.crud.spring.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.spring.usuario.entidad.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
