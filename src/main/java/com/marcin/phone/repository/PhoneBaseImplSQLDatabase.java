package com.marcin.phone.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.marcin.phone.model.Person;

/**
 * Class implements interface for retrieving and storing data (phone numbers base i.e. 
 * phone numbers belonging to individual persons).
 * Class reads data from a .txt file containing all contacts (phone numbers base)
 * and saves contacts into a .txt file again if the base was changed while using it.
 * @author dream-tree
 */
// @Repository   by now NullPointer in SessionFactory
@Qualifier(value="database")
public class PhoneBaseImplSQLDatabase implements PhoneBaseDAO {
	
	private static List<Person> personList;
	private SessionFactory factory;
	
	public PhoneBaseImplSQLDatabase() {
		personList = readEntries();
		factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Person.class)
				.buildSessionFactory();
	}
	
	/** 
	 * reads all data from a .txt file containing all contacts (phone numbers base)
	 * and converts all String entries into List<Person> object
	 */
	public List<Person> readEntries() {
		Session session = factory.getCurrentSession();
		List<Person> list = new ArrayList<>();
		try {
			list = session.createQuery("from Person").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
		return list;		
	}
	
	@Override
	public List<Person> getPersonList() {
		return personList;
	}
	 	
	/** 
	 * Saves new person data in the base (PhoneBook)
	 * and creates .txt file containing all contacts (the whole phone numbers base) altogether with freshly added contact(s)
	 * by converting List<Person> into String entries
	 * @param firstName first name of new person
	 * @param lastName last name of new person
	 * @param nick nickname name of new person
	 * @param phoneNumber phone number of new person
	 */
	public void saveUpdatedEntries(List<Person> updatedList) {
		Session session = factory.getCurrentSession();
		session.createQuery("delete all from person");
		List<String> updatedTxtList = new ArrayList<>();
		// TODO: use StringBuilder
		for (Person person : updatedList) {
			updatedTxtList.add(person.getFirstName() + " " + person.getLastName() + " " + " " 
			+ String.valueOf(person.getNumber()));			
		}
		Path path = FileSystems.getDefault().getPath("src/application/phonebookList.txt");
		try {
			Files.write(path, updatedTxtList);		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
