package wms.user.services.userservice.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.exceptions.UserNotFoundException;
import wms.user.services.userservice.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
		CustomUser retrievedUser = userRepository.findByEmail(username);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + retrievedUser.getRoles()));
		return new User(retrievedUser.getEmail(), retrievedUser.getPassword(), grantedAuthorities);
	}
}
