package wms.user.services.userservice.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.entity.Role;
import wms.user.services.userservice.exceptions.EmailAlreadyExistsException;
import wms.user.services.userservice.model.RegisterRequest;
import wms.user.services.userservice.repository.RoleRepository;
import wms.user.services.userservice.repository.UserRepository;
import wms.user.services.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public CustomUser register(RegisterRequest request) {
		// validation
		if (userRepository.findByEmail(request.getEmail()) != null)
			throw new EmailAlreadyExistsException("User has already registed: " + request.getEmail());

		CustomUser registerUser = new CustomUser(null, request.getUsername(), request.getEmail(),
				passwordEncoder.encode(request.getPassword()), request.getRoles());

		Set<Role> persistedRoles = new HashSet<>();
		for (Role role : request.getRoles()) {
			Role persistedRole = roleRepository.findByRole(role.getRole());
			if (persistedRole == null) {
				persistedRole = roleRepository.save(role);
			}
			persistedRoles.add(persistedRole);
		}
		registerUser.setRoles(persistedRoles);
		return userRepository.save(registerUser);
	}

	public Optional<CustomUser> findById(final Long userId) {
		return userRepository.findById(userId);
	}

	public List<CustomUser> findAll() {
		return userRepository.findAll();
	}
}
