package fpt.training.studentManagementRest.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fpt.training.studentManagementRest.model.UserAccount;
import fpt.training.studentManagementRest.utils.AuthenticationConfigConstants;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManagement;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManagement) {
		super();
		this.authenticationManagement = authenticationManagement;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserAccount user = new ObjectMapper().readValue(request.getInputStream(), UserAccount.class);
			System.out.println(user.getUsername() + user.getPassword());

			return authenticationManagement.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String token = JWT.create()
				.withSubject(
						((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
				.withClaim("role", authResult.getAuthorities().iterator().next().getAuthority())
				.withExpiresAt(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()));

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"" + AuthenticationConfigConstants.HEADER_STRING + "\":\""
				+ AuthenticationConfigConstants.TOKEN_PREFIX + token + "\"}");

		response.addHeader(AuthenticationConfigConstants.HEADER_STRING,
				AuthenticationConfigConstants.TOKEN_PREFIX + token);
	}
}
