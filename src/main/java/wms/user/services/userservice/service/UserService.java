package wms.user.services.userservice.service;

import org.springframework.security.core.userdetails.UserDetails;

import wms.user.services.userservice.exceptions.UserNotFoundException;

public interface UserService {

	public UserDetails loadUserByUsername(String username) throws UserNotFoundException;
}
