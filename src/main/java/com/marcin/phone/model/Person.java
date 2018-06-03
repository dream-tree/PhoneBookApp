package com.marcin.phone.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Person is the entity used to create contact entries in the phone base (database).
 * Each "valid" Person object has the first name, the last name and the phone number.
 * Valid object means an object ready to be saved, deleted or updated in the phone base (database).
 * There are also "invalid" Person objects consisting of first and last name only.
 * These objects are constructed by the application logic only to note exceptional behaviour
 * if no contacts are found by the search engine.
 * They might be constructed in 3 classes as listed below: 
 * <ul>
 *  <li>{@link com.marcin.phone.data.InputValidator#isPhoneNumberUnique(int)}</li>
 *  <li>{@link com.marcin.phone.controllers.CenterGridController#processUserInput()}</li>
 *  <li>{@link com.marcin.phone.data.DataSearch#searchByNumber(int)}</li>
 * </ul>
 * Invalid objects cannot be saved to the phone base (database).
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Component
@Getter
@Setter
public class Person {

	String firstName;
	String lastName;
	int number;

	/**
	 * Deafult Person constructor used by org.springframework.beans.factory.
	 */
	public Person() {		
	}
		
	/**
	 * Constructs a "valid" Person instance as described in {@link Person}.
	 * @param firstName first name of phone base contact (person)
	 * @param lastName last name of phone base contact (person)
	 * @param number phone number of phone base contact (person)
	 */
	public Person(String firstName, String lastName, int number) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.number = number;
	}
	
	/**
	 * Constructs an "invalid" Person instance as described in {@link Person}.
	 * @param firstName first name of phone base contact (person)
	 * @param lastName last name of phone base contact (person)
	 */
	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// %,d: Requires the output to include the locale-specific group separators (Poland: xxx xxx xxx).
		return String.format("Phone number:  %,d  of %s %s", number, firstName, lastName);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + number;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (number != other.number)
			return false;
		return true;
	}
}