package com.tlkble.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.tlkble.services.UserService;

/**
 * CustomAuthenticationProvider
 * @author Ben
 * @issues none
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = (String) authentication.getName();
		String password = (String) authentication.getCredentials();

		com.tlkble.domain.User user = userService.findByUsername(username);
		
		
		if (user == null) {
			throw new BadCredentialsException("#bad-credentials");
		}
		
		/* When user is not null
		 * check username equals
		 * the registered name
		 * 
		 * Check password entered
		 * is equal to password in
		 * database.
		 */
		
		if (user != null) {
			if (!(username.equalsIgnoreCase(user.getUsername())))
				throw new BadCredentialsException("#bad-credentials");
			
			if (!(BCrypt.checkpw(password, user.getPassword()))) {
				throw new BadCredentialsException("#bad-credentials");
			}
		}
		
		/* Password must then be correct
		 * proceed...
		 */
		
		System.out.println("Testing processes");
		
		return new UsernamePasswordAuthenticationToken(authentication.getName(),
				authentication.getCredentials().toString(), getAuthorities(user));
	}

	@SuppressWarnings("serial")
	public static Collection<GrantedAuthority> getAuthorities(com.tlkble.domain.User user) {

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority grantedAuthority = new GrantedAuthority() {

			@Override
			public String getAuthority() {
				if (user.getRole().equals("ROLE_USER"))
					return "ROLE_USER";
				else if (user.getRole().equals("ROLE_ADMIN"))
					return "ROLE_ADMIN";
				return null;
			}
		};

		grantedAuthorities.add(grantedAuthority);
		return grantedAuthorities;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
