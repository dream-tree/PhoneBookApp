package com.marcin.phone.repository;

import java.util.ArrayList;
import java.util.List;

import com.marcin.phone.model.Person;

public interface PhoneBaseDAO {	
	public List<Person> readEntries();	
	public void saveUpdatedEntries(List<Person> updatedList);
	public List<Person> getPersonList();
}
