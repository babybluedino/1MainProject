package com.verizon.tsp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verizon.tsp.dao.UserRepository;
import com.verizon.tsp.models.Admin;

@Service
public class AdminService {

	@Autowired
	UserRepository userRepository;

	public Admin save(Admin user) {
		return userRepository.saveAndFlush(user);
	}

	public Admin update(Admin user) {
		return userRepository.save(user);
	}

	public Admin find(String userName) {
		return userRepository.findOneByUsername(userName);
	}

	public Admin find(Long id) {
		return userRepository.getOne(id);
	}
}
