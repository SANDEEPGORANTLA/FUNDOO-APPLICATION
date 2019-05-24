package com.bridgelabz.fundoo.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.dto.LabelDto;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Response;

@Service
public interface LabelServiceInterface 
{
	 Response create(LabelDto labelDto, String token);
	 Response update(LabelDto labelDto,String token, String labelId);
	 Response delete(String token, String labelId);
	 List<Label> retrive(String token);
}
