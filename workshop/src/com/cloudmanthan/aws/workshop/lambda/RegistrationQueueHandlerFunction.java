package com.cloudmanthan.aws.workshop.lambda;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;



public class RegistrationQueueHandlerFunction implements RequestHandler<SQSEvent, Void> {

	@Override
	public Void handleRequest(SQSEvent event, Context context) {
		
		context.getLogger().log("Input: " + event);
		
		List<SQSMessage> sqsMessages = event.getRecords();

		
		for (SQSMessage msg : event.getRecords()) {
			System.out.println(new String(msg.getBody()));
			context.getLogger().log("Message is " + msg.getBody());
		}
		return null;

	}

}
