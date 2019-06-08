package com.bridgelabz.fundoo.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bridgelabz.fundoo.dto.UserForgetPasswordDto;
import com.bridgelabz.fundoo.dto.UserLoginDto;
import com.bridgelabz.fundoo.dto.UserRegistrationDto;
import com.bridgelabz.fundoo.dto.UserSetPasswordDto;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;

public interface UserServiceInterface 
{
	 Response register(UserRegistrationDto userRegistrationDto, HttpServletRequest request);
	 Response login(UserLoginDto userLoginDto, HttpServletResponse resopnse);
	 Response validateMail(String token);
	 Response forget(UserForgetPasswordDto userForgetPasswordDto); 
	 Response setPassword(UserSetPasswordDto userSetPasswordDto, String token);
	 List<User> getAll();

}
