package com.marcin.phone.controllers;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.marcin.phone.model.Person;

/**
 * Person comparator (main comparator). Compares Person objects by phone number.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Component
public class PersonComparator implements Comparator<Person> {
	@Override
	public int compare(Person p1, Person p2) {
		return p1.getNumber()-p2.getNumber();
	}
}
	
/**
 * Person comparator. 
 * Compares Person objects by first name in first place; if result is 0, it compares objects by the last name.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Component
class FirstNamePersonComparator implements Comparator<Person> {
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

/**
 * Person comparator. 
 * Compares Person objects by last name in first place; if result is 0, it compares objects by the first name.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Component
class LastNamePersonComparator implements Comparator<Person> {
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