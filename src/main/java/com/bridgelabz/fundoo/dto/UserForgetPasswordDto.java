package com.bridgelabz.fundoo.dto;

public class UserForgetPasswordDto 
{
	private String emailId;
	private String Password;

	public String getEmailId() 
	{
		return emailId;
	}
	public void setEmailId(String emailId) 
	{
		this.emailId = emailId;
	}
	public String getPassword() 
	{
		return Password;
	}
	public void setPassword(String password) 
	{
		Password = password;
	}
	@Override
	public String toString() 
	{
		return "ForgetPasswordDto [emailId=" + emailId + ", Password=" + Password + "]";
	}

}
