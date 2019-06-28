package com.bridgelabz.fundoo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.Note;

@Repository
public interface NoteRepositoryInterface extends MongoRepository<Note, String> 
{
	Optional<Note> findByNoteIdAndUserId(String noteId, String userId);
	Optional<Note> findByNoteId(String noteId);
	List<Note> findByUserId(String userId);
	
	
}