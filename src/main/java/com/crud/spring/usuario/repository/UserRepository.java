package com.crud.spring.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.spring.usuario.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
