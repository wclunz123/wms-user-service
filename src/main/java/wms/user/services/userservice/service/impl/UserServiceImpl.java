package wms.user.services.userservice.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.entity.Role;
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
		CustomUser registerUser = new CustomUser(null, request.getUsername(), request.getEmail(),
				passwordEncoder.encode(request.getPassword()), request.getRoles());
		System.out.println("Check user: " + registerUser.toString());

//		Set<Role> persistedRoles = new HashSet<>();
//		for (Role role : request.getRoles()) {
//			Role persistedRole = roleRepository.findByRole(role.getRole());
//			if (persistedRole == null) {
//				persistedRole = roleRepository.save(role);
//			}
//			persistedRoles.add(persistedRole);
//		}

		return userRepository.save(registerUser);
	}
}
