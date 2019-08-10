package com.cloudmanthan.aws.workshop.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;



public class RegistrationQueueHandlerFunction implements RequestHandler<SQSEvent, Void> {

	@Override
	public Void handleRequest(SQSEvent event, Context context) {
		
		context.getLogger().log("Input: " + event);
/*
		
		for (SQSMessage msg : event.getRecords()) {
			System.out.println(new String(msg.getBody()));
		}
*/		return null;

	}

}
