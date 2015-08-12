package com.kana.dev.springboot.contacts.rest;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kana.dev.springboot.contacts.model.Contact;
import com.kana.dev.springboot.contacts.service.ContactService;

@RestController
@RequestMapping("/service")
public class RestContactController {
	private static Logger logger = LoggerFactory.getLogger(RestContactController.class.getSimpleName());
	private final ContactService contactService;

	@Autowired
	public RestContactController(final ContactService contactService) {
		this.contactService = contactService;
	}

	@RequestMapping(value="/contacts", method=RequestMethod.GET)
	public List<Contact> getContacts(final Map<String,Object> model, final HttpServletResponse response) {
		logger.info(">>>>> .... Inside ContactController.home(...)");
		final List<Contact> contacts = contactService.findAll();
		return contacts;
	}

	@RequestMapping(value="/contacts", method=RequestMethod.POST)
	public Contact saveContact(final Contact contact) {
		logger.info(">>>>> .... Inside ContactController.submit(...)");		
		final Contact savedContact = contactService.save(contact);
		return savedContact;
	}
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public Contact save(@RequestBody final Contact contact) {
		logger.info(">>>>> .... Inside RestContactController.save(...)");
		logger.info("The user info to be persisted is {} ", contact);
		contactService.save(contact);
		return contact;
	}
	
}