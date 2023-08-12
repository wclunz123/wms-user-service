package wms.user.services.userservice.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.entity.Role;
import wms.user.services.userservice.exceptions.EmailAlreadyExistsException;
import wms.user.services.userservice.exceptions.UserNotFoundException;
import wms.user.services.userservice.model.RegisterRequest;
import wms.user.services.userservice.repository.RoleRepository;
import wms.user.services.userservice.repository.UserRepository;
import wms.user.services.userservice.service.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public CustomUser register(final RegisterRequest request) {
		// validation
		if (userRepository.findByEmail(request.getEmail()) != null)
			throw new EmailAlreadyExistsException("User has already registed: " + request.getEmail());

		CustomUser registerUser = new CustomUser(null, request.getUsername(), request.getEmail(),
				passwordEncoder.encode(request.getPassword()), request.getRoles());

		Set<Role> persistedRoles = new HashSet<>();
		for (Role role : request.getRoles()) {
			Role persistedRole = roleRepository.findByRole(role.getRole());
			if (persistedRole == null)
				persistedRole = roleRepository.save(role);
			persistedRoles.add(persistedRole);
		}
		log.info("Attempt to register user email: " + request.getEmail());
		registerUser.setRoles(persistedRoles);
		return userRepository.save(registerUser);
	}

	public CustomUser findById(final Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User ID " + userId + " not found in database."));
	}

	public List<CustomUser> findAll() {
		return userRepository.findAll();
	}

	public CustomUser update(final RegisterRequest registerRequest) {
		log.info("Attempt to update user email: " + registerRequest.getEmail());
		CustomUser user = userRepository.findByEmail(registerRequest.getEmail());
		if (user == null)
			throw new UserNotFoundException("User Email " + registerRequest.getEmail() + " not found in database.");
		
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setUsername(registerRequest.getUsername());
		
		Set<Role> persistedRoles = new HashSet<>();
		for (Role role : registerRequest.getRoles()) {
			Role persistedRole = roleRepository.findByRole(role.getRole());
			if (persistedRole == null)
				persistedRole = roleRepository.save(role);
			persistedRoles.add(persistedRole);
		}
		user.setRoles(persistedRoles);
		return userRepository.save(user);
	}

	public void delete(final Long userId) {
		log.info("Attempt to delete user ID: " + userId);
		CustomUser user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User ID " + userId + " not found in database."));

		userRepository.deleteById(userId);
	}
}
