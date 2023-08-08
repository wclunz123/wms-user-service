package wms.user.services.userservice.config;

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

import io.jsonwebtoken.ExpiredJwtException;
import wms.user.services.userservice.exceptions.InvalidCredentialsException;
import wms.user.services.userservice.service.JwtUserDetailsService;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService userService;

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
			} catch (ExpiredJwtException ex) {
				System.out.println("JWT Token has expired");
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.loadUserByUsername(username);

			if (jwtTokenManager.validateJwtToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, token, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
