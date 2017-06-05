package com.prandium.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="messages_table")
public class Messages {
	
	@Id
	@Column(name="messageId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int messageId;
	
	@Column(name="userName")
    private String userName;
	
	@Column(name="fromUser")
    private String fromUser;
	
	@Column(name="message")
    private String message;
	
	@Column(name="messageDate")
    private String messageDate;

	public Messages() {
		
	}
	
	

	public Messages(String userName, String fromUser, String message) {
		
		this.userName = userName;
		this.fromUser = fromUser;
		this.message = message;
	}



	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	
	

}
