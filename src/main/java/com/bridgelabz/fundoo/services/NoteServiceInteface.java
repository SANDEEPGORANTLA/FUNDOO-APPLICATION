package com.bridgelabz.fundoo.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.model.Response;

@Service
public interface NoteServiceInteface 
{
	public Response create(NoteDto noteDto, String token);
	public Response update(NoteDto noteDto, String token, String noteId);
	public Response delete(NoteDto noteDto, String noteId);
	public List<NoteDto> retrieve(String token);
	public Response Archive(String token, String noteId);
	public Response Trash(String token, String noteId);
	public Response Pin(String token, String noteId);
}
