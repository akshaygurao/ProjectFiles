package com.prandium.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="review_table")
public class Review {
	
	@Id
	@Column(name="reviewId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reviewId;
	@Column(name="restaurantName")
	private String restaurantName;
	@Column(name="reviewMessage")
	private String reviewMessage;
	@Column(name="reviewDate")
	private String reviewDate;
	@Column(name="username")
	private String username;
	public Review() {
		
	}
	
	
	public Review(String reviewMessage, String reviewDate, String username) {
		
		this.reviewMessage = reviewMessage;
		this.reviewDate = reviewDate;
		this.username = username;
	}


	public String getReviewMessage() {
		return reviewMessage;
	}
	public void setReviewMessage(String reviewMessage) {
		this.reviewMessage = reviewMessage;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getRestaurantName() {
		return restaurantName;
	}


	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}


	@Override
	public String toString() {
		return "Review [reviewMessage=" + reviewMessage + ", reviewDate=" + reviewDate + ", username=" + username + "]";
	}
	
	
	

}
