package com.bridgelabz.fundoo.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.fundoo.model.Response;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepositoryInterface;
import com.bridgelabz.fundoo.utility.ResponseUtility;
import com.bridgelabz.fundoo.utility.TokenUtility;

@Service
public class AmazonClient 
{

	@Autowired
	UserRepositoryInterface userRepositoryInterface;

	private AmazonS3 s3client;

	@Value("${amazonProperties.endPointUrl}")
	private String endPointUrl;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;

	@Value("${amazonProperties.accessKey}")
	private String accessKey;

	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@SuppressWarnings("deprecation")
	@PostConstruct
	private void initializeAmazon() 
	{
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	private File convertMultiPartToFIle(MultipartFile file) throws IOException 
	{
		File convertFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertFile);
		fos.write(file.getBytes());
		fos.close();
		return convertFile;
	}

	private String generateFileName(MultipartFile multipart) 
	{
		return new Date().getTime() + "-" + multipart.getOriginalFilename().replace("", "-");
	}

	private void uploadFileToS3Bucket(String fileName, File file) 
	{
		s3client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public Response uploadFile(MultipartFile multipart, String token) throws IOException 
	{
		String id = TokenUtility.verifyToken(token);
		Optional<User> optiUser = userRepositoryInterface.findByUserId(id);
		if (optiUser.isPresent()) 
		{
			User user = optiUser.get();
			String fileUrl = "";
			File file = convertMultiPartToFIle(multipart);
			String fileName = generateFileName(multipart);
			fileUrl = endPointUrl + "/" + bucketName + "/" + fileName;
			uploadFileToS3Bucket(fileName, file);
			file.delete();
			user.setImage(fileUrl);
			userRepositoryInterface.save(user);
			Response response = ResponseUtility.getResponse(201, token, "profile pic is successfully updated");
			return response;
		} 
		else 
		{
			Response response = ResponseUtility.getResponse(404, "0", "profile pic is updated Fail");
			return response;
		}
	}
	public Response deleteFileFromS3Bucket(String fileName,String token) throws IOException
	{
		String id=TokenUtility.verifyToken(token);
		Optional<User> optiUser=userRepositoryInterface.findByUserId(id);
		if(optiUser.isPresent())
		{
			User user=optiUser.get();
			s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
			user.setImage(null);
			userRepositoryInterface.save(user);
			Response response=ResponseUtility.getResponse(201,token,"profile pic is deleated successfully");
			return response;
		}
		else
		{
			Response response=ResponseUtility.getResponse(404,"0","profile pic is not deleated successfully");
			return response;
		}
	}
	public URL getPropic(String token) {
	String id=TokenUtility.verifyToken(token);
	boolean isUser=userRepositoryInterface.findById(id).isPresent();
	if(!isUser) {
	// response.sendResponse(204,"profilePic is retrived successfully","");
	// return response;
	System.out.println("propic retrival failed");
	}
	// User user=new User();
	User user=userRepositoryInterface.findById(id).get();
	String image=user.getImage();
	URL url=null;
	try {
	url=new URL(image);
	} catch (MalformedURLException e) {
	e.printStackTrace();
	}
	return url;
	}

}


