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

import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.services.NoteServiceImpl;

@RestController
@RequestMapping("/note")
public class NoteController {
	@Autowired
	private NoteServiceImpl noteServiceImpl;

//********************************** create ***********************************************************************//
	@PostMapping("/create")
	public Response create(@RequestBody NoteDto noteDto, @RequestHeader String token) 
	{
		Response response = noteServiceImpl.create(noteDto, token);
		return response;
	}

//********************************** update ***********************************************************************//
	@PutMapping("/update")
	public Response update(@RequestBody NoteDto noteDto, @RequestHeader String token, @RequestParam String noteId) {
		Response response = noteServiceImpl.update(noteDto, token, noteId);
		return response;
	}

//********************************** delete ***********************************************************************//
	@DeleteMapping("/delete")
	public Response delete(@RequestBody NoteDto noteDto, @RequestParam String noteId) {
		Response response = noteServiceImpl.delete(noteDto, noteId);
		return response;
	}

//********************************** get-all ***********************************************************************//
	@GetMapping("/retrieve")
	public List<NoteDto> retrieve(@RequestHeader String token) {
		List<NoteDto> list = noteServiceImpl.retrieve(token);
		return list;
	}

//********************************** Archive **********************************************************************//
	@PostMapping("/archive")
	public Response Archive(@RequestHeader String token, @RequestParam String noteId) {
		Response response = noteServiceImpl.Archive(token, noteId);
		return response;
	}

//********************************** Trash ************************************************************************//
	@PostMapping("/trash")
	public Response Trash(@RequestHeader String token, @RequestParam String noteId) {
		Response response = noteServiceImpl.Trash(token, noteId);
		return response;
	}

//********************************************************************************************//
	@PostMapping("/pin")
	public Response Pin(@RequestHeader String token, @RequestParam String noteId) {
		Response response = noteServiceImpl.Pin(token, noteId);
		return response;
	}
//*******************************************************************************************//

}
