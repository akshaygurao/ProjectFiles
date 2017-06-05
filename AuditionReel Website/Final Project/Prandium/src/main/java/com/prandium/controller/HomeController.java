package com.prandium.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	protected String goToUserHome(HttpServletRequest request) throws Exception {
		return "home";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	protected String logOut(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("Log Out Successful");
		return "home";
	}
	
	@RequestMapping(value = "/access", method = RequestMethod.GET)
	protected String denyAccess(HttpServletRequest request) throws Exception {
		return "access";
	}

}
