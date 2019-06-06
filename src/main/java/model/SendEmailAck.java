package model;

import model.constant.Status;

public class SendEmailAck
{
	String requestId;
	Status status;

	public SendEmailAck()
	{
	}

	public SendEmailAck( String requestId, Status status )
	{
		this.requestId = requestId;
		this.status = status;
	}

	public String getRequestId()
	{
		return requestId;
	}

	public void setRequestId( String requestId )
	{
		this.requestId = requestId;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus( Status status )
	{
		this.status = status;
	}
}
