package wms.user.services.userservice.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import wms.user.services.userservice.exceptions.UserNotFoundException;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
		if ("randomuser123".equals(username)) {
			return new User("randomuser123", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UserNotFoundException("Username " + username + " not found. Please enter correct credentials.");
		}
	}
}
