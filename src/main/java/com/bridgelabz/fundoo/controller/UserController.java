package com.bridgelabz.fundoo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoo.dto.UserForgetPasswordDto;
import com.bridgelabz.fundoo.dto.UserLoginDto;
import com.bridgelabz.fundoo.dto.UserRegistrationDto;
import com.bridgelabz.fundoo.dto.UserSetPasswordDto;
import com.bridgelabz.fundoo.utility.Response;
import com.bridgelabz.fundoo.services.AmazonClient;
import com.bridgelabz.fundoo.services.UserServiceImpl;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = { "jwtToken" })
public class UserController {

	private AmazonClient amazonClient;

	@Autowired
	public UserController(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
	}

	@Autowired
	private UserServiceImpl userServiceImpl;

//************************ registration ***************************************************************//

	@PostMapping("/register")
	public ResponseEntity<Response> userRegister(@RequestBody UserRegistrationDto userRegistrationDto,
			HttpServletRequest request) throws UserException, UnsupportedEncodingException {
		Response response = userServiceImpl.register(userRegistrationDto, request);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//************************ token-activation ***************************************************************//
	@GetMapping("/activation/{token}")
	public ResponseEntity<Response> validatResponse(@RequestHeader String token)
			throws UserException, UnsupportedEncodingException {
		Response response = userServiceImpl.validateMail(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//*********************** login *************************************************************************//	
	@PostMapping("/login")
	public ResponseEntity<Response> userLogin(@RequestBody UserLoginDto userLoginDto, HttpServletResponse resopnse)
			throws UserException, UnsupportedEncodingException {

		Response response = userServiceImpl.login(userLoginDto, resopnse);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//*********************************************************************************************************//
	@PutMapping("/forgotpassword")
	public ResponseEntity<Response> forgetPassword(@RequestBody UserForgetPasswordDto userForgetpassword)
			throws UserException, UnsupportedEncodingException {
		Response response = userServiceImpl.forget(userForgetpassword);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//************************ forget-password ****************************************************************//	
	@PutMapping("/setPassword")
	public ResponseEntity<Response> setPassword(@RequestBody UserSetPasswordDto userSetPasswordDto,
			@RequestHeader String token) throws UserException, UnsupportedEncodingException {
		Response response = userServiceImpl.setPassword(userSetPasswordDto, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//*********************************************************************************************************//
	@PostMapping("/uploadFile")
	public ResponseEntity<Response> uploadFile(@RequestPart(value = "file") MultipartFile file,
			@RequestHeader String token) throws IOException {
		Response response = amazonClient.uploadFile(file, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@DeleteMapping("/deleteFile")
	public ResponseEntity<Response> deleteFile(@RequestHeader String fileName, @RequestHeader String token)
			throws IOException {
		Response response = amazonClient.deleteFileFromS3Bucket(fileName, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/get")
	public URL getPropic(@RequestParam String token) {
		URL response = amazonClient.getPropic(token);
		return response;

	}
}
