package wms.user.services.userservice.utils.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import wms.user.services.userservice.entity.User;
import wms.user.services.userservice.exceptions.InvalidCredentialsException;
import wms.user.services.userservice.service.UserService;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenManager jwtTokenManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String tokenHeader = request.getHeader("Authorization");
		String username = null;
		String token = null;

		if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			token = tokenHeader.substring(7); // Remove the Bearer prefix and get token

			try {
				username = jwtTokenManager.getUsernameFromToken(token);
			} catch (IllegalArgumentException ex) {
				System.out.println("Unable to get JWT Token");
			}
//			catch (ExpiredJwtException ex) {
//				System.out.println("JWT Token has expired");
//			}
		} else {
			throw new InvalidCredentialsException("Invalid jwt token in Authorization header.");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails user = userService.loadUserByUsername(username);

			if (jwtTokenManager.validateJwtToken(token, user)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
						null, user.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
