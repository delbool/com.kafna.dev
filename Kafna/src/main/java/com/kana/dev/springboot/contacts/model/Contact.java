package com.kana.dev.springboot.contacts.model;

public class Contact {
  private Long id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String emailAddress;

  public void setId(final Long id) {
    this.id = id;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }
  
  public String getLastName() {
    return lastName;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  public void setEmailAddress(final String emailAddress) {
    this.emailAddress = emailAddress;
  }
  
  public String getEmailAddress() {
    return emailAddress;
  }
  
  @Override
public String toString(){
	  return "Contact: [" + firstName + ", " + lastName + ", " + phoneNumber + ", " + emailAddress +"]";
  }
}