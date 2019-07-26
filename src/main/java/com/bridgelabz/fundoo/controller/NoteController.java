package com.bridgelabz.fundoo.controller;

import java.io.IOException;
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
import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.utility.Response;
import com.bridgelabz.fundoo.services.ElasticSearchServiceIntrface;
import com.bridgelabz.fundoo.services.NoteServiceInteface;

@RestController
@RequestMapping(value = "/note")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = { "jwtToken" })
public class NoteController {
	@Autowired
	private NoteServiceInteface iNoteService;

	@Autowired
	private ElasticSearchServiceIntrface esServiceInterface;

//********************************** create ***************************************************************************************************************//

	@PostMapping("/create")
	public ResponseEntity<Response> create(@RequestBody NoteDto noteDto, @RequestHeader String token)
			throws UserException, UnsupportedEncodingException {
		Response response = iNoteService.create(noteDto, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
//********************************** update ***************************************************************************************************************//

	@GetMapping("/searchString")
	public List<Note> searchNoteByText(@RequestParam String findString, @RequestHeader String token)
			throws IOException {
		return esServiceInterface.searchNoteByText(findString, token);
	}

	@PutMapping("/update")
	public ResponseEntity<Response> update(@RequestBody NoteDto noteDto, @RequestHeader String token,
			@RequestParam String noteId) throws UserException, UnsupportedEncodingException {
		Response response = iNoteService.update(noteDto, token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
//********************************** delete ***************************************************************************************************************//

	@DeleteMapping("/delete")
	public ResponseEntity<Response> delete(@RequestHeader String token, @RequestParam String noteId)
			throws UserException, UnsupportedEncodingException {
		Response response = iNoteService.delete(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
//********************************** get-all **************************************************************************************************************//

	@GetMapping("/retrieveAll")
	public List<Note> retrieve(@RequestHeader String token) {
		List<Note> list = iNoteService.retrieve(token);
		return list;
	}
//********************************** Archive **************************************************************************************************************//

	@PutMapping("/archive")
	public ResponseEntity<Response> Archive(@RequestHeader String token, @RequestParam String noteId)
			throws UserException, UnsupportedEncodingException {
		Response response = iNoteService.Archive(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
//********************************** Trash ****************************************************************************************************************//

	@PutMapping("/trash")
	public ResponseEntity<Response> Trash(@RequestHeader String token, @RequestParam String noteId)
			throws UserException, UnsupportedEncodingException {
		Response response = iNoteService.Trash(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//*********************************************************************************************************************************************************//

	@DeleteMapping("/pin")
	public ResponseEntity<Response> Pin(@RequestHeader String token, @RequestParam String noteId)
			throws UserException, UnsupportedEncodingException {
		Response response = iNoteService.Pin(token, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
//*********************************************************************************************************************************************************//

	@PutMapping("/addLabels")
	public ResponseEntity<Response> addLabels(@RequestHeader String token, @RequestParam String noteId,
			@RequestParam String labelId) {
		System.out.println("AddLabels: NoteId:" + noteId + "  LabelId:" + labelId);
		Response response = iNoteService.addLabelsToNote(token, noteId, labelId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//********************************************************************************************************************************************************//	
	@PutMapping("/revomeLabels")
	public ResponseEntity<Response> revomeLabels(@RequestHeader String token, @RequestParam String noteId,
			@RequestParam String labelId) {
		Response response = iNoteService.revomeLabelsFromNote(token, noteId, labelId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//************************************bin-all*************************************************************************************************************//
	@GetMapping("/bin")
	public List<Note> bin(@RequestHeader String token) {
		List<Note> list = iNoteService.bin(token);
		return list;
	}

//********************************* archive-all************************************************************************************************************//
	@GetMapping("/archiveAll")
	public List<Note> getArchiveNotes(@RequestHeader String token) {
		List<Note> list = iNoteService.archiveNote(token);
		return list;
	}

//****************************** pin-all ******************************************************************************************************************//	
	@GetMapping("/pinAll")
	public List<Note> getPinNotes(@RequestHeader String token) {
		List<Note> list = iNoteService.pinnedNote(token);
		return list;

	}

//**************************** color **********************************************************************************************************************//
	@PutMapping("/color")
	public ResponseEntity<Response> addColour(@RequestParam String noteId, @RequestHeader String token,
			@RequestBody String color) throws UserException, UnsupportedEncodingException {
		Response response = iNoteService.addColour(noteId, token, color);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

//*********************************************************************************************************************************************************//
	@GetMapping("/retriveLabels")
	public List<Label> getLabelsFromNote(@RequestParam String noteId, @RequestHeader String token)

	{
		List<Label> labels = iNoteService.getLabelsFromNote(noteId, token);
		return labels;
	}
}
