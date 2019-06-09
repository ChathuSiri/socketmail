package model;

import model.constant.Status;

import java.io.Serializable;

public class SendEmailAck implements Serializable
{
	private String requestId;
	private Status status;

	public SendEmailAck()
	{
	}

	public SendEmailAck(String requestId, Status status)
	{
		this.requestId = requestId;
		this.status = status;
	}

	public SendEmailAck(String requestId, boolean isSuccess)
	{
		this.requestId = requestId;
		if(isSuccess)
		{
			this.status = Status.OK;
		} else
		{
			this.status = Status.ERROR;
		}
	}

	public String getRequestId()
	{
		return requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}
}
