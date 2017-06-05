package com.prandium.exception;

@SuppressWarnings("serial")
public class MessageException extends Exception{
	
	public MessageException(String message)
	{
		super("MessageException-"+message);
	}
	
	public MessageException(String message, Throwable cause)
	{
		super("MessageException-"+message,cause);
	}

}
