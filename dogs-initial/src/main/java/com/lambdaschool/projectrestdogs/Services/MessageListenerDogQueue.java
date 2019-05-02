package com.lambdaschool.projectrestdogs.Services;

import com.lambdaschool.projectrestdogs.ProjectrestdogsApplication;
import com.lambdaschool.projectrestdogs.models.MessageDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListenerDogQueue
{
	public static final Logger logger = LoggerFactory.getLogger(MessageListenerDogQueue.class);

	@RabbitListener(queues = ProjectrestdogsApplication.DOGS_QUEUE)
	public void receiveDogsQueueMessage(MessageDetail message)
	{
//		logger.info(message.);
		logger.info("Received from DogsQueue \n {}", message.toString());
	}
}
