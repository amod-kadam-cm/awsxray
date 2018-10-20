package com.cloudmanthan.k8s.workshop.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.cloudmanthan.k8s.workshop.dao.SQSFifo;

public class QueueLoadGenerator {

	static Logger logger = Logger.getLogger("com.cloudmanthan.k8s.workshop.test.QueueLoadGenerator");

	public static void main(String[] args) {

		AmazonSQS sqsClient = AmazonSQSClientBuilder.defaultClient();

		String queueUrl = System.getenv("FIFIQ_URL");

		logger.log(Level.INFO, "Sending a message to " + queueUrl);

		for (int i = 0; i < 5; i++) {

			String message = "msg " + i;
			logger.log(Level.INFO, "Sending a message to " + message);

			SendMessageRequest sendMessageRequest = new SendMessageRequest(queueUrl, message)
					.withMessageGroupId("regapp");

			sqsClient.sendMessage(sendMessageRequest);

		}

	}

}
