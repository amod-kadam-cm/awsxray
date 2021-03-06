package com.cloudmanthan.k8s.workshop.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.cloudmanthan.k8s.workshop.model.RegistrationInfo;

public class SQSFifo {

	AmazonSQS sqsClient = AmazonSQSClientBuilder.defaultClient();

	Logger logger = Logger.getLogger(this.getClass().getName());
	String queueUrl  = System.getenv("FIFO_Q_URL");
	
	
	public void sendMessage(RegistrationInfo regInfo) {

		String message = "First Name " + regInfo.getFirstName() + "Last Name : " + regInfo.getLastName();
		
		
		logger.log(Level.INFO, "Sending a message to ");
		final SendMessageRequest sendMessageRequest = new SendMessageRequest(queueUrl, message)
				.withMessageGroupId("regapp").withMessageDeduplicationId(UUID.randomUUID().toString());

		sqsClient.sendMessage(sendMessageRequest);

	}

	public void recieveMessage() {
		
		ReceiveMessageRequest receiveMessageRequest =
                new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(10);

	
		GetQueueAttributesRequest attReq = new GetQueueAttributesRequest()
				.withQueueUrl(queueUrl)
				.withAttributeNames("ApproximateNumberOfMessages");
		
		
		GetQueueAttributesResult attResult =  sqsClient.getQueueAttributes(attReq);
		
		Map<String,String> attMap = attResult.getAttributes();
		
		String approxNoMessages = attMap.get("ApproximateNumberOfMessages");
		
		logger.info("Number of messagess are "+ approxNoMessages );
		
		
		List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
		
		
		for (Message message: messages) {
			
			 logger.log(Level.INFO,"Message"); 
			 
             String body =  "Body: " +  message.getBody();
             
             logger.info(body);
			
		}		
	}
}
