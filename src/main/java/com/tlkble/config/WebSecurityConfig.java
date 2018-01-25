package com.tlkble.config;

import com.tlkble.config.CustomAuthenticationFailureHandler;
import com.tlkble.config.CustomAuthenticationHandler;
import com.tlkble.config.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

/**
 * WebSecurityConfig
 * 
 * @author Ben
 * @issues none
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/* sets custom authentication handlers */
	@Autowired
	private CustomAuthenticationProvider authProvider;

	@Autowired
	private CustomAuthenticationHandler authHandler;

	@Autowired
	private CustomAuthenticationFailureHandler failureHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/register/**", "/login/**", "/public/**", "/webjars/**").permitAll()
				.antMatchers("/user/home").hasRole("USER").antMatchers("/event/**").hasRole("USER")
				.antMatchers("/events/**").hasRole("USER").antMatchers("/topic/**").hasRole("USER")
				.antMatchers("/admin/**").hasRole("ADMIN").and().formLogin().loginPage("/login")
				.successHandler(authHandler).failureHandler(failureHandler).and().logout().logoutUrl("/user/logout")
				.logoutSuccessUrl("/login").permitAll().and().exceptionHandling().accessDeniedPage("/404").and().csrf()
				.disable().headers()
				.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
	}

	/**
	 * Allows Spring Security to ignore the resources
	 */
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
}