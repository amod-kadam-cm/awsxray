package com.cloudmanthan.k8s.workshop.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.cloudmanthan.k8s.workshop.model.RegistrationInfo;

public class SQSFifo {
	
	

	public  void sendMessage(RegistrationInfo regInfo){
		
		 Logger logger = Logger.getLogger(this.getClass().getName());

		
		AmazonSQS sqsClient = AmazonSQSClientBuilder.defaultClient();
		
		String message = "First Name " + regInfo.getFirstName() + "Last Name : " + regInfo.getLastName() ;
		String myQueueUrl  = "https://sqs.us-east-1.amazonaws.com/297106433303/demo-regapp-q.fifo";
		
		//String mySystem.getenv("FIFIQ_URL");
		
		logger.log(Level.INFO,"Sending a message to MyFifoQueue.fifo.\n");
        final SendMessageRequest sendMessageRequest =
                new SendMessageRequest(myQueueUrl,
                		message).withMessageGroupId("regapp");
 		
        
        sqsClient.sendMessage(sendMessageRequest);
		
	}
}
