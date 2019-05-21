package com.bridgelabz.fundoo.services;

import java.util.List;

import com.bridgelabz.fundoo.dto.LabelDto;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Response;

public interface LabelServiceInterface 
{
	
	public Response create(LabelDto labelDto, String token);
	public Response update(LabelDto labelDto,String token, String labelId);
	public Response delete(String token, String labelId);
	public List<Label> retrive(String token);
	
}
