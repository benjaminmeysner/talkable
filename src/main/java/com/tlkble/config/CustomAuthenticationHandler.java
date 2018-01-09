package com.tlkble.config;

import com.tlkble.domain.User;
import com.tlkble.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * CustomAuthenticationHandler
 * @author Ben
 * @issues none
 */
@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws ServletException, IOException {

		/* Retrieve current session
		 * and add user as attribute
		 */
		HttpSession session = request.getSession();
		User user = userRepo.findByUsernameIgnoreCase(authentication.getName());
		session.setAttribute("user", user);
		response.setStatus(HttpServletResponse.SC_OK);
		
		/* redirect to home upon
		 * login
		 */
		response.sendRedirect("/user/home");
	}
		
	

}
