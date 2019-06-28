package com.bridgelabz.fundoo.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.services.ElasticSearchServiceIntrface;

@RestController
@RequestMapping("/EsNote")
public class ElasticController 
{
	@Autowired(required = true)
	private ElasticSearchServiceIntrface esServiceInterface;
	
//************************************* create ************************************************//	
	@PostMapping("/create")
	public String createNote(@RequestBody Note note) throws IOException 
	{
		return esServiceInterface.createNote(note);
	}
//************************************* delete ************************************************//
	@DeleteMapping("/delete")
	public String deleteNote(@RequestParam(value = "id") String id) throws Exception 
	{
		return esServiceInterface.deleteNote(id);
	}
//************************************* find-by-id ************************************************//
	@PostMapping("/find")
	public Note findById(@RequestParam(value="id") String id) throws Exception 
	{
		return esServiceInterface.findById(id);
	}

//************************************* update ************************************************//
	@PutMapping("/update")
	public String updateNote(@RequestBody Note note) throws Exception
	{
		return esServiceInterface.upDateNote(note);
	}

}
