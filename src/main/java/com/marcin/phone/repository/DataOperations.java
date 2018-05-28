package com.marcin.phone.repository;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.marcin.phone.data.DataSearch;
import com.marcin.phone.model.Person;

@Component
public class DataOperations {
	
	private PhoneBaseDAO base;
/*	private DataSearch baseSearching;*/
	private List<Person> list;
	
	@Autowired
	public DataOperations(@Qualifier(value="txt") PhoneBaseDAO base/*, DataSearch baseSearching*/) {
		this.base = base;
/*		this.baseSearching = baseSearching;*/
		list = base.getPersonList();
	//	baseSearching.setPersonList(list);	
	}	
	
	/**
	 * Modifies contact selected by user.
	 * Operation has two phases:
	 * (1) it removes contact selected by user from the list and 
	 * (2) adds modified version of selected contact to the list.
	 * @param chosenContact contact selected by user
	 * @param modifiedContact contact after modification by user
	 */	
	public void updateList(Person chosenContact, Person modifiedContact) {
		for(Iterator<Person> it = list.iterator(); it.hasNext(); ) {
			Person temp = it.next();
			if(temp.getNumber()==(chosenContact.getNumber())) {
				it.remove();		
			}
		}
		toUpperAndLowerCase(modifiedContact);
		list.add(modifiedContact);
		base.saveUpdatedEntries(list);
	}
	
	/**
	 * adds new person specified by user to contact list
	 * @param newPerson person to add to contact list
	 */
	public void addToList(Person newPerson) {
		toUpperAndLowerCase(newPerson);
		list.add(newPerson);
		base.saveUpdatedEntries(list);
	}	
	
	public void removeFromList(Person deletedContact) {
		for(Iterator<Person> it = list.iterator(); it.hasNext(); ) {
			Person temp = it.next();
			if(temp.getNumber()==deletedContact.getNumber()) {
				it.remove();		
			}
		}
		base.saveUpdatedEntries(list);
	}
	
	/**
	 * capitalizes first letter of first and last name and 
	 * converts all other characters in first and last name to lower case
	 * @param modifiedContact contact after modification by user
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
	 * @return the list
	 */
	public List<Person> getList() {
		return list;
	}
	
	
}