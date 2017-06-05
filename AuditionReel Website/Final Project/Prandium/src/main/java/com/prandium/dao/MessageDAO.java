package com.prandium.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.prandium.exception.MessageException;
import com.prandium.pojo.Messages;

public class MessageDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<Messages> getMessagesForUser(String username) throws MessageException {
		try {
			begin();
			Query q = getSession().createQuery("FROM Messages WHERE username=:username");
			q.setString("username", username);
			List<Messages> messagesList = q.list();
			commit();
			return messagesList;
		} catch (HibernateException e) {
			rollback();
			throw new MessageException("Could not get messages for:" + username, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Messages> getMessagesFromUser(String username) throws MessageException {
		try {
			begin();
			Query q = getSession().createQuery("FROM Messages WHERE fromUser=:fromUser");
			q.setString("fromUser", username);
			List<Messages> messagesList = q.list();
			commit();
			return messagesList;
		} catch (HibernateException e) {
			rollback();
			throw new MessageException("Could not get messages for:" + username, e);
		}
	}

	public Messages insertMessage(Messages messages) throws MessageException {
		
		try {
			begin();
			System.out.println("inside DAO");
			Date d = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			String messageDate = format.format(d);
			Messages m = new Messages(messages.getUserName(), messages.getFromUser(), messages.getMessage());
			
			m.setFromUser(messages.getFromUser());
			m.setMessage(messages.getMessage());
			m.setMessageDate(messageDate);
			m.setUserName(messages.getUserName());
			
			getSession().save(m);
			commit();
			return m;

		} catch (HibernateException e) {
			rollback();
			throw new MessageException("Exception while creating message: " + e.getMessage());
		}
	}

}
