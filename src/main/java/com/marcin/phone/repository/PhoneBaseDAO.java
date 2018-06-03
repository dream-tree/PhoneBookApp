package com.marcin.phone.repository;

import java.util.List;

import com.marcin.phone.model.Person;

/**
 * General contract for reading all contacts from a persistent store, such as a .txt file or a database
 * and for saving all contacts to a persistent store.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
public interface PhoneBaseDAO {
	/**
	 * Loads all contacts from a persistent store, such as a .txt file or a database.
	 * @return list of all loaded contacts
	 */
	public List<Person> readEntries();	
	
	/**
	 * Saves all contacts to a persistent store, such as a .txt file or a database.
	 * @return list of all loaded contacts
	 */
	public void saveUpdatedEntries(List<Person> updatedList);
	
	/**
	 * Gets list of all loaded contacts.
	 * @return list of all loaded contacts
	 */
	public List<Person> getPersonList();
}