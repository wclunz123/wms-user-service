package wms.user.services.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wms.user.services.userservice.config.JwtTokenManager;
import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.exceptions.InvalidCredentialsException;
import wms.user.services.userservice.model.LoginRequest;
import wms.user.services.userservice.model.LoginResponse;
import wms.user.services.userservice.model.RegisterRequest;
import wms.user.services.userservice.service.JwtUserDetailsService;
import wms.user.services.userservice.service.UserService;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenManager jwtTokenManager;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
		final String token = jwtTokenManager.generateJwtToken(userDetails);
		return ResponseEntity.ok(new LoginResponse(token));
	}

	@PostMapping("/register")
	public ResponseEntity<CustomUser> register(@RequestBody RegisterRequest registerRequest) throws Exception {
		final CustomUser registeredUser = userService.register(registerRequest);
		return ResponseEntity.ok(registeredUser);
	}
}
