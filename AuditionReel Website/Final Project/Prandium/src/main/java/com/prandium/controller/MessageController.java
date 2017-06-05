package com.prandium.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.prandium.dao.MessageDAO;
import com.prandium.exception.MessageException;
import com.prandium.filter.XSSFilter;
import com.prandium.pojo.Messages;
import com.prandium.pojo.User;
import com.prandium.validator.MessagesValidator;

@Controller
@RequestMapping("/message/*")
public class MessageController {
	
	@Autowired
	@Qualifier("messageDao")
	MessageDAO messageDao;
	
	@Autowired
	@Qualifier("messagesValidator")
	MessagesValidator messagesValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(messagesValidator);
	}
	
	@RequestMapping(value = "/message/inbox", method = RequestMethod.GET)
	protected ModelAndView getMessageHome(HttpServletRequest request) throws Exception {
		System.out.print("Message Home(GET)");
		HttpSession session = (HttpSession) request.getSession();
		String username = (String)session.getAttribute("username");
		List<Messages> messageList = messageDao.getMessagesFromUser(username);

		return new ModelAndView("message-home", "messageList", messageList);

	}
	
	@RequestMapping(value = "/message/inbox", method = RequestMethod.POST)
	protected ModelAndView setMessageHome(HttpServletRequest request) throws Exception {
		System.out.print("Message Home(POST)");
		HttpSession session = (HttpSession) request.getSession();
		String username = (String)session.getAttribute("username");
		List<Messages> messageList = messageDao.getMessagesFromUser(username);

		return new ModelAndView("message-home", "messageList", messageList);

	}
	
	
	
	@RequestMapping(value="/message/reply", method= RequestMethod.GET)
	protected ModelAndView getReplyMessage() throws Exception{
		return new ModelAndView("message-home", "messages", new Messages());
	}
	
	@RequestMapping(value = "/message/reply", method = RequestMethod.POST)
	protected ModelAndView replyMessage(HttpServletRequest request, @ModelAttribute("messages") Messages messages, BindingResult result) throws Exception {
		System.out.print("New Message Reply");
		
		messagesValidator.validate(messages, result);

		if (result.hasErrors()) {
			return new ModelAndView("message-home", "messages", messages);
		}
		
        try {

			System.out.print("registerNewRestaurant");
			String message = messages.getMessage();
			String finalMessage = XSSFilter.removeXSS(message);
			messages.setMessage(finalMessage);
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			String recipient = request.getParameter("to");
			Date d = new Date();
	        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	        String messageDate = format.format(d);
	        messages.setMessageDate(messageDate);
	        messages.setUserName(user.getUsername());
	        messages.setFromUser(recipient);

			Messages m = messageDao.insertMessage(messages);
			
			request.getSession().setAttribute("messages", m);
			
			return new ModelAndView("message-success", "messages", m);

		} catch (MessageException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error adding restaurant");
		}

	}
	
	@RequestMapping(value="/message/compose", method= RequestMethod.GET)
	protected ModelAndView getComposeMessage() throws Exception{
		
		return new ModelAndView("message-compose", "messages", new Messages());
	}
	
	@RequestMapping(value="/message/compose", method= RequestMethod.POST)
	protected ModelAndView composeMessage() throws Exception{
		//write code to insert message in database
		return new ModelAndView("message-compose", "messages", new Messages());
	}
	
	@RequestMapping(value="/message/sentMail", method= RequestMethod.GET)
	protected ModelAndView getSentMessage(HttpServletRequest request) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		String username = (String)session.getAttribute("username");
		List<Messages> messageList = messageDao.getMessagesForUser(username);

		return new ModelAndView("message-sent", "messageList", messageList);
	}
	
	@RequestMapping(value="/message/sentMail", method= RequestMethod.POST)
	protected ModelAndView setSentMessage(HttpServletRequest request) throws Exception{
		
		HttpSession session = (HttpSession) request.getSession();
		String username = (String)session.getAttribute("username");
		List<Messages> messageList = messageDao.getMessagesForUser(username);

		return new ModelAndView("message-sent", "messageList", messageList);
	}
	

}
