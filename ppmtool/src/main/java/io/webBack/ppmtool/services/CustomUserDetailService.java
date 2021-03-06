package io.webBack.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.webBack.ppmtool.domain.User;
import io.webBack.ppmtool.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user == null) {
			new UsernameNotFoundException("User Not Found");
		}
		return user;
		
	}
	
	@Transactional()
	public User loadUserById(Long id) {
		User user= userRepo.getById(id);
		if(user == null) {
			new UsernameNotFoundException("User Not Found");
		}
		return user;
		
	}

	
}
