package model;

import java.io.Serializable;
import java.util.Objects;

public class SendEmailRequest implements Serializable
{
	private String requestId;
	private String subject;
	private String message;
	private String recipientAddress;
	private String senderName;

	public SendEmailRequest()
	{
	}

	public String getRequestId()
	{
		return requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getRecipientAddress()
	{
		return recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress)
	{
		this.recipientAddress = recipientAddress;
	}

	public String getSenderName()
	{
		return senderName;
	}

	public void setSenderName(String senderName)
	{
		this.senderName = senderName;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof SendEmailRequest)) return false;
		SendEmailRequest that = (SendEmailRequest) o;
		return Objects.equals(requestId, that.requestId);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(requestId);
	}
}
