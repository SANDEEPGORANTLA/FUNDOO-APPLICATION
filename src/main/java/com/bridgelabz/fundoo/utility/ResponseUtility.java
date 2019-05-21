package com.bridgelabz.fundoo.utility;

import com.bridgelabz.fundoo.model.Response;

public class ResponseUtility 
{
	public static  Response getResponse(int statusCode, String statusMessage) 
	{
		Response respone = new Response();
		respone.setStatusCode(statusCode);
		respone.setStatusMessage(statusMessage);
		return respone;
	}

	public static Response getResponse(int statusCode, String token, String statusMessage) 
	{
		Response respone = new Response();
		respone.setStatusCode(statusCode);
		respone.setToken(token);
		respone.setStatusMessage(statusMessage);
		return respone;
	}
}