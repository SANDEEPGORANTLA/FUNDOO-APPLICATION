package com.bridgelabz.fundoo.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User 
{
	@Id
	private String userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String phoneNumber;
	private String password;
	private String registerStamp;
	private String UpdateStamp;
	private boolean isVarified;
	private String token;
	
	@DBRef
	private List<Note> notes;
	@DBRef
	private List<Label> labels;
	
	
	public User() 
	{

	}

	public String getUserId() 
	{
		return userId;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	public String getFirstName() 
	{
		return firstName;
	}
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	public String getLastName() 
	{
		return lastName;
	}
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	public String getEmailId() 
	{
		return emailId;
	}
	public void setEmailId(String emailId) 
	{
		this.emailId = emailId;
	}
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	public String getRegisterStamp() 
	{
		return registerStamp;
	}
	public void setRegisterStamp(String registerStamp) 
	{
		this.registerStamp = registerStamp;
	}
	public String getUpdateStamp() 
	{
		return UpdateStamp;
	}
	public void setUpdateStamp(String updateStamp) 
	{
		UpdateStamp = updateStamp;
	}
	public boolean isVerified() 
	{
		return isVarified;
	}
	public void setVerified(boolean isVerified) 
	{
		this.isVarified = isVerified;
	}
	public String getToken() 
	{
		return token;
	}
	public void setToken(String token) 
	{
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", phoneNumber=" + phoneNumber + ", password=" + password + ", registerStamp=" + registerStamp
				+ ", UpdateStamp=" + UpdateStamp + ", isVarified=" + isVarified + ", token=" + token + ", notes="
				+ notes + ", labels=" + labels + "]";
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public User get() {
		
		return null;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}
	
}
