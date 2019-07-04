package com.bridgelabz.fundoo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
			note.setCreateTime(Utility.todayDate());
			note.setUpdateTime(Utility.todayDate());
			note = noteRepositoryInterface.save(note);
			List<Note> notes = user.get().getNotes();
			if (!(notes == null)) {
				notes.add(note);
				user.get().setNotes(notes);
			} else {
				notes = new ArrayList<Note>();
				notes.add(note);
				user.get().setNotes(notes);
			}
			userRepositoryInterface.save(user.get());
			Response response = ResponseUtility.getResponse(200, "Note is created Sucessfully");
			return response;
		} else {
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
			Response response = ResponseUtility.getResponse(200, token, "Note is updated Successfully");
			return response;
		} else {
			Response response = ResponseUtility.getResponse(200, token, "Note is not updated");
			return response;
		}
	}

//*******************************  delete-note  ***************************************************************************//
	@Override
	public Response delete(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);

		if (note.isPresent()) {
			if (note.get().Trash() == true) {
				noteRepositoryInterface.delete(note.get());
			}
			Response response = ResponseUtility.getResponse(200, "Note is Deleated Successfully");
			return response;
		} else {
			Response response = ResponseUtility.getResponse(204, "Note is not Deleated");
			return response;
		}
	}

//******************************   Retrieve   *****************************************************************************//
	@Override
	public List<Note> retrieve(String token) {
		String id = TokenUtility.verifyToken(token);
		List<Note> note = (List<Note>) noteRepositoryInterface.findByUserId(id);
		List<Note> listNote = new ArrayList<>();
		for (Note userNote : note) {
			Note noteDto = modelMapper.map(userNote, Note.class);
			if (noteDto.Trash() == false && noteDto.Archive() == false) {
				listNote.add(noteDto);
			}
		}
		return listNote;
	}

//*******************************   Archive    ****************************************************************************//	
	@Override
	public Response Archive(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			if ((note.get().Archive()) == false) {
				note.get().setArchive(true);
			} else {
				note.get().setArchive(false);
			}
			note.get().setUpdateTime(Utility.todayDate());
			noteRepositoryInterface.save(note.get());
			Response response = ResponseUtility.getResponse(200, token, "Note Archived");
			return response;
		} else {
			Response response = ResponseUtility.getResponse(204, token, "Note UnArchived");
			return response;
		}
	}

//******************************    Trash    ******************************************************************************//
	@Override
	public Response Trash(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			if (note.get().Trash() == false) {
				note.get().setTrash(true);
				LocalDateTime dateTime = LocalDateTime.now();
				note.get().setUpdateTime(String.valueOf(dateTime));

				noteRepositoryInterface.save(note.get());
				Response response = ResponseUtility.getResponse(200, token, "Note is Trashed ");
				return response;
			} else {
				note.get().setTrash(false);
				LocalDateTime dateTime = LocalDateTime.now();
				note.get().setUpdateTime(String.valueOf(dateTime));

				noteRepositoryInterface.save(note.get());
				Response response = ResponseUtility.getResponse(200, token, "Note is restored ");
				return response;
			}

		}
		Response response = ResponseUtility.getResponse(204, token, "Note is not Trashed ");
		return response;
	}

//******************************    Pin     *******************************************************************************//
	@Override
	public Response Pin(String token, String noteId) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);
		if (note.isPresent()) {
			if (note.get().Pin() == false) {
				note.get().setPin(true);
			} else {
				note.get().setPin(false);
			}
			note.get().setUpdateTime(Utility.todayDate());
			noteRepositoryInterface.save(note.get());
			Response response = ResponseUtility.getResponse(200, token, "Note is pinned");
			return response;
		} else {
			Response response = ResponseUtility.getResponse(204, token, "Note is Unpinned");
			return response;
		}
	}

