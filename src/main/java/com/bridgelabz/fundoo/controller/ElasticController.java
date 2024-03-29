package com.bridgelabz.fundoo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.services.ElasticSearchServiceIntrface;

@RestController
@RequestMapping("/EsNote")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = { "jwtToken" })
public class ElasticController {
	@Autowired(required = true)
	private ElasticSearchServiceIntrface esServiceInterface;

//************************************* create ************************************************//	
	@PostMapping("/create")
	public String createNote(@RequestBody Note note) throws IOException {
		return esServiceInterface.createNote(note);
	}

//************************************* delete ************************************************//
	@DeleteMapping("/delete")
	public String deleteNote(@RequestParam(value = "id") String id) throws Exception {
		return esServiceInterface.deleteNote(id);
	}

//************************************* find-by-id ************************************************//
	@PostMapping("/find")
	public Note findById(@RequestParam(value = "id") String id) throws Exception {
		return esServiceInterface.findById(id);
	}

//************************************* update ************************************************//
	@PutMapping("/update")
	public String updateNote(@RequestBody Note note) throws Exception {
		return esServiceInterface.upDateNote(note);
	}

//************************************* search ***************************************************//
	@GetMapping(value = "/search")
	public List<Note> searchByTitle(@RequestParam(value = "title") String title, String userId) throws Exception {
		return esServiceInterface.searchByTitle(title, userId);
	}

	@GetMapping("/searchString")
	public List<Note> searchNoteByText(@RequestParam String findString, @RequestHeader String token)
			throws IOException {
		return esServiceInterface.searchNoteByText(findString, token);
	}
}
