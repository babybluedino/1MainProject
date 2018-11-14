package com.verizon.tsp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.verizon.tsp.models.Admin;


/**
 * This Service class for providing the user credentials from the database.
 * 
 * @author kamal berriga
 *
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	AdminService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin user = userService.find(username);
		return  user;
	}

}
