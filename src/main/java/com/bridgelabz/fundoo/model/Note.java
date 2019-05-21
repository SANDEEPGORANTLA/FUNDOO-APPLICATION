package com.bridgelabz.fundoo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Note {
	@Id
	private String noteId;
	private String userId;
	private String title;
	private String description;
	private String createTime;
	private String updateTime;
	private boolean trash;
	private boolean archive;
	private boolean Pin;

	public Note()
	{
		
	}
	public Note(String noteId, String userId, String title, String description, String createTime, String updateTime,
			boolean trash, boolean archive, boolean Pin) {
		super();
		this.noteId = noteId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.trash = trash;
		this.archive = archive;
		this.Pin = Pin;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean Trash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public boolean Archive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public boolean Pin() {
		return Pin;
	}

	public void setPin(boolean Pin) {
		this.Pin = Pin;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", userId=" + userId + ", title=" + title + ", description=" + description
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", trash=" + trash + ", archive="
				+ archive + ", Pin=" + Pin + "]";
	}
	
}
