package fpt.training.studentManagementRest.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fpt.training.studentManagementRest.model.UserAccount;

@Service
public class UserDetailServices implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserAccount user = userService.getUser(userName);
		if (user == null) {
			throw new UsernameNotFoundException(userName);
		}
		return new User(user.getUsername(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority(user.getRole().getRoleName())));
	}

}
