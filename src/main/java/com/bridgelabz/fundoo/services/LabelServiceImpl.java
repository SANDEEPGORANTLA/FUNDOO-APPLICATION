package com.bridgelabz.fundoo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.LabelDto;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.LabelRepositoryInterface;
import com.bridgelabz.fundoo.repository.UserRepositoryInterface;
import com.bridgelabz.fundoo.utility.ResponseUtility;
import com.bridgelabz.fundoo.utility.TokenUtility;
import com.bridgelabz.fundoo.utility.Utility;

@Service("LabelServiceInterface")
public class LabelServiceImpl implements LabelServiceInterface {
	@Autowired
	private LabelRepositoryInterface labelRepositoryInterface;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepositoryInterface userRepositoryInterface;

//********************************  create  ******************************************************************//
	@Override
	public Response create(LabelDto labelDto, String token) {
		String id = TokenUtility.verifyToken(token);
		User user = userRepositoryInterface.findById(id).get();
		Optional<User> user1 = userRepositoryInterface.findByEmailId(user.getEmailId());
		if (user1.isPresent()) {
			Label label = modelMapper.map(labelDto, Label.class);
			label.setLabelName(label.getLabelName());
			label.setCreateTime(Utility.todayDate());
			label.setUpdateTime(Utility.todayDate());
			List<Label> labels = user1.get().getLabels();
			if(!(labels==null))
			{
				labels.add(label);
				user1.get().setLabels(labels);
			}
			else
			{
				labels=new ArrayList<Label>();
				labels.add(label);
				user1.get().setLabels(labels);
			}
			userRepositoryInterface.save(user1.get());
			labelRepositoryInterface.save(label);
			Response response = ResponseUtility.getResponse(200, token, "Label is created Sucessfully");
			return response;
		}
		Response response = ResponseUtility.getResponse(204, "Label is not created");
		return response;
	}
//******************************* update *********************************************************************//
	@SuppressWarnings("unused")
	@Override
	public Response update(LabelDto labelDto, String token, String labelId) {
		String id = TokenUtility.verifyToken(token);
		User user = userRepositoryInterface.findById(id).get();
		Optional<Label> label = labelRepositoryInterface.findByLabelId(labelId);
		if (label.isPresent()) {
			label.get().setLabelName(labelDto.getLabelName());
			label.get().setUpdateTime(Utility.todayDate());
			labelRepositoryInterface.save(label.get());
			Response response = ResponseUtility.getResponse(200, token, "Label is updated successfully");
			return response;
		}
		Response response = ResponseUtility.getResponse(204, "Label is not updated");
		return response;
	}

//****************************** delete **********************************************************************//
	@SuppressWarnings("unused")
	@Override
	public Response delete(String token, String labelId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Label> label = labelRepositoryInterface.findByLabelId(labelId);
		if (label.isPresent()) {
			label.get().setCreateTime(Utility.todayDate());
			label.get().setUpdateTime(Utility.todayDate());
			labelRepositoryInterface.deleteById(labelId);
			Response response = ResponseUtility.getResponse(200, token, "Lable is Deleated Successfully");
			return response;
		}
		Response response = ResponseUtility.getResponse(204, "Label is not Deleated");
		return response;
	}
//******************************* retrieve *******************************************************************//
	@Override
	public List<Label> retrive(String token)
	{
	String id=TokenUtility.verifyToken(token);
	List<Label> lable=(List<Label>)labelRepositoryInterface.findByUserId(id);
	lable=labelRepositoryInterface.findAll();
	return lable;
	}
//************************************************************************************************************//	
}
