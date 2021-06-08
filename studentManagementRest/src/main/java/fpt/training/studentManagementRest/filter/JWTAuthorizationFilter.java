package fpt.training.studentManagementRest.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import fpt.training.studentManagementRest.utils.AuthenticationConfigConstants;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(AuthenticationConfigConstants.HEADER_STRING);

		if (header == null || !header.startsWith(AuthenticationConfigConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken token = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(token);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(AuthenticationConfigConstants.HEADER_STRING);
		if (token != null) {
			DecodedJWT jwtToken = JWT.require(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()))
					.build().verify(token.replace(AuthenticationConfigConstants.TOKEN_PREFIX, ""));

			String userName = jwtToken.getSubject();
			String role = jwtToken.getClaim("role").asString();

			if (userName != null) {
				return new UsernamePasswordAuthenticationToken(userName, null, getAuthorities(role));
			}
			return null;
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}
}
