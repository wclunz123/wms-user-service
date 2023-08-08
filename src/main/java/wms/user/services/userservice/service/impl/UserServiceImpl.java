package wms.user.services.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.model.RegisterRequest;
import wms.user.services.userservice.repository.UserRepository;
import wms.user.services.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public CustomUser register(RegisterRequest request) {
		

		CustomUser registerUser = new CustomUser(null, request.getUsername(), request.getEmail(),
				passwordEncoder.encode(request.getPassword()), request.getRole());
		System.out.println("Check User: " + registerUser.toString());

		return userRepository.save(registerUser);
	}
}
