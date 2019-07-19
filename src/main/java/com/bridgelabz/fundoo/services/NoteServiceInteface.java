package com.bridgelabz.fundoo.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.utility.Response;

@Service
public interface NoteServiceInteface 
{
	
	Response create(NoteDto noteDto, String token);
	Response update(NoteDto noteDto, String token, String noteId);
	Response delete(String token, String noteId);
	List<Note> retrieve(String token);
	Response Archive(String token, String noteId);
	Response Trash(String token, String noteId);
	Response Pin(String token, String noteId);
	Response addLabelsToNote(String token, String noteId, String labelId);
	Response revomeLabelsFromNote(String token, String noteId, String labelId);
	List<Note> bin(String token);
	List<Note> archiveNote(String token);
	List<Note> pinnedNote(String token);
	Response addColour(String noteId,String token,String colour);
	List<Label> getLabelsFromNote(String noteId, String token);
	
}
