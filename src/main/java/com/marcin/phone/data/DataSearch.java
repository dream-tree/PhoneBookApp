package com.marcin.phone.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcin.phone.model.Person;
import com.marcin.phone.repository.DataOperations;

import lombok.Getter;

/**
 * Class implementing the search mechanism for contacts saved in the phone base.
 * It is utilized in the search bar in the application main window. 
 * This mechanism allows to find a contact by a phone number, by a single word 
 * (first or last name), or by 2 separate words i.j., by the first and the last name
 * simultaneously. Other combinations are not allowed and are rejected by the
 * search mechanism.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Component
public class DataSearch {

	private List<Person> personList;
	@Getter
	private Set<Person> foundContacts;
	private DataOperations dataOperations;

	@Autowired
	public DataSearch(DataOperations dataOperations) {
		this.foundContacts = new HashSet<>();
		this.dataOperations = dataOperations;
		personList = dataOperations.getList();
	}

	/**
	 * Initial (zero-level) point for searching contacts in the phone base. It determines
	 * if user input is an integer (phone number) or String (last/first name). 
	 * 
	 * @param user
	 *            input to check
	 */
	public Set<Person> initSearch(String input) {
		int number = -1;
		try {
			number = Integer.parseInt(input);
			// excluding numbers with less than 9 digits or wit 10 digits
			if (number / 100000000 < 1 || number / 100000000 >= 10) {
				foundContacts.add(new Person("Phone number should be 9 digits long.", "", -1));
				return foundContacts;
			} else {
				return searchByNumber(number);
			}
			// catching String input and too long numbers (higher than Integer.MAX_VALUE also)
		} catch (Exception e) {
			e.getMessage();
		}
		// search depends on number of words entered
		String[] splittedInput = input.trim().split(" ");
		// if 1 word entered
		if (splittedInput.length == 1) {
			return searchByName(input.trim());
			// if 2 words entered
		} else if (splittedInput.length == 2) {
			return searchDeeper(splittedInput);
		}
		// max 2 words might be entered (firstName, lastName) in an arbitrary sequence
		else if (splittedInput.length > 2) {
			foundContacts.add(new Person("Incorrect input (too many words).", "", -1));
			return foundContacts;
		}
		return searchByName(input);
	}

	/**
	 * Searches the phone base by the first name (Person.firstName) or the last name
	 * (Person.lastName). Set collection is used to avoid adding to the resulting
	 * {@link #foundContacts} collection the same contact if one contact has 
	 * the same first and last name e.g., John John.
	 * 
	 * @param name
	 *            first and/or last name to be found in the phone base
	 * @return foundContacts set of contacts found in the phone base after searching
	 *         the first or/and last name. Resulted set might be empty.
	 */
	public Set<Person> searchByName(String name) {
		int foundEntries = 0;
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getFirstName().toLowerCase().equals(name.toLowerCase())) {
				foundEntries++;
				foundContacts.add(personList.get(i));
			}
			if (personList.get(i).getLastName().toLowerCase().equals(name.toLowerCase())) {
				foundEntries++;
				foundContacts.add(personList.get(i));
			}
		}
		if (foundEntries == 0) {
			return foundContacts;
		}
		return foundContacts;
	}

	/**
	 * Searches phone base by the phone number entered by the user (Person.number).
	 * 
	 * @param number
	 *            number to be found in phone base
	 * @return foundContacts set of contacts found in the phone base after the
	 *         searching the phone number. Resulted set might be empty.
	 */
	public Set<Person> searchByNumber(int number) {
		int foundEntries = 0;
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getNumber() == number) {
				foundEntries++;
				foundContacts.add(personList.get(i));
			}
		}
		if (foundEntries == 0) {
			foundContacts.add(new Person("No contact found.", ""));
			return foundContacts;
		}
		return foundContacts;
	}

	/**
	 * Searches the phone base by the first name (Person.firstName) and the last
	 * name (Person.lastName). Order of the entered words is irrelevant. Searching
	 * contacts mechanism (similarly as adding contact mechanism) allows to save/find 
	 * contacts whose first and last names are converted (as far as they have
	 * different phone numbers), as given in the example: John Smith vs. Smith John.
	 * 
	 * @param name
	 *            array of two Strings - first and last name - to be found in the
	 *            phone base
	 * @return foundContacts set of contacts found in the phone base after searching
	 *         the first or/and last name. Resulted set might be empty.
	 */
	public Set<Person> searchDeeper(String[] splittedInput) {
		for (int i = 0; i < personList.size(); i++) {
			if (personList.get(i).getFirstName().toLowerCase().equals(splittedInput[0].toLowerCase())
					&& personList.get(i).getLastName().toLowerCase().equals(splittedInput[1].toLowerCase())) {
				foundContacts.add(personList.get(i));
				// break;
			} else if (personList.get(i).getFirstName().toLowerCase().equals(splittedInput[1].toLowerCase())
					&& personList.get(i).getLastName().toLowerCase().equals(splittedInput[0].toLowerCase())) {
				foundContacts.add(personList.get(i));
				// break;
			}
		}
		return foundContacts;
	}

	/**
	 * Displays all entries in the phone base.
	 * 
	 * @return foundContacts set of contacts found in the phone base after the
	 *         searching process.
	 */
	public Set<Person> showAllBase() {
		for (int i = 0; i < personList.size(); i++) {
			foundContacts.add(personList.get(i));
		}
		return foundContacts;
	}
}