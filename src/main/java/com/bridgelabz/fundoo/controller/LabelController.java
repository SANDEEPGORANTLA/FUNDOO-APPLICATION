package com.bridgelabz.fundoo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.services.LabelServiceImpl;

@RestController
@RequestMapping("/label")
public class LabelController {
	@Autowired
	private LabelServiceImpl labelServiceImpl;

//***************************** create-label *****************************************************************//
	@PostMapping("/create")
	public Response create(@RequestBody LabelDto labelDto, @RequestHeader String token) {
		Response response = labelServiceImpl.create(labelDto, token);
		return response;
	}

//**************************** update-label ******************************************************************//
	@PutMapping("/update")
	public Response update(@RequestBody LabelDto labelDto, @RequestHeader String token, @RequestParam String labelId) {
		Response response = labelServiceImpl.update(labelDto, token, labelId);
		return response;
	}

//**************************** delete-label ******************************************************************//
	@DeleteMapping("/delete")
	public Response delete(@RequestHeader String token, @RequestParam String labelId) {
		Response response = labelServiceImpl.delete(token, labelId);
		return response;
	}

//**************************** get-label  ********************************************************************//
	@GetMapping("/retrive")
	public List<Label> retrive(@RequestHeader String token) {
		List<Label> list = labelServiceImpl.retrive(token);
		return list;
	}

}
