package model;

import java.io.Serializable;

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

    public SendEmailRequest( String requestId, String subject, String message, String recipientAddress, String senderName )
    {
        this.requestId = requestId;
        this.subject = subject;
        this.message = message;
        this.recipientAddress = recipientAddress;
        this.senderName = senderName;
    }

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId( String requestId )
    {
        this.requestId = requestId;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject( String subject )
    {
        this.subject = subject;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public String getRecipientAddress()
    {
        return recipientAddress;
    }

    public void setRecipientAddress( String recipientAddress )
    {
        this.recipientAddress = recipientAddress;
    }

    public String getSenderName()
    {
        return senderName;
    }

    public void setSenderName( String senderName )
    {
        this.senderName = senderName;
    }
}
