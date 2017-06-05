package com.prandium.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.prandium.exception.UserException;
import com.prandium.pojo.Email;
import com.prandium.pojo.User;

@Repository
public class UserDAO extends DAO {

	public UserDAO() {
	}

	public User get(String username, String password) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :username and password = :password");
			q.setString("username", username);
			q.setString("password", password);			
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}
	
	public User get(int userId) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where personID= :personID");
			q.setInteger("personID", userId);		
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + userId, e);
		}
	}
	
	public User getUser(String username) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where username= :username");
			q.setString("username", username);		
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}

	public User register(User u)
			throws UserException {
		try {
			begin();
			System.out.println("inside DAO");

			Email email = new Email(u.getEmail().getEmailAddress());
			User user = new User(u.getUsername(), u.getPassword());

			user.setFirstName(u.getFirstName());
			user.setLastName(u.getLastName());
			user.setEmail(email);
			email.setUser(user);
			user.setType(u.getType());
			getSession().save(user);
			commit();
			return user;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}

	public void delete(User user) throws UserException {
		try {
			begin();
			getSession().delete(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not delete user " + user.getUsername(), e);
		}
	}

	public User updateSettings(User user) throws UserException {
		// TODO Auto-generated method stub
		try{
			System.out.println("Inside DAO");
			begin();
			Email email = new Email(user.getEmail().getEmailAddress());
			User u = new User(user.getUsername(), user.getPassword());
			email.setUser(user);
			getSession().update(u);
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while changing user details: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> getAll() throws UserException {
		// TODO Auto-generated method stub
		try{
			begin();
			Query q = getSession().createQuery("from User");
			List<User> userList = q.list();
			commit();
			return userList;
		}catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while fetching all the users: " + e.getMessage());
		}
	}
}