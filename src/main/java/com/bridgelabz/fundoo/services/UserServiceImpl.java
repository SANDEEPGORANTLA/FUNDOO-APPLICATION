package com.bridgelabz.fundoo.services;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.dto.UserForgetPasswordDto;
import com.bridgelabz.fundoo.dto.UserLoginDto;
import com.bridgelabz.fundoo.dto.UserRegistrationDto;
import com.bridgelabz.fundoo.dto.UserSetPasswordDto;
import com.bridgelabz.fundoo.utility.Response;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepositoryInterface;
import com.bridgelabz.fundoo.utility.EmailSenderUtil;
import com.bridgelabz.fundoo.utility.EncryptUtil;
import com.bridgelabz.fundoo.utility.ResponseUtility;
import com.bridgelabz.fundoo.utility.TokenUtility;
import com.bridgelabz.fundoo.utility.Utility;

@Service("UserServiceInterface")
public class UserServiceImpl implements UserServiceInterface {
	@Autowired
	private UserRepositoryInterface userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private EmailSenderUtil emailSenderUtil;
	@Autowired
	private EncryptUtil encryptUtil;

//**************************** registration *******************************************************//
	public Response register(UserRegistrationDto userRegistrationDto, HttpServletRequest request) {
		boolean User = userRepository.findByEmailId(userRegistrationDto.getEmailId()).isPresent();
		if (User) {
			Response respone = ResponseUtility.getResponse(404, "user exites");
			return respone;
		} else {
			User user = modelMapper.map(userRegistrationDto, User.class);
			user.setPassword(encryptUtil.encryptPassword(user.getPassword()));
			user.setRegisterStamp(Utility.todayDate());
			user.setUpdateStamp(Utility.todayDate());
			registerActivationMail(user, request);
			user.setVerified(true);
			userRepository.save(user);
			Response response = ResponseUtility.getResponse(400, "user Register Successfully");
			return response;
		}
	}
//*************************************************************************************************//
	private void registerActivationMail(User user, HttpServletRequest request) {
		String token = TokenUtility.generateToken(user.getUserId());
		StringBuffer requestUrl = request.getRequestURL();
		System.out.println(requestUrl);
		String url = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/activation/" + token;
		System.out.println(url);
		emailSenderUtil.mailSender(user.getEmailId(), "user.email.subject", url);
	}
//**************************** login **************************************************************//
	public Response login(UserLoginDto userLoginDto, HttpServletResponse resopnse) {
		boolean email = userRepository.findByEmailId(userLoginDto.getEmailId()).isPresent();
		if (!email) {
			Response response1 = ResponseUtility.getResponse(404, "User-Email-Invaild");
			return response1;
		}
		User user = userRepository.findByEmailId(userLoginDto.getEmailId()).get();
		boolean Password = encryptUtil.ispassword(userLoginDto, user);
		if (!Password) {
			Response response = ResponseUtility.getResponse(404, "User-Login-Failed");
			return response;
		}
		if (!(user.isVerified())) {
			Response response = ResponseUtility.getResponse(404, "user login failed");
			return response;
		} else {
			user.setUpdateStamp(Utility.todayDate());
			userRepository.save(user);
			String token = TokenUtility.generateToken(user.getUserId());
			user.setToken(token);
			Response response = ResponseUtility.getResponse(400, token, "login is Sucessfull");
			return response;
		}
	}
//*************************** forget-password *****************************************************//
	public Response forget(UserForgetPasswordDto userForgetPasswordDto) {
		String email = userForgetPasswordDto.getEmailId();
		boolean forget = userRepository.findByEmailId(email).isPresent();
		Response response = null;
		if (!forget) {
			response = ResponseUtility.getResponse(404, "email id is not valid");
			return response;
		} else {
			User user = userRepository.findByEmailId(userForgetPasswordDto.getEmailId()).get();
			String token = TokenUtility.generateToken(user.getUserId());
			emailSenderUtil.mailSender(user.getEmailId(), "user.email.subject",
					"http://localhost:4200/setpassword/" + token);
			user.setUpdateStamp(Utility.todayDate());
			userRepository.save(user);
			response = ResponseUtility.getResponse(400, token, "email id is not valid");
			return response;
		}
	}
//*****************************validate email *****************************************************//
	public Response validateMail(String token) {
		User user = new User();
		String id = TokenUtility.verifyToken(token);
		boolean check = userRepository.findByEmailId(id).isPresent();
		if (!check) {
			user.setVerified(true);
			Response response = ResponseUtility.getResponse(400, token, "Email is valid sucessfully");
			return response;
		} else {
			Response response = ResponseUtility.getResponse(404, token, "User is not valid");
			return response;
		}
	}
//*************************** reset password ******************************************************//
	public Response setPassword(UserSetPasswordDto userSetPasswordDto, String token) {
		String id = TokenUtility.verifyToken(token);
		User user = userRepository.findById(id).get();
		String email = user.getEmailId();
		boolean isUser = userRepository.findByEmailId(email).isPresent();
		if (!isUser) {
			Response response = ResponseUtility.getResponse(404, token, "user email invalid");
			return response;
		} else {
			User userId = userRepository.findByEmailId(email).get();
			userId.setPassword(encryptUtil.encryptPassword(userSetPasswordDto.getpassword()));
			userId.setUpdateStamp(Utility.todayDate());
			userRepository.save(userId);
			Response response = ResponseUtility.getResponse(400, token, "User Password is Successfully Set");
			return response;
		}
	}
//*************************************************************************************************//
	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}
}
