package com.bridgelabz.fundoo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.LabelRepositoryInterface;
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
	private LabelRepositoryInterface labelRepositoryInterface;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepositoryInterface userRepositoryInterface;

//********************************* create-note ***************************************************************************//	
	@Override
	public Response create(NoteDto noteDto, String token) {
		String id = TokenUtility.verifyToken(token);
		Optional<User> user = userRepositoryInterface.findById(id);
		if (user.isPresent()) {
			Note note = modelMapper.map(noteDto, Note.class);
			note.setUserId(user.get().getUserId());
			System.out.println(note);
			note.setCreateTime(Utility.todayDate());
			note.setUpdateTime(Utility.todayDate());
			note = noteRepositoryInterface.save(note);

			List<Note> notes = user.get().getNotes();
			System.err.println(notes);
			if (!(notes == null)) 
			{
				notes.add(note);
				user.get().setNotes(notes);
			} 
			else 
			{
				notes = new ArrayList<Note>();
				notes.add(note);
				user.get().setNotes(notes);
			}
			
			userRepositoryInterface.save(user.get());
			Response response = ResponseUtility.getResponse(200, "Note is created Sucessfully");
			return response;
		}
		else
		{
		Response response = ResponseUtility.getResponse(204, "Note is not created");
		return response;
		}
	}

//******************************** update-note ****************************************************************************//
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
		else
		{
		Response response = ResponseUtility.getResponse(205, token, "Note is not updated");
		return response;
		}
	}

//*******************************  delete-note  ***************************************************************************//
	@Override
	public Response delete(String token, String noteId) {
		String id=TokenUtility.verifyToken(token);
		Optional<Note> note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			note.get().setCreateTime(Utility.todayDate());
			note.get().setUpdateTime(Utility.todayDate());
			noteRepositoryInterface.delete(note.get());
			Response response = ResponseUtility.getResponse(205, "Note is Deleated Successfully");
			return response;
		}
		else
		{
		Response response = ResponseUtility.getResponse(205, "Note is not Deleated");
		return response;
		}
	}

//******************************   Retrieve   *****************************************************************************//
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

//*******************************   Archive    ****************************************************************************//	
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
		} 
		else
		{
			Response response = ResponseUtility.getResponse(204, token, "Note does not create Archieve");
			return response;
		}
	}

//******************************    Trash    ******************************************************************************//
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
		else
		{
			Response response = ResponseUtility.getResponse(204, "0", "Note doesnot created in Trash");
			return response;
		}
	}

//******************************    Pin     *******************************************************************************//
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
		else
		{
		Response response = ResponseUtility.getResponse(204, "0", "Note have doesnot Created the Pin");
		return response;
		}
	}
//****************************** adding-labels****************************************************************************************//
	@Override
	public Response addLabelsToNote(String token,String noteId,String labelId)
	{
		String id=TokenUtility.verifyToken(token);
		Optional<User> user=userRepositoryInterface.findByUserId(id);
		Optional<Note> oNote=noteRepositoryInterface.findByNoteId(noteId);
		
		Optional<Label> oLabel=labelRepositoryInterface.findByLabelId(labelId);
		if(user.isPresent() && oNote.isPresent() && oLabel.isPresent())
		{
			Label label=oLabel.get();
			Note note=oNote.get();
			note.setUpdateTime(Utility.todayDate());
			System.out.println(note);
			List<Label> lables = note.getLabels();
			System.err.println(lables);
			if(lables!=null)
			{
				Optional<Label> opLable=lables.stream().filter(l->l.getLabelName().equals(label.getLabelName())).findFirst();
				if(!opLable.isPresent())
				{
					lables.add(label);
					note =noteRepositoryInterface.save(note);
					System.out.println("save lables in note"+note);
					Response response=ResponseUtility.getResponse(205,"Lables are added to the note");
					return response;
				}
			}
			else
			{
				lables = new ArrayList<Label>();
				lables.add(label);
				note.setLabels(lables);
				note = noteRepositoryInterface.save(note);
				Response response=ResponseUtility.getResponse(200,"Lable is added");
				return response;
			}
		}
		Response response=ResponseUtility.getResponse(204,"0","Error is happend while adding lable to note");
		return response;
	}
//************************************************************************************************************************************//	
	@Override
	public Response revomeLabelsFromNote(String token, String noteId, String labelId) 
	{
		String id=TokenUtility.verifyToken(token);
		Optional<User> user=userRepositoryInterface.findByUserId(id);
		System.out.println(user);
		Optional<Note> oNote=noteRepositoryInterface.findByNoteId(noteId);
	    System.out.println(noteId);
	    System.out.println(oNote);
		Optional<Label> oLabel=labelRepositoryInterface.findByLabelId(labelId);
		System.out.println(oLabel);
		if(user.isPresent() && oNote.isPresent() && oLabel.isPresent())
		{
			Label label=oLabel.get();
			Note note=oNote.get();
			System.err.println(label);
			note.setUpdateTime(Utility.todayDate());
			List<Label> labels=note.getLabels();
			if(labels!=null)
			{
				if(labels.stream().filter(g->g.getLabelId().equals(label.getLabelId())).findFirst().isPresent())
				{
					Label findLable=labels.stream().filter(l->l.getLabelId().equals(label.getLabelId())).findFirst().get();
					labels.remove(findLable);
					noteRepositoryInterface.save(note);
					Response response=ResponseUtility.getResponse(206,token,"Lable is removed from the Note");
					return response;
				}
			}
			else
			{
				List<Label> labeld=new ArrayList<Label>();
				labeld.remove(label);
				note.setLabels(labeld);
				note = noteRepositoryInterface.save(note);
				System.out.println("removed lable from note");
				Response response=ResponseUtility.getResponse(206,token,"Label is removed");
				return response;
			}
		}
		Response response=ResponseUtility.getResponse(208,"0","lable is not removed from note due to an error");
		return response;
	}
//**************************************************************************************************************************//
}
