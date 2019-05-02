package com.lambdaschool.projectrestdogs.Services;

import com.lambdaschool.projectrestdogs.ProjectrestdogsApplication;
import com.lambdaschool.projectrestdogs.models.Dog;
import com.lambdaschool.projectrestdogs.models.MessageDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

//@Service
public class MessageSender
{
	private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
	private RabbitTemplate rt;

	public MessageSender(RabbitTemplate rt)
	{
		this.rt = rt;
	}

	public void sendMessage(String msg)
	{

		MessageDetail message = new MessageDetail(msg);
		logger.info("Message sent: " + msg);
		rt.convertAndSend(ProjectrestdogsApplication.DOGS_QUEUE, message);

	}

	public void sendDogMessage(Dog dog)
	{
		MessageDetail dogMessage = new MessageDetail(dog);
		logger.info("Dog created: " + dog);
		rt.convertAndSend(ProjectrestdogsApplication.NEW_DOGS_QUEUE, dogMessage);
	}


}
