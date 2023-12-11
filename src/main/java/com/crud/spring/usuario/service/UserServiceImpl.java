package com.crud.spring.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.spring.usuario.entity.User;
import com.crud.spring.usuario.exception.*;
import com.crud.spring.usuario.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(User user) {
		if (user.getName() == null || user.getName().isEmpty()) {
			throw new BadRequestException("Invalid name");
		}
		if (user.getLastName() == null || user.getLastName().isEmpty()) {
			throw new BadRequestException("Invalid last name");
		}
		if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@hotmail.com")) {
			throw new BadRequestException("Invalid email");
		}
		
		return userRepository.save(user);
	}

	@Override
	public User updateUser(Long id, User user) {
		user.setId(id);
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
}
