package com.cloudmanthan.aws.workshop.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.cloudmanthan.k8s.workshop.model.RegistrationInfo;

public class RegistrationDynamoDAO {
	
	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
	Logger logger = Logger.getLogger(this.getClass().getName());

	public boolean register(RegistrationInfo regInfo) {
		try {
			
	    logger.log(Level.INFO, "Putting Item in DynamoDB : " + regInfo.toString());
		String tableName = "registration";

		// Create a PutRequest for a new 'registration' item
		Map<String, AttributeValue> registrationItem = new HashMap<String, AttributeValue>();

		registrationItem.put("firstname", new AttributeValue().withS(regInfo.getFirstName()));
		registrationItem.put("lastname", new AttributeValue().withS(regInfo.getLastName()));
		
		PutItemRequest putItemRequest = new PutItemRequest();
		putItemRequest.withItem(registrationItem);
		putItemRequest.withTableName(tableName);
		client.putItem(putItemRequest);
		}catch (Exception e) {
			System.out.println(e);
			logger.log(Level.SEVERE, e.getMessage());
		}

		return true;

	}
}
