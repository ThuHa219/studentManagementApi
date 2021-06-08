package fpt.training.studentManagementRest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import fpt.training.studentManagementRest.filter.JWTAuthenticationFilter;
import fpt.training.studentManagementRest.filter.JWTAuthorizationFilter;
import fpt.training.studentManagementRest.service.UserDetailServices;
import fpt.training.studentManagementRest.utils.AuthenticationConfigConstants;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailServices userDetailServices;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll()
			.antMatchers("/login").permitAll()
			
			.antMatchers(HttpMethod.GET, "/api/students").hasAnyAuthority("ADMIN", "STUDENT")
			
			.antMatchers("/api/students/enroll").hasAnyAuthority("STUDENT")
			.antMatchers("/api/students/unenroll").hasAnyAuthority("STUDENT")
			.antMatchers("/api/user/changePassword").hasAnyAuthority("STUDENT")
			.antMatchers("/api/classes/enroll").hasAnyAuthority("STUDENT")
			
			.antMatchers("/api/classes/**").hasAnyAuthority("ADMIN")
			.antMatchers("/api/classes").hasAnyAuthority("ADMIN")
            .antMatchers("/api/subjects/**/classes").hasAnyAuthority("ADMIN")
            .antMatchers("/api/students/**").hasAnyAuthority("ADMIN")
            .antMatchers(HttpMethod.POST, "/api/students").hasAnyAuthority("ADMIN")
            .antMatchers("/api/subjects/**").hasAnyAuthority("ADMIN")
            .antMatchers("/api/subjects").hasAnyAuthority("ADMIN")
            
			.anyRequest().authenticated()
			.and()
			.addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.addFilter(new JWTAuthorizationFilter(authenticationManager()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailServices).passwordEncoder(passwordEncoder);
	}
}
