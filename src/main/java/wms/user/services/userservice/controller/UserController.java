package wms.user.services.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.exceptions.InvalidCredentialsException;
import wms.user.services.userservice.exceptions.UserNotFoundException;
import wms.user.services.userservice.model.ApiResponse;
import wms.user.services.userservice.model.LoginRequest;
import wms.user.services.userservice.model.LoginResponse;
import wms.user.services.userservice.model.RegisterRequest;
import wms.user.services.userservice.service.UserService;
import wms.user.services.userservice.service.impl.JwtUserDetailsService;
import wms.user.services.userservice.utils.ApiUtils;
import wms.user.services.userservice.utils.JwtTokenManager;

@CrossOrigin
@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {
	public final static String BASE_URL = "/api/user";
	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenManager jwtTokenManager;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) throws Exception {
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
		return ApiUtils.success(new LoginResponse(token), "Authenticated", HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<CustomUser>> register(@RequestBody RegisterRequest registerRequest)
			throws Exception {
		final CustomUser registeredUser = userService.register(registerRequest);
		return ApiUtils.success(registeredUser, "Created", HttpStatus.CREATED);
	}

	@GetMapping("/get")
	public ResponseEntity<ApiResponse<List<CustomUser>>> findAll() {
		final List<CustomUser> listOfUsers = userService.findAll();
		return ApiUtils.success(listOfUsers, "Success", HttpStatus.OK);
	}

	@GetMapping("/get/{userId}")
	public ResponseEntity<ApiResponse<CustomUser>> findById(@PathVariable Long userId) {
		final CustomUser user = userService.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));

		return ApiUtils.success(user, "Success", HttpStatus.OK);
	}

}