//****************************** adding-labels****************************************************************************************//
	@Override
	public Response addLabelsToNote(String token, String noteId, String labelId) {
		String id = TokenUtility.verifyToken(token);
		Optional<User> user = userRepositoryInterface.findByUserId(id);
		Optional<Note> oNote = noteRepositoryInterface.findByNoteId(noteId);

		Optional<Label> oLabel = labelRepositoryInterface.findByLabelId(labelId);
		if (user.isPresent() && oNote.isPresent() && oLabel.isPresent()) {
			Label label = oLabel.get();
			Note note = oNote.get();
			note.setUpdateTime(Utility.todayDate());
			List<Label> lables = note.getLabels();
			if (lables != null) {
				Optional<Label> opLable = lables.stream().filter(l -> l.getLabelName().equals(label.getLabelName()))
						.findFirst();
				if (!opLable.isPresent()) {
					lables.add(label);
					note = noteRepositoryInterface.save(note);
					Response response = ResponseUtility.getResponse(201, "Lables are added to the note");
					return response;
				}
			} else {
				lables = new ArrayList<Label>();
				lables.add(label);
				note.setLabels(lables);
				note = noteRepositoryInterface.save(note);
				Response response = ResponseUtility.getResponse(200, "Lable is added");
				return response;
			}
		}
		Response response = ResponseUtility.getResponse(204, "0", "Error is happend while adding lable to note");
		return response;
	}

//************************************************************************************************************************************//	
	@Override
	public Response revomeLabelsFromNote(String token, String noteId, String labelId) {
		String id = TokenUtility.verifyToken(token);
		Optional<User> user = userRepositoryInterface.findByUserId(id);
		Optional<Note> oNote = noteRepositoryInterface.findByNoteId(noteId);
		Optional<Label> oLabel = labelRepositoryInterface.findByLabelId(labelId);

		if (user.isPresent() && oNote.isPresent() && oLabel.isPresent()) {
			Label label = oLabel.get();
			Note note = oNote.get();
			note.setUpdateTime(Utility.todayDate());
			List<Label> labels = note.getLabels();
			if (labels != null) {
				if (labels.stream().filter(g -> g.getLabelId().equals(label.getLabelId())).findFirst().isPresent()) {
					Label findLable = labels.stream().filter(l -> l.getLabelId().equals(label.getLabelId())).findFirst()
							.get();
					labels.remove(findLable);
					noteRepositoryInterface.save(note);
					Response response = ResponseUtility.getResponse(201, token, "Lable is removed from the Note");
					return response;
				}
			} else {
				List<Label> labeId = new ArrayList<Label>();
				labeId.remove(label);
				note.setLabels(labeId);
				note = noteRepositoryInterface.save(note);
				Response response = ResponseUtility.getResponse(200, token, "Label is removed");
				return response;
			}
		}
		Response response = ResponseUtility.getResponse(204, "0", "lable is not removed from note due to an error");
		return response;
	}

//*********************************** bin notes ************************************************************************************//	
	@Override
	public List<Note> bin(String token) {
		String id = TokenUtility.verifyToken(token);
		List<Note> note = (List<Note>) noteRepositoryInterface.findByUserId(id);
		List<Note> binNote = note.stream().filter(data -> (data.Trash() == true)).collect(Collectors.toList());
		return binNote;
	}

//********************************** archive notes ********************************************************************************//
	@Override
	public List<Note> archiveNote(String token) {
		String id = TokenUtility.verifyToken(token);

		List<Note> list = (List<Note>) noteRepositoryInterface.findByUserId(id);
		List<Note> archiveNotes = list.stream().filter(data -> (data.Archive() == true)).collect(Collectors.toList());
		return archiveNotes;

	}

//********************************* pinned notes **********************************************************************************//
	@Override
	public List<Note> pinnedNote(String token) {
		String id = TokenUtility.verifyToken(token);
		List<Note> pin = (List<Note>) noteRepositoryInterface.findByUserId(id);
		List<Note> pinNote = pin.stream().filter(data -> (data.Pin() == true)).collect(Collectors.toList());
		return pinNote;
	}

//******************************** color *****************************************************************************************//
	@Override
	public Response addColour(String noteId, String token, String color) {
		String id = TokenUtility.verifyToken(token);
		Optional<Note> optNote = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id);
		if (optNote.isPresent()) {
			Note note = optNote.get();
			note.setColor(color);
			note.setUpdateTime(Utility.todayDate());
			noteRepositoryInterface.save(note);
			Response response = ResponseUtility.getResponse(200, token, "color is Set");
			return response;
		} else {
			Response response = ResponseUtility.getResponse(204, token, "color is removed");
			return response;
		}
	}

//******************************************************************************************************************//
	public List<Label> getLabelsFromNote(String noteId, String token)

	{
		String id = TokenUtility.verifyToken(token);
		Note note = noteRepositoryInterface.findByNoteIdAndUserId(noteId, id).get();
		if (note.getLabels() != null) 
		{
			List<Label> labels = note.getLabels().stream().collect(Collectors.toList());
			return labels;
		} 
		else 
		{
			return null;
		}
	}

}
