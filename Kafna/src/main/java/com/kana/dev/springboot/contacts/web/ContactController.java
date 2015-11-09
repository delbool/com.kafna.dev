package com.kana.dev.springboot.contacts.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kana.dev.springboot.contacts.model.Contact;
import com.kana.dev.springboot.contacts.service.ContactService;

@Controller
@RequestMapping("/")
public class ContactController {
	private static Logger logger = LoggerFactory.getLogger(ContactController.class.getSimpleName());
	private final ContactService contactService;

	@Autowired
	public ContactController(final ContactService contactService) {
		this.contactService = contactService;
	}

	@RequestMapping(method=RequestMethod.GET)
	public String home(final Map<String,Object> model, final HttpServletResponse response) {
		logger.info(">>>>> .... Inside ContactController.home(...)");
		final List<Contact> contacts = contactService.findAll();
		model.put("contacts", contacts);
		return "home";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String submit(final Contact contact) {
		logger.info(">>>>> .... Inside ContactController.submit(...)");		
		contactService.save(contact);
		return "redirect:/";
	}
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public Contact save(final Contact contact) {
		logger.info(">>>>> .... Inside ContactController.submit(...)");		
		contactService.save(contact);
		return contact;
	}
	
}