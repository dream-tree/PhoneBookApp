package com.marcin.phone.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.marcin.phone.model.Person;

/**
 * Class implements interface for reading from a persistent store all available contacts by loading data from a .txt file.
 * It is also responsible for saving all available contacts to a .txt file again.
 * All loaded contacts are stored in a List collection (called phone base).
 * This List collection is retrieved by {@see DataOpertions} class for proper action chosen by user i.e.,
 * for likely adding new contact, removing existing contact or modifying existing contact.
 * Once the List collection is modified, it's returned to PhoneBaseImplTxtFile 
 * and the updated List collection is saved in the .txt file again.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Repository
@Qualifier(value="txt")
public class PhoneBaseImplTxtFile implements PhoneBaseDAO {
	
	private static List<Person> personList;
	
	public PhoneBaseImplTxtFile() {
		personList = readEntries();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> readEntries() {
		List<String> txtList = new ArrayList<>();
		// reading all contacts from a .txt file 
		Path path = FileSystems.getDefault().getPath("src/main/resources/phonebookList.txt");
		try {
			txtList = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
		}		

		String firstName = "";
		String lastName = "";
		int number = 0;		
		Scanner in = null;
		
		// converting all String entries into List<Person> objects
		List<Person> list = new ArrayList<>();
			for(int i = 0; i < txtList.size(); i++) {
				String oneLineToFrags = txtList.get(i);
				in = new Scanner(oneLineToFrags);			
				firstName = in.next();
				lastName = in.next();
				number = Integer.parseInt(in.next());
				
				list.add(new Person(firstName, lastName, number)); 
				in.close();
			}
		return list;		
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> getPersonList() {
		return personList;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void saveUpdatedEntries(List<Person> updatedList) {
		List<String> updatedTxtList = new ArrayList<>();
		// TODO: use StringBuilder
		// converting List<Person> into String entries for the .txt file
/*		for (Person person : updatedList) {
			updatedTxtList.add(person.getFirstName() + " " + person.getLastName() + " " + " " 
			+ String.valueOf(person.getNumber()));			
		}*/
		for (Person person : updatedList) {
			StringBuilder sb = new StringBuilder();
			sb.append(person.getFirstName()).append(" ");
			sb.append(person.getLastName()).append("  ");
			sb.append(String.valueOf(person.getNumber()));
			updatedTxtList.add(sb.toString());			
		}
		
		Path path = FileSystems.getDefault().getPath("src/main/resources/phonebookList.txt");
		try {
			Files.write(path, updatedTxtList);		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}