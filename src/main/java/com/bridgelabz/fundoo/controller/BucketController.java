package com.bridgelabz.fundoo.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.services.AmazonClient;

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
}