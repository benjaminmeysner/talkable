package com.tlkble.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.tlkble.services.UserService;

/**
 * Cloak a password for viewing online
 * 
 * @author Ben
 *
 *
 */

public class PasswordCloak {

	@Autowired
	UserService userService;

	public static String cloakPassword(String pwd_) {

		/** cloak password **/
		char prefix_, suffix_;
		int pwdlength_;

		pwdlength_ = pwd_.length() - 1;
		prefix_ = pwd_.charAt(0);
		suffix_ = pwd_.charAt(pwdlength_);

		pwd_ = pwd_.replaceAll(".", "*");

		char[] pwd_arr_ = pwd_.toCharArray();

		pwd_arr_[0] = prefix_;
		pwd_arr_[pwdlength_] = suffix_;

		System.out.println(pwd_);

		return String.valueOf(pwd_arr_);
	}
}
