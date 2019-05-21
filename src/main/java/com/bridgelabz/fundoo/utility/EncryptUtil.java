package com.bridgelabz.fundoo.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.dto.UserLoginDto;
import com.bridgelabz.fundoo.model.User;

@Component
public class EncryptUtil 
{
	@Autowired
	private PasswordEncoder passwordEncoder;
	public String encryptPassword(String  password)
	{
		return passwordEncoder.encode(password);	
	}
	public boolean ispassword(UserLoginDto loginDto,User user)
	{
		return passwordEncoder.matches(loginDto.getPassword(),user.getPassword());	
	}
}