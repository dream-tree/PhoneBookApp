package application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataSearch {

	private List<Person> personList;
	private Set<Person> foundContacts;
	
	public DataSearch(List<Person> list) {
		this.personList = list;
		this.foundContacts = new HashSet<>();
	}
	
	/** 
	 * initial (zero-level) point for searching the base (PhoneBase);
	 * determines if user input is an integer (phone number) or String (last/first name)
	 * @param user input to check
	 */	
	public Set<Person> initSearch(String input) {
		int number = -1;
		try {
			number = Integer.parseInt(input);
			// excluding numbers with less than 9 digits
			if(number/100000000<1) {
				foundContacts.add(new Person("Submitted phone number is too short.", "", 0));			
				return foundContacts;		
			} else {
				return searchByNumber(number);
			}
		// catching String input and too long numbers (higher than Integer.MAX_VALUE too) 
		} catch (Exception e) {
			e.getMessage();		
		}
		// search depends on number of words entered
		String[] splittedInput = input.trim().split(" ");
		// if 1 word entered
		if(splittedInput.length == 1) {
			System.out.println("foundcontacts3xx: " + foundContacts);
			return searchByName(input.trim());	
		// if 2 words entered
		} else if(splittedInput.length == 2) {
			return searchDeeper(splittedInput); 
		}
		// max 2 words might be entered (firstName, lastName) in an arbitrary sequence
		else if(splittedInput.length > 2) {
			foundContacts.add(new Person("Incorrect input (too many words).", "", 0));
			return foundContacts;
		}
		return searchByName(input);		
	}
	
	/** 
	 * searches the base (PhoneBase) by first name (Person.firstName) and/or last name (Person.lastName);
	 * set is used to exclude doubling results if first name and last name was typed in search bar
	 * @param name input word to found in base
	 */	
	public Set<Person> searchByName(String name) {
		int foundEntries = 0;
		for(int i = 0; i < personList.size(); i++) {
			if(personList.get(i).getFirstName().toLowerCase().equals(name.toLowerCase())) {
				foundEntries++;
				foundContacts.add(personList.get(i));
			}
			if(personList.get(i).getLastName().toLowerCase().equals(name.toLowerCase())) {
				foundEntries++;
				foundContacts.add(personList.get(i));	
			}
		}
		if(foundEntries == 0) {
			return foundContacts;
		}			
		return foundContacts;
	}
	
	/** 
	 * searches the base (PhoneBase) by telephone number entered by the user (Person.number);
	 * @param number number to found in base entered by the seeker
	 */	
	public Set<Person> searchByNumber(int number) {
		int foundEntries = 0;
		for(int i = 0; i < personList.size(); i++) {
			if(personList.get(i).getNumber() == number) {   
				foundEntries++;
				foundContacts.add(personList.get(i));
			}
		}
		if(foundEntries == 0) {
			foundContacts.add(new Person("No contact found.", ""));
			return foundContacts;
		}
		return foundContacts;
	}
	
	public Set<Person> searchDeeper(String[] splittedInput) {	
		for(int i = 0; i < personList.size(); i++) {
			if(personList.get(i).getFirstName().toLowerCase().equals(splittedInput[0].toLowerCase())
					&& personList.get(i).getLastName().toLowerCase().equals(splittedInput[1].toLowerCase())) {
						foundContacts.add(personList.get(i));
						break;
			} else if(personList.get(i).getFirstName().toLowerCase().equals(splittedInput[1].toLowerCase())
					&& personList.get(i).getLastName().toLowerCase().equals(splittedInput[0].toLowerCase())) {
						foundContacts.add(personList.get(i));
						break;
			} 					
		}
		return foundContacts;
	}	
	
	/** 
	 * shows all entries in the base (PhoneBase)
	 */	
	public Set<Person> showAllBase() {
		for(int i = 0; i < personList.size(); i++) {
			foundContacts.add(personList.get(i));
		}
		return foundContacts;
	}

	/**
	 * @return the foundContacts
	 */
	public Set<Person> getFoundContacts() {
		return foundContacts;
	}
}