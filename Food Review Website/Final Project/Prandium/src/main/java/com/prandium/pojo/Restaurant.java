package com.prandium.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Table(name = "restaurant_table")
public class Restaurant {

	@Id
	@Column(name = "restaurantID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long restaurantID;

	@Column(name = "restaurantName")
	private String restaurantName;
	
	@Column(name="registrar")
	private String registrar;

	@Column(name = "restaurantLocation")
	private String restaurantLocation;

	@Column(name = "restaurantPhone")
	private String restaurantPhone;
	
	@Transient
	private CommonsMultipartFile photo;
	
	@Column(name = "filename")
	private String filename;  
	

	public Restaurant() {

	}

	public Restaurant(String restaurantName, String restaurantLocation, String restaurantPhone) {

		this.restaurantName = restaurantName;
		this.restaurantLocation = restaurantLocation;
		this.restaurantPhone = restaurantPhone;
	}

	public long getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(long restaurantID) {
		this.restaurantID = restaurantID;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantLocation() {
		return restaurantLocation;
	}

	public void setRestaurantLocation(String restaurantLocation) {
		this.restaurantLocation = restaurantLocation;
	}

	public String getRestaurantPhone() {
		return restaurantPhone;
	}

	public void setRestaurantPhone(String restaurantPhone) {
		this.restaurantPhone = restaurantPhone;
	}

	public String getRegistrar() {
		return registrar;
	}

	public void setRegistrar(String registrar) {
		this.registrar = registrar;
	}

	public CommonsMultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(CommonsMultipartFile photo) {
		this.photo = photo;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "Restaurant [restaurantID=" + restaurantID + ", restaurantName=" + restaurantName + ", registrar="
				+ registrar + ", restaurantLocation=" + restaurantLocation + ", restaurantPhone=" + restaurantPhone
				+ ", photo=" + photo + ", filename=" + filename + "]";
	}
	
	

}
