package com.bridgelabz.fundoo.controller;

import java.io.IOException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoo.utility.Response;
import com.bridgelabz.fundoo.services.AmazonClient;
//import com.bridgelabz.fundoo.utility.ResponseUtility;

@RestController
@RequestMapping("/storage")
public class BucketController 
{	
	private AmazonClient amazonClient;

	@Autowired
	public BucketController(AmazonClient amazonClient)
	{
	this.amazonClient=amazonClient;
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<Response>uploadFile(@RequestPart(value="file")MultipartFile file,@RequestHeader String token)throws IOException
	{
	Response response=amazonClient.uploadFile(file,token);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@DeleteMapping("/deleteFile")
	public ResponseEntity<Response> deleteFile(@RequestHeader String fileName,@RequestHeader String token) throws IOException
	{
	Response response=amazonClient.deleteFileFromS3Bucket(fileName,token);
	return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	@GetMapping("/get")
	public URL getPropic(@RequestHeader String token) 
	{
		URL response=amazonClient.getPropic(token);
		return response;
		
	}
}