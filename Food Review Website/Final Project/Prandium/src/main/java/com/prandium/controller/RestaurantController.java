package com.prandium.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.prandium.dao.RestaurantDAO;
import com.prandium.exception.RestaurantException;
import com.prandium.pojo.Restaurant;
import com.prandium.pojo.Review;
import com.prandium.pojo.User;
import com.prandium.validator.RestaurantValidator;
import com.prandium.filter.*;

@Controller
@RequestMapping("/rest/*")
public class RestaurantController {

	@Autowired
	@Qualifier("restaurantDao")
	RestaurantDAO restaurantDao;

	@Autowired
	@Qualifier("restaurantValidator")
	RestaurantValidator restaurantValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(restaurantValidator);
	}

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "/rest/registerRestaurant", method = RequestMethod.GET)
	protected ModelAndView registerRestaurant() throws Exception {
		System.out.print("registerRestaurant(GET)");

		return new ModelAndView("register-restaurant", "restaurant", new Restaurant());

	}

	@RequestMapping(value = "/rest/registerRestaurant", method = RequestMethod.POST)
	protected ModelAndView registerNewRestaurant(HttpServletRequest request,
			@ModelAttribute("restaurant") Restaurant restaurant, BindingResult result) throws Exception {

		restaurantValidator.validate(restaurant, result);

		if (result.hasErrors()) {
			return new ModelAndView("register-restaurant", "restaurant", restaurant);
		}
		try {
			String restaurantName = restaurant.getRestaurantName();
			String finalRestaurantName = XSSFilter.removeXSS(restaurantName);
			String restaurantLocation = restaurant.getRestaurantLocation();
			String finalRestaurantLocation = XSSFilter.removeXSS(restaurantLocation);
			String restaurantPhone = restaurant.getRestaurantPhone();
			String finalRestaurantPhone = XSSFilter.removeXSS(restaurantPhone);
			restaurant.setRestaurantName(finalRestaurantName);
			restaurant.setRestaurantLocation(finalRestaurantLocation);
			restaurant.setRestaurantPhone(finalRestaurantPhone);
			System.out.print("registerNewRestaurant(POST)");
			String registrar = (String) request.getSession().getAttribute("username");
			restaurant.setRegistrar(registrar);

			if (restaurant.getFilename().trim() != "" || restaurant.getFilename() != null) {
				File directory;
				String check = File.separator;

				String path = null;
				if (check.equalsIgnoreCase("\\")) {
					path = servletContext.getRealPath("").replace("build\\", "");
				}

				if (check.equalsIgnoreCase("/")) {
					path = servletContext.getRealPath("").replace("build/", "");
					path += "/"; // Adding trailing slash for Mac systems.
				}
				directory = new File(path + "\\" + restaurant.getFilename());
				boolean temp = directory.exists();
				if (!temp) {
					temp = directory.mkdir();
				}
				if (temp) {
					// We need to transfer to a file
					CommonsMultipartFile photoInMemory = restaurant.getPhoto();

					String fileName = photoInMemory.getOriginalFilename();
					// could generate file names as well

					File localFile = new File(directory.getPath(), fileName);

					// move the file from memory to the file

					photoInMemory.transferTo(localFile);
					restaurant.setFilename(localFile.getPath());
					System.out.println("File is stored at" + localFile.getPath());
					Restaurant r = restaurantDao.register(restaurant);

					request.getSession().setAttribute("restaurant", r);

					return new ModelAndView("restaurant-success", "restaurant", r);

				} else {
					System.out.println("Failed to create directory!");
					return new ModelAndView("error", "errorMessage", "error adding restaurant");
				}
			}

		} catch (RestaurantException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error adding restaurant");
		} catch (IllegalStateException e) {
			System.out.println("*** IllegalStateException: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("*** IOException: " + e.getMessage());
		}
		return new ModelAndView("restaurant-success");
	}

	@RequestMapping(value = "/rest/search", method = RequestMethod.GET)
	protected ModelAndView showSearchRestaurant() throws Exception {
		System.out.print("searchRestaurant");

		return new ModelAndView("user-home", "restaurant", new Restaurant());

	}

	@RequestMapping(value = "/rest/search", method = RequestMethod.POST)
	protected ModelAndView searchRestaurant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.print("searchStarted");

		try {
			String searchQuery = request.getParameter("keyword");
			List<Restaurant> restaurantList = restaurantDao.get(searchQuery);
			request.setAttribute("searchQuery", searchQuery);
			return new ModelAndView("restaurant-search-results", "restaurantList", restaurantList);

		} catch (RestaurantException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}

	@RequestMapping(value = "/rest/profile", method = RequestMethod.GET)
	protected ModelAndView showRestaurantProfilePage(HttpServletRequest request) throws Exception {
		System.out.println("Restaurant Profile");
		String restaurantName = request.getParameter("restaurantName");
		HttpSession session = request.getSession();
		session.setAttribute("restaurantName", restaurantName);
		System.out.println(restaurantName);
		Restaurant restaurant = restaurantDao.getRestaurant(restaurantName);
		List<Review> listOfReviews = restaurantDao.getReviews(restaurantName);
		session.setAttribute("listOfReviews", listOfReviews);
		System.out.println(restaurant);
		return new ModelAndView("restaurant-profile", "restaurant", restaurant);

	}

	@RequestMapping(value = "/rest/profile", method = RequestMethod.POST)
	protected ModelAndView setRestaurantProfilePage(HttpServletRequest request) throws Exception {
		System.out.print("Restaurant Profile");
		String restaurantName = request.getParameter("restaurantName");
		Restaurant restaurant = restaurantDao.getRestaurant(restaurantName);
		System.out.println(restaurant);
		return new ModelAndView("restaurant-profile", "restaurant", restaurant);

	}

	@RequestMapping(value = "/rest/viewRegistered", method = RequestMethod.POST)
	protected ModelAndView viewRegistered(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.print("searchStarted for all registered");

		try {
			String username = request.getParameter("username");
			List<Restaurant> restaurantList = restaurantDao.getRegistered(username);

			return new ModelAndView("restaurant-search-results", "restaurantList", restaurantList);

		} catch (RestaurantException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}

	@RequestMapping(value = "/rest/viewRegistered", method = RequestMethod.GET)
	protected ModelAndView getViewRegistered(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.print("searchStarted for all registered");
		return new ModelAndView("restaurant-search-results", "restaurant", new Restaurant());
	}

	@RequestMapping(value = "/rest/deleteRestaurant", method = RequestMethod.GET)
	protected ModelAndView getDeleteRestaurant(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.print("delete restaurant(GET)");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getType().equalsIgnoreCase("A")) {
			String restaurantName = request.getParameter("restaurantName");
			Restaurant rest = restaurantDao.getRestaurant(restaurantName);
			restaurantDao.deleteRestaurant(rest);
			List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();
			return new ModelAndView("view-all-rest", "restaurantList", restaurantList);
		} else {
			return new ModelAndView("access");
		}
	}

}
