package wms.user.services.userservice.service.impl;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import wms.user.services.userservice.exceptions.UserNotFoundException;
import wms.user.services.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

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
