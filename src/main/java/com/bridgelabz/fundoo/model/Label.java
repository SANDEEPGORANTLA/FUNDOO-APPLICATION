package com.bridgelabz.fundoo.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Label {
	@Id
	private String labelId;
	private String userId;
	private String labelName;
	private String createTime;
	private String updateTime;
	@DBRef
	private List<Note> notes;

	public Label() {

	}

	public Label(String labelId, String userId, String labelName, String createTime, String updateTime) {
		super();
		this.labelId = labelId;
		this.userId = userId;
		this.labelName = labelName;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
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

	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", userId=" + userId + ", labelName=" + labelName + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", notes=" + notes + "]";
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

}
