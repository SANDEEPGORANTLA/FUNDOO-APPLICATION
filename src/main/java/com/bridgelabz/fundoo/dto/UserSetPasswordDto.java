package com.bridgelabz.fundoo.dto;

public class UserSetPasswordDto 
{
	private String password;
	private String confirmPassword;

	public String getpassword() 
	{
		return password;
	}

	public void setpassword(String password) 
	{
		this.password = password;
	}

	public String getConfirmPassword() 
	{
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) 
	{
		this.confirmPassword = confirmPassword;
	}

}
