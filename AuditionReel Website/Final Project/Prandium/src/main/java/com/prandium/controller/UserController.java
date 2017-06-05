package com.prandium.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.prandium.dao.RestaurantDAO;
import com.prandium.dao.UserDAO;
import com.prandium.exception.UserException;
import com.prandium.filter.XSSFilter;
import com.prandium.pojo.Restaurant;
import com.prandium.pojo.User;
import com.prandium.validator.UserValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("restaurantDao")
	RestaurantDAO restaurantDao;

	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView goHome() {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	protected String getloginUser(HttpServletRequest request) throws Exception {
		return "";
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	protected String loginUser(HttpServletRequest request) throws Exception {

		HttpSession session = (HttpSession) request.getSession();

		try {

			System.out.print("loginUser");
			String tempUsername = request.getParameter("username");
			String tempPassword = request.getParameter("password");
			String username = XSSFilter.removeXSS(tempUsername);
			String password = XSSFilter.removeXSS(tempPassword);

			User u = userDao.get(username, password);

			if (u == null) {
				System.out.println("UserName/Password does not exist");
				session.setAttribute("errorMessage", "UserName/Password does not exist");
				return "error";
			}

			List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();
			session.setAttribute("restaurantList", restaurantList);
			session.setAttribute("user", u);
			session.setAttribute("username", u.getUsername());
			if (u.getType().equalsIgnoreCase("U")) {
				return "user-home";
			} else if (u.getType().equalsIgnoreCase("A")) {
				return "admin-home";
			} else {
				return "owner-home";
			}

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "error while login");
			return "error";
		}

	}

	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	protected ModelAndView registerUser() throws Exception {
		System.out.print("registerUser");

		return new ModelAndView("register-user", "user", new User());

	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request, @ModelAttribute("user") User user,
			BindingResult result) throws Exception {

		validator.validate(user, result);

		if (result.hasErrors()) {
			return new ModelAndView("register-user", "user", user);
		}

		try {
			String tempUsername = user.getUsername();
			String tempPassword = user.getPassword();
			String tempFirstName = user.getFirstName();
			String tempLastName = user.getLastName();
			String username = XSSFilter.removeXSS(tempUsername);
			String password = XSSFilter.removeXSS(tempPassword);
			String firstName = XSSFilter.removeXSS(tempFirstName);
			String lastName = XSSFilter.removeXSS(tempLastName);

			user.setUsername(username);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);

			System.out.print("registerNewUser");
			user.setType("U");

			User u = userDao.register(user);

			request.getSession().setAttribute("user", u);

			return new ModelAndView("home", "user", u);

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/user/register-owners", method = RequestMethod.GET)
	protected ModelAndView registerOwner() throws Exception {
		System.out.print("registerOwner");

		return new ModelAndView("register-owners", "user", new User());

	}

	@RequestMapping(value = "/user/register-owners", method = RequestMethod.POST)
	protected ModelAndView registerNewOwner(HttpServletRequest request, @ModelAttribute("user") User user,
			BindingResult result) throws Exception {

		validator.validate(user, result);

		if (result.hasErrors()) {
			return new ModelAndView("register-owners", "user", user);
		}

		try {

			String tempUsername = user.getUsername();
			String tempPassword = user.getPassword();
			String tempFirstName = user.getFirstName();
			String tempLastName = user.getLastName();
			String username = XSSFilter.removeXSS(tempUsername);
			String password = XSSFilter.removeXSS(tempPassword);
			String firstName = XSSFilter.removeXSS(tempFirstName);
			String lastName = XSSFilter.removeXSS(tempLastName);

			user.setUsername(username);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);

			System.out.print("registerNewUser");
			user.setType("O");

			User u = userDao.register(user);

			request.getSession().setAttribute("user", u);

			return new ModelAndView("home", "user", u);

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/user/settings", method = RequestMethod.GET)
	protected ModelAndView getSettings(HttpServletRequest request) throws Exception {
		System.out.print("Account settings (GET)");
		User user = (User) request.getSession().getAttribute("user");

		return new ModelAndView("edit-profile", "user", user);

	}

	@RequestMapping(value = "/user/settings", method = RequestMethod.POST)
	protected ModelAndView updateSettings(HttpServletRequest request, @ModelAttribute("user") User user,
			BindingResult result) throws Exception {
		System.out.print("Account settings (POST)");
		validator.validate(user, result);

		// if (result.hasErrors()) {
		// System.out.println("Got Errors");
		// return new ModelAndView("edit-profile", "user", user);
		// }
		//
		try {
			User u = userDao.updateSettings(user);
			request.getSession().setAttribute("user", u);
			return new ModelAndView("account-success");

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/user/viewUsers", method = RequestMethod.GET)
	protected ModelAndView getAllUsers(HttpServletRequest request) throws Exception {
		System.out.print("Get all Users (GET)");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getType().equalsIgnoreCase("A")) {
			List<User> userList = userDao.getAll();
			return new ModelAndView("view-all-users", "userList", userList);
		} else {
			return new ModelAndView("access");
		}
	}

	@RequestMapping(value = "/user/viewUsers", method = RequestMethod.POST)
	protected ModelAndView setAllUsers(HttpServletRequest request) throws Exception {
		System.out.print("Get all Users (POST)");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getType().equalsIgnoreCase("A")) {
			List<User> userList = userDao.getAll();
			return new ModelAndView("view-all-users", "userList", userList);
		} else {
			return new ModelAndView("access");
		}
	}

	@RequestMapping(value = "/user/viewRestaurants", method = RequestMethod.GET)
	protected ModelAndView getAllRestaurants(HttpServletRequest request) throws Exception {
		System.out.print("Get all restaurants (GET)");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getType().equalsIgnoreCase("A")) {
			List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();
			return new ModelAndView("view-all-rest", "restaurantList", restaurantList);
		} else {
			return new ModelAndView("access");
		}
	}

	@RequestMapping(value = "/user/viewRestaurants", method = RequestMethod.POST)
	protected ModelAndView setAllRestaurants(HttpServletRequest request) throws Exception {
		System.out.print("Get all restaurants (POST)");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getType().equalsIgnoreCase("A")) {
			List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();
			return new ModelAndView("view-all-rest", "restaurantList", restaurantList);
		} else {
			return new ModelAndView("access");
		}
	}

	@RequestMapping(value = "/user/deleteUser", method = RequestMethod.GET)
	protected ModelAndView getDeleteUser(HttpServletRequest request) throws Exception {
		System.out.print("Delete user (GET)");
		String username = request.getParameter("username");
		User user = (User) request.getSession().getAttribute("user");
		if (user.getType().equalsIgnoreCase("A")) {
			User u = userDao.getUser(username);
			userDao.delete(u);
			List<User> userList = userDao.getAll();
			return new ModelAndView("view-all-users", "userList", userList);
		} else {
			return new ModelAndView("access");
		}
	}

}
