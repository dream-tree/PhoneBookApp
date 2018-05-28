package com.marcin.phone.controllers;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.marcin.phone.model.Person;

@Component
public class PersonComparator implements Comparator<Person> {
	// main comparator: comparing by phone number
	@Override
	public int compare(Person p1, Person p2) {
		return p1.getNumber()-p2.getNumber();
	}
}
	
@Component
class FirstNamePersonComparator implements Comparator<Person> {
	// additional comparator: comparing by first name, if result is 0 comparing by last name
	@Override
	public int compare(Person p1, Person p2) {
		int firstNameComparision = p1.getFirstName().compareTo(p2.getFirstName());
		if(firstNameComparision != 0) {
			return firstNameComparision;
		} else {
			return p1.getLastName().compareTo(p2.getLastName());
		}
	}	
}	

@Component
class LastNamePersonComparator implements Comparator<Person> {
	// additional comparator: comparing by last name, if result is 0 comparing by first name
	@Override
	public int compare(Person p1, Person p2) {
		int lastNameComparision = p1.getLastName().compareTo(p2.getLastName());
		if(lastNameComparision != 0) {
			return lastNameComparision;
		} else {
			return p1.getFirstName().compareTo(p2.getFirstName());
		}
	}	
}	