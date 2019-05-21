package com.bridgelabz.fundoo.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.dto.UserForgetPasswordDto;
import com.bridgelabz.fundoo.dto.UserLoginDto;
import com.bridgelabz.fundoo.dto.UserRegistrationDto;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.services.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController 
{
	@Autowired(required = true)
	private UserServiceImpl userServiceImpl;
//************************ registration ***************************************************************//
	@PostMapping("/register")
	public Response userRegister(@RequestBody UserRegistrationDto userRegistrationDto, HttpServletRequest request) 
	{
		Response response = userServiceImpl.register(userRegistrationDto, request);
		return response;
	}
////*********************** login *************************************************************************//	
	@PostMapping("/login")
	public Response userLogin(@RequestBody UserLoginDto userLoginDto) 
	{
		Response response = userServiceImpl.login(userLoginDto);
		return response;
	}
//************************ forget-password ****************************************************************//	
	@PutMapping("/forgetpassword")
	public Response forgetPassword(@RequestBody UserForgetPasswordDto userForgetpassword,@RequestHeader String token)
	{
		Response response = userServiceImpl.forget(userForgetpassword,token);
		return response;
	}
//************************ token-activation ***************************************************************//
	@GetMapping("/activation/{token}")
	public Response validatResponse(@PathVariable String token)
	{
		Response response=userServiceImpl.validateMail(token);
		return response;	
	}
//*********************************************************************************************************//
	@PutMapping("/setPassword/{token}")
	public Response setPassword(@RequestBody UserForgetPasswordDto userForgetpassword,@PathVariable String token)
	{
	Response response=userServiceImpl.setPassword(userForgetpassword, token);
	return response;
	}	
}
