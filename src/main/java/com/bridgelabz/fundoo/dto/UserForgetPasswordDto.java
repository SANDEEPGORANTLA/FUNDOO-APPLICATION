package com.bridgelabz.fundoo.dto;

public class UserForgetPasswordDto {
	private String emailId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "ForgetPasswordDto [emailId=" + emailId + "]";
	}
}
