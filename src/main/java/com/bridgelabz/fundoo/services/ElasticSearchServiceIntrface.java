package com.bridgelabz.fundoo.services;

import java.io.IOException;
import java.util.List;

import com.bridgelabz.fundoo.model.Note;

public interface ElasticSearchServiceIntrface {

	String createNote(Note note) throws IOException;

	String deleteNote(String id) throws Exception;

	Note findById(String id) throws Exception;

	String upDateNote(Note note) throws Exception;

	List<Note> searchByTitle(String title, String userId) throws Exception;

	public List<Note> searchNoteByText(String findString, String token) throws IOException;

}