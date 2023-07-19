package wms.user.services.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wms.user.services.userservice.exceptions.InvalidCredentialsException;
import wms.user.services.userservice.model.LoginRequest;
import wms.user.services.userservice.model.LoginResponse;
import wms.user.services.userservice.service.UserService;
import wms.user.services.userservice.utils.jwt.JwtTokenManager;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenManager jwtTokenManager;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new InvalidCredentialsException("Invalid credentials provided");
		}

		final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
		final String token = jwtTokenManager.generateJwtToken(userDetails);

		return ResponseEntity.ok(new LoginResponse(token));
	}
}
