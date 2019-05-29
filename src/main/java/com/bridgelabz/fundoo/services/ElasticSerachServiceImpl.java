package com.bridgelabz.fundoo.services;

import java.io.IOException;
import java.util.Map;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSerachServiceImpl implements ElasticSearchServiceIntrface
{
	String index = "es";
	String type = "createnote";
	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private ObjectMapper objectMapper;
//*********************** create **************************************************************//
	@SuppressWarnings("unchecked")
	@Override
	public String createNote(Note note) throws IOException
	{
		Map<String, Object> documentMapper = objectMapper.convertValue(note, Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type, note.getNoteId()).source(documentMapper);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
	}
//********************** delete ***************************************************************//
	@Override
	public String deleteNote(String id) throws Exception
	{
		DeleteRequest deleteRequest = new  DeleteRequest(index, type, id);
		DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
		return response.getResult().name();
	}
//********************** find-by-id ***************************************************************//
	@Override
	public Note findById(String id) throws Exception
	{
		GetRequest getRequest = new GetRequest(index, type, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();
		return objectMapper.convertValue(resultMap, Note.class);
	}
//********************** update ***************************************************************//
	@SuppressWarnings("unchecked")
	@Override
	public String upDateNote(Note note) throws Exception 
	{
		Note notes=findById(note.getNoteId());
		UpdateRequest updateRequest=new UpdateRequest(index, type, note.getNoteId());
		Map<String ,Object> mapDoc = objectMapper.convertValue(note, Map.class);
		updateRequest.doc(mapDoc);
		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		return updateResponse.getResult().name();
	}
}
