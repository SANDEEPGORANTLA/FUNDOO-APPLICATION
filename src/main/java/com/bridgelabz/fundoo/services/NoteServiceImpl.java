package com.bridgelabz.fundoo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.NoteRepositoryInterface;
import com.bridgelabz.fundoo.repository.UserRepositoryInterface;
import com.bridgelabz.fundoo.utility.ResponseUtility;
import com.bridgelabz.fundoo.utility.TokenUtility;
import com.bridgelabz.fundoo.utility.Utility;

@Service("NoteServiceInteface")
public class NoteServiceImpl implements NoteServiceInteface {
	@Autowired
	private NoteRepositoryInterface noteRepositoryInterface;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepositoryInterface userRepositoryInterface;

//********************************* create-note *********************************************************//	
	@Override
	public Response create(NoteDto noteDto, String token) {
		String id = TokenUtility.verifyToken(token);
		User user = userRepositoryInterface.findById(id).get();
		Optional<User> user1 = userRepositoryInterface.findByEmailId(user.getEmailId());
		if (user1.isPresent()) {
			System.out.println("first");
			Note note = modelMapper.map(noteDto, Note.class);
			note.setUserId(user1.get().getUserId());
			System.out.println(note);
			note.setCreateTime(Utility.todayDate());
			note.setUpdateTime(Utility.todayDate());
			note = noteRepositoryInterface.save(note);
			System.out.println("================================================");
			List<Note> notes = user1.get().getNotes();
			System.err.println(notes);
			if (!(notes == null)) 
			{
				notes.add(note);
				user1.get().setNotes(notes);
			} 
			else 
			{
				notes = new ArrayList<Note>();
				notes.add(note);
				user1.get().setNotes(notes);
			}
			
			userRepositoryInterface.save(user1.get());
			Response response = ResponseUtility.getResponse(200, "Note is created Sucessfully");
			return response;
		}
		Response response = ResponseUtility.getResponse(204, "Note is not created");
		return response;
	}

//******************************** update-note *********************************************************//
	@Override
	public Response update(NoteDto noteDto, String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			note.get().setTitle(noteDto.getTitle());
			note.get().setDescription(noteDto.getDescription());
			note.get().setUpdateTime(Utility.todayDate());
			noteRepositoryInterface.save(note.get());
			Response response = ResponseUtility.getResponse(205, token, "Note is updated Successfully");
			return response;
		}
		Response response = ResponseUtility.getResponse(205, token, "Note is not updated");
		return response;
	}

//*******************************  delete-note  ********************************************************//
	@Override
	public Response delete(NoteDto noteDto, String noteId) {
		Optional<Note> note = noteRepositoryInterface.findByNoteId(noteId);
		if (note.isPresent()) {
			note.get().setCreateTime(Utility.todayDate());
			note.get().setTitle(noteDto.getTitle());
			note.get().setDescription(noteDto.getDescription());
			note.get().setUpdateTime(Utility.todayDate());
			noteRepositoryInterface.delete(note.get());
			Response response = ResponseUtility.getResponse(205, "Note is Deleated Successfully");
			return response;
		}
		Response response = ResponseUtility.getResponse(205, "Note is not Deleated");
		return response;
	}

//******************************   Retrieve   **********************************************************//
	@Override
	public List<NoteDto> retrieve(String token) {
		String id = TokenUtility.verifyToken(token);
		List<Note> note = (List<Note>) noteRepositoryInterface.findByUserId(id);
		List<NoteDto> listNote = new ArrayList<>();
		for (Note userNote : note) {
			NoteDto noteDto = modelMapper.map(userNote, NoteDto.class);
			System.out.println("notes all created in  sub");
			listNote.add(noteDto);
			System.out.println(listNote);
		}
		return listNote;
	}

//*******************************   Archive    **********************************************************//	
	@Override
	public Response Archive(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			note.get().setPin(false);
			if ((note.get().Archive()) == false) {
				note.get().setArchive(true);
			} else {
				note.get().setArchive(false);
			}
			note.get().setUpdateTime(Utility.todayDate());
			noteRepositoryInterface.save(note.get());
			Response response = ResponseUtility.getResponse(204, token, "Archieve is created Successfully");
			return response;
		} else {
			Response response = ResponseUtility.getResponse(204, token, "Note does not create Archieve");
			return response;
		}
	}

//******************************    Trash    ************************************************************//
	@Override
	public Response Trash(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			note.get().setTrash(true);
			note.get().setUpdateTime(Utility.todayDate());
			noteRepositoryInterface.save(note.get());
			Response response = ResponseUtility.getResponse(200, token, "Trash is created with in the Note");
			return response;
		}
		Response response = ResponseUtility.getResponse(204, "0", "Note doesnot created in Trash");
		return response;
	}

//******************************    Pin     *************************************************************//
	@Override
	public Response Pin(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			note.get().setPin(true);
			note.get().setUpdateTime(Utility.todayDate());
			noteRepositoryInterface.save(note.get());
			Response response = ResponseUtility.getResponse(200, token, "Pin is created with in the Note");
			return response;
		}
		Response response = ResponseUtility.getResponse(204, "0", "Note have doesnot Created the Pin");
		return response;
	}
//******************************************************************************************************//

}
