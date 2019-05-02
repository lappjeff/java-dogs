package com.lambdaschool.projectrestdogs.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageDetail implements Serializable
{
	private String message;
	private Dog dog;
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	public MessageDetail()
	{

	}

	public MessageDetail(String message)
	{
		this.message = message;
	}

	public MessageDetail(Dog dog)
	{
		this.dog = dog;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}


	@Override
	public String toString()
	{
		return "MessageDetail{" + "message='" + message + '\'' + ", timestamp=" + timestamp + '}';
	}
}
