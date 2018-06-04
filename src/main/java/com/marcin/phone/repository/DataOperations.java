package com.marcin.phone.repository;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.marcin.phone.model.Person;

/**
 * Class is responsible for adding, modifying and removing contacts i.e., Person
 * objects to/from the uploaded contact list - List collection. Once the List
 * collection update is done, such a contact list is forwarded to the
 * {@link com.marcin.phone.repository.PhoneBaseDAO} responsible for saving
 * contacts as the persistent data.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Component
public class DataOperations {

	private PhoneBaseDAO base;
	private List<Person> list;

	@Autowired
	public DataOperations(@Qualifier(value = "csv") PhoneBaseDAO base) {
		this.base = base;
		list = base.getPersonList();
	}

	/**
	 * Modifies contact selected by user. Each operation has two phases:
	 * <ol>
	 * <li>it removes single contact selected by user from the list and</li>
	 * <li>adds modified version of the selected contact to the list.</li>
	 * <ol>
	 * 
	 * @param chosenContact
	 *            contact selected by user
	 * @param modifiedContact
	 *            contact after modification by user
	 */
	public void updateList(Person chosenContact, Person modifiedContact) {
		for (Iterator<Person> it = list.iterator(); it.hasNext();) {
			Person temp = it.next();
			if (temp.getNumber() == (chosenContact.getNumber())) {
				it.remove();
			}
		}
		toUpperAndLowerCase(modifiedContact);
		list.add(modifiedContact);
		base.saveUpdatedEntries(list);
	}

	/**
	 * Adds new person (contact) specified by user to the contact list.
	 * 
	 * @param newPerson
	 *            person to add to contact list
	 */
	public void addToList(Person newPerson) {
		toUpperAndLowerCase(newPerson);
		list.add(newPerson);
		base.saveUpdatedEntries(list);
	}

	/**
	 * Removes person (contact) selected by user from the contact list.
	 * 
	 * @param newPerson
	 *            person to add to contact list
	 */
	public void removeFromList(Person deletedContact) {
		for (Iterator<Person> it = list.iterator(); it.hasNext();) {
			Person temp = it.next();
			if (temp.getNumber() == deletedContact.getNumber()) {
				it.remove();
			}
		}
		base.saveUpdatedEntries(list);
	}

	/**
	 * Capitalizes first letter of the first and last name and converts all other
	 * characters in the first and last name to lower case.
	 * 
	 * @param modifiedContact
	 *            contact after modification by user.
	 */
	public void toUpperAndLowerCase(Person modifiedContact) {
		StringBuilder first = new StringBuilder(modifiedContact.getFirstName());
		first.replace(0, 1, first.substring(0, 1).toUpperCase()).toString();
		first.replace(1, first.length(), first.substring(1, first.length()).toLowerCase()).toString();
		StringBuilder last = new StringBuilder(modifiedContact.getLastName());
		last.replace(0, 1, last.substring(0, 1).toUpperCase()).toString();
		last.replace(1, last.length(), last.substring(1, last.length()).toLowerCase()).toString();
		modifiedContact.setFirstName(first.toString());
		modifiedContact.setLastName(last.toString());
	}

	/**
	 * @return the list of contacts
	 */
	public List<Person> getList() {
		return list;
	}
}