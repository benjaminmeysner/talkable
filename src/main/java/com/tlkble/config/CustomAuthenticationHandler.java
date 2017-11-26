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

//---------------------------------------- //
//--   Used for redirecting upon login  -- //
//---------------------------------------- //
@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws ServletException, IOException {

		//Get current session
		HttpSession session = request.getSession();
		User user = userRepo.findByUsername(authentication.getName());
		session.setAttribute("user", user);
		response.setStatus(HttpServletResponse.SC_OK);
		
		//------------------------
		//Add logic here to redirect upon
		//user type
		response.sendRedirect("/");
	}
		
	

}
