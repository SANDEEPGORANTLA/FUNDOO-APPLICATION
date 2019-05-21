package com.bridgelabz.fundoo.services;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundoo.dto.UserForgetPasswordDto;
import com.bridgelabz.fundoo.dto.UserLoginDto;
import com.bridgelabz.fundoo.dto.UserRegistrationDto;
import com.bridgelabz.fundoo.model.Response;

public interface UserServiceInterface 
{
	public Response register(UserRegistrationDto userRegistrationDto, HttpServletRequest request);
	public Response login(UserLoginDto userLoginDto);
	public Response forget(UserForgetPasswordDto userForgetPasswordDto, String token);
	public Response validateMail(String token);
	public Response setPassword(UserForgetPasswordDto userforgetPasswordDto, String token);
}
