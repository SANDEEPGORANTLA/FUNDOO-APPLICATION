package com.bridgelabz.fundoo.services;

import java.io.IOException;
import com.bridgelabz.fundoo.model.Note;

public interface ElasticSearchServiceIntrface 
{

	String createNote(Note note) throws IOException;

	String deleteNote(String id) throws Exception;

	Note findById(String id) throws Exception;

	String upDateNote(Note note) throws Exception;

}