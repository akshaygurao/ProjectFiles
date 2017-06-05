package com.prandium.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.prandium.dao.RestaurantDAO;
import com.prandium.exception.RestaurantException;
import com.prandium.filter.XSSFilter;
import com.prandium.pojo.Restaurant;
import com.prandium.pojo.Review;

@Controller
@RequestMapping("/review/*")
public class ReviewController {

	@Autowired
	@Qualifier("restaurantDao")
	RestaurantDAO restaurantDao;

	
	@RequestMapping(value = "/review/review", method = RequestMethod.GET)
	protected ModelAndView getReviews(HttpServletRequest request) throws Exception {
		System.out.print("Review Restaurant");
		String restaurantName = request.getParameter("restaurantName");
		List<Review> listOfReviews = restaurantDao.getReviews(restaurantName);
		return new ModelAndView("restaurant-profile", "listOfReviews", listOfReviews);
	}

	@RequestMapping(value = "/review/review", method = RequestMethod.POST)
	protected ModelAndView setReviews(HttpServletRequest request) throws Exception {
		System.out.print("Review Restaurant");


		try {
			System.out.println("Inside the review post method");
			String reviewMessage = request.getParameter("reviewMessage");
			String finalReviewMessage = XSSFilter.removeXSS(reviewMessage);
			String restaurantName = (String)request.getSession().getAttribute("restaurantName");
			String username = request.getParameter("username");
			System.out.println(username +"  " + restaurantName);
			Date d = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			String reviewDate = format.format(d);
			Review review = new Review(finalReviewMessage, reviewDate, username);
			review.setRestaurantName(restaurantName);
			Review r = restaurantDao.addReview(review);
			r.setReviewDate(reviewDate);
			List<Review> listOfReviews = restaurantDao.getReviews(restaurantName);
			Restaurant restaurant = restaurantDao.getRestaurant(restaurantName);
			request.getSession().setAttribute("restaurant", restaurant);
			return new ModelAndView("restaurant-profile", ("listOfReviews"), listOfReviews);
		} catch (RestaurantException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error adding review");
		}
	}
}
