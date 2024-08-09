/**
 * 
 */
package wms.user.services.userservice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.entity.Role;
import wms.user.services.userservice.exceptions.UserExceptionControllerAdvice;
import wms.user.services.userservice.model.LoginRequest;
import wms.user.services.userservice.model.RegisterRequest;
import wms.user.services.userservice.repository.UserRepository;
import wms.user.services.userservice.service.UserService;
import wms.user.services.userservice.service.impl.JwtUserDetailsService;
import wms.user.services.userservice.utils.JwtTokenManager;
import wms.user.services.userservice.utils.RoleEnum;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Mock
	private JwtUserDetailsService userDetailsService;

	@Mock
	private JwtTokenManager jwtTokenManager;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setControllerAdvice(new UserExceptionControllerAdvice()).build();
	}

	@Test
	public void testLogin_Success() throws Exception {
		LoginRequest loginRequest = new LoginRequest("username", "password");
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		UserDetails loginUser = new User(loginRequest.getUsername(), loginRequest.getPassword(), grantedAuthorities);

		when(authenticationManager.authenticate(any())).thenReturn(null);
		when(userDetailsService.loadUserByUsername(loginRequest.getUsername())).thenReturn(loginUser);
		when(jwtTokenManager.generateJwtToken(loginUser)).thenReturn("dummyToken");

		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(loginRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Authenticated"))
				.andExpect(jsonPath("$.data.token").value("dummyToken"));
	}

	@Test
	public void testRegister_Success() throws Exception {
		RegisterRequest registerRequest = new RegisterRequest("username", "email@test.com", "password",
				new HashSet<RoleEnum>());
		CustomUser registeredUser = new CustomUser(1L, "username", "email@test.com", "password", new HashSet<Role>());

		when(userService.register(any(RegisterRequest.class))).thenReturn(registeredUser);

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(registerRequest))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value("Created")).andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testFindAll_Success() throws Exception {
		List<CustomUser> userList = new ArrayList<>();
		when(userService.findAll()).thenReturn(userList);

		mockMvc.perform(get("/get")).andExpect(status().isOk()).andExpect(jsonPath("$.message").value("Success"))
				.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	public void testFindById_Success() throws Exception {
		Long userId = 1L;
		CustomUser user = new CustomUser(userId, "username", "email@test.com", "password", new HashSet<Role>());
		when(userService.findById(userId)).thenReturn(user);

		mockMvc.perform(get("/get/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Success")).andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data.username").value("username"))
				.andExpect(jsonPath("$.data.email").value("email@test.com"))
				.andExpect(jsonPath("$.data.roles").exists());
	}

	@Test
	public void testUpdate_Success() throws Exception {
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setRoles(new HashSet<RoleEnum>());

		CustomUser user = new CustomUser(1L, "username", "email@test.com", "password", new HashSet<Role>());
		when(userService.update(any(RegisterRequest.class))).thenReturn(user);

		mockMvc.perform(put("/update").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(registerRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Success")).andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testDelete_Success() throws Exception {
		Long userId = 1L;

		mockMvc.perform(delete("/delete/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Success")).andExpect(jsonPath("$.data").value(userId));
	}
}
