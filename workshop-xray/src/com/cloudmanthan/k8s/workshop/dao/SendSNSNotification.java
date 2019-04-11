package com.cloudmanthan.k8s.workshop.dao;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.cloudmanthan.k8s.workshop.model.RegistrationInfo;

public class SendSNSNotification {

	public static void sendMessage(RegistrationInfo regInfo){
		
		AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
		
		String message = "First Name " + regInfo.getFirstName() + "Last Name : " + regInfo.getLastName() ;
		
		String SNS_TOPIC_ARN = System.getenv("SNS_TOPIC_ARN");
		//String topicArn = "arn:aws:sns:us-east-1:297106433303:AMOD_TOPIC";
 		snsClient.publish(SNS_TOPIC_ARN, message);
 		
 		
		
	}
}
