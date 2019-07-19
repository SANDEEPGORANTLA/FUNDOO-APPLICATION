package com.bridgelabz.fundoo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo.dto.LabelDto;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.utility.Response;
import com.bridgelabz.fundoo.services.LabelServiceImpl;

@RestController
@RequestMapping("/label")
@CrossOrigin(origins="*", allowedHeaders="*",exposedHeaders= {"jwtToken"})
public class LabelController {
	@Autowired
	private LabelServiceImpl labelServiceImpl;

//***************************** create-label ****************************************************************************************************************//
	@PostMapping("/create")
	public ResponseEntity<Response> create(@RequestBody LabelDto labelDto, @RequestHeader String token)  throws UserException,UnsupportedEncodingException
	{
		Response response = labelServiceImpl.create(labelDto, token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

//**************************** update-label *****************************************************************************************************************//
	@PutMapping("/update")
	public ResponseEntity<Response> update(@RequestBody LabelDto labelDto, @RequestHeader String token, @RequestParam String labelId)  throws UserException,UnsupportedEncodingException
	{
		Response response = labelServiceImpl.update(labelDto, token, labelId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

//**************************** delete-label *****************************************************************************************************************//
	@DeleteMapping("/delete")
	public ResponseEntity<Response> delete(@RequestHeader String token, @RequestParam String labelId)  throws UserException,UnsupportedEncodingException
	{
		Response response = labelServiceImpl.delete(token, labelId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

//**************************** get-label  ********************************************************************************************************************//
	@GetMapping("/retrive")
	public List<Label> retrive(@RequestHeader String token) throws UserException,UnsupportedEncodingException
	{
		List<Label> list = labelServiceImpl.retrive(token);
		return list;
	}
//***********************************************************************************************************************************************************//
}
