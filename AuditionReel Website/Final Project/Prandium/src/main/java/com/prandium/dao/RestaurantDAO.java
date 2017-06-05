package com.prandium.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.prandium.exception.RestaurantException;
import com.prandium.pojo.Restaurant;
import com.prandium.pojo.Review;

public class RestaurantDAO extends DAO{

	public RestaurantDAO(){
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Restaurant> get(String restaurantName) throws RestaurantException {
		try {
			begin();
			Query q = getSession().createQuery("from Restaurant where restaurantName like :restaurantName");
			q.setString("restaurantName", restaurantName+"%");	
			List<Restaurant> restaurantList = q.list();
			commit();
			return restaurantList;
		} catch (HibernateException e) {
			rollback();
			throw new RestaurantException("Could not get restaurant " + restaurantName, e);
		}
	}

	public Restaurant register(Restaurant restaurant) throws RestaurantException {
		try {
			begin();
			System.out.println("inside DAO");

			Restaurant r = new Restaurant(restaurant.getRestaurantName(),restaurant.getRestaurantLocation(),restaurant.getRestaurantPhone());
			
			r.setRestaurantName(restaurant.getRestaurantName());
			r.setRestaurantLocation(restaurant.getRestaurantLocation());
			r.setRestaurantPhone(restaurant.getRestaurantPhone());
			r.setRegistrar(restaurant.getRegistrar());
			r.setFilename(restaurant.getFilename());
			getSession().save(r);
			commit();
			return r;

		} catch (HibernateException e) {
			rollback();
			throw new RestaurantException("Exception while creating restaurant: " + e.getMessage());
		}
	}
	
	public Restaurant getRestaurant(String restaurantName) throws RestaurantException {
		try {
			begin();
			Query q = getSession().createQuery("from Restaurant where restaurantName = :restaurantName");
			q.setString("restaurantName", restaurantName);
			Restaurant r = (Restaurant)q.uniqueResult();
			commit();
			return r;
		} catch (HibernateException e) {
			rollback();
			throw new RestaurantException("Could not get restaurant" +restaurantName, e);
		}
	}
	
	public Review addReview(Review review) throws RestaurantException {
		try {
			begin();
			System.out.println("inside review DAO");
			Review r = new Review(review.getReviewDate(),review.getReviewMessage(),review.getUsername());
			r.setUsername(review.getUsername());
			r.setReviewMessage(review.getReviewMessage());
			r.setReviewDate(review.getReviewDate());
			r.setRestaurantName(review.getRestaurantName());
			getSession().save(r);
			commit();
			return r;

		} catch (HibernateException e) {
			rollback();
			throw new RestaurantException("Exception while adding review: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Restaurant> getAllRestaurants() throws RestaurantException {
		try {
			begin();
			Query q = getSession().createQuery("from Restaurant");	
			List<Restaurant> restaurantList = q.list();
			commit();
			return restaurantList;
		} catch (HibernateException e) {
			rollback();
			throw new RestaurantException("Could not get all the restaurants ", e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Review> getReviews(String restaurantName) throws RestaurantException{
		try {
			begin();
			Query q = getSession().createQuery("from Review where restaurantName=:restaurantName");
			q.setString("restaurantName", restaurantName);
			List<Review> listOfReviews = q.list();
			commit();
			return listOfReviews;
		} catch (HibernateException e) {
			rollback();
			throw new RestaurantException("Could not get all the restaurants ", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Restaurant> getRegistered(String username) throws RestaurantException {
		try {
			begin();
			Query q = getSession().createQuery("from Restaurant where registrar= :username");
			q.setString("username", username);	
			List<Restaurant> restaurantList = q.list();
			commit();
			return restaurantList;
		} catch (HibernateException e) {
			rollback();
			throw new RestaurantException("Could not get restaurant " + username, e);
		}
	}
	
	public void deleteRestaurant(Restaurant restaurant) throws RestaurantException {
		try{
			begin();
			getSession().delete(restaurant);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new RestaurantException("Could not delete restaurant " + restaurant.getRestaurantName(), e);
		}
	}
}
