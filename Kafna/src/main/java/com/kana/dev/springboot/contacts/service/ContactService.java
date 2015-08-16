package com.kana.dev.springboot.contacts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kana.dev.springboot.contacts.model.Contact;
import com.kana.dev.springboot.contacts.persistence.ContactRepository;

@Service
public class ContactService {

	private static Logger logger = LoggerFactory.getLogger(ContactService.class.getSimpleName());
	private final ContactRepository contactRepo;

	@Autowired
	public ContactService(final ContactRepository contactRepo) {
		this.contactRepo = contactRepo;
	}

	public void submit(final Contact contact) {
		logger.info(">>>>> .... Inside ContactService.submit(...)");		
		contactRepo.save(contact);
	}
	
	public Contact save(final Contact contact) {
		logger.info(">>>>> .... Inside ContactService.save(...)");		
		contactRepo.save(contact);
		return contact;
	}

	public List<Contact> findAll() {
		logger.info(">>>>> .... Inside ContactService.findAll(...)");
		final List<Contact> contacts = contactRepo.findAll();
		return contacts;
	}
}
