package com.marcin.phone.repository;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.marcin.phone.model.Person;
import com.opencsv.CSVReader;

/**
 * Class implements interface for reading from a persistent store all available contacts by loading data from a .csv file.
 * It is also responsible for saving all available contacts to a .csv file again.
 * All loaded contacts are stored in a List collection (called phone base).
 * This List collection is retrieved by {@see DataOpertions} class for proper action chosen by user i.e.,
 * for likely adding new contact, removing existing contact or modifying existing contact.
 * Once the List collection is modified, it's returned to PhoneBaseImplCsvFile 
 * and the updated List collection is saved in the .csv file again.
 * 
 * Class uses CSVReader library for managing the .csv files.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Repository
@Qualifier(value = "csv")
public class PhoneBaseImplCsvFile implements PhoneBaseDAO {

	private static List<Person> personList;
	private static final Path path = FileSystems.getDefault().getPath("src/main/resources/phonebookList.csv");

	public PhoneBaseImplCsvFile() {
		personList = readEntries();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> readEntries() {
		List<Person> csvList = new ArrayList<>();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(path.toString()));
			String[] line;
			// reading all contacts from a .csv file
			while ((line = reader.readNext()) != null) {
				csvList.add(new Person(line[0], line[1], Integer.parseInt(line[2])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csvList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveUpdatedEntries(List<Person> updatedList) {
		/*
		 * CSVWriter csvOutput = null; try { csvOutput = new CSVWriter(new
		 * FileWriter(path.toString(), true), CSVWriter.DEFAULT_SEPARATOR,
		 * CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
		 * CSVWriter.DEFAULT_LINE_END); for(Person person : updatedList) {
		 * System.out.println(person); csvOutput.writeNext(new
		 * String[]{person.getFirstName(), person.getLastName(),
		 * String.valueOf(person.getNumber())}); } csvOutput.close(); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */

		List<String> updatedCsvList = new ArrayList<>();
		// converting List<Person> into String entries for the .csv file
		for (Person person : updatedList) {
			StringBuilder sb = new StringBuilder();
			sb.append(person.getFirstName()).append(",");
			sb.append(person.getLastName()).append(",");
			sb.append(String.valueOf(person.getNumber()));
			updatedCsvList.add(sb.toString());
		}
		Path path = FileSystems.getDefault().getPath("src/main/resources/phonebookList.csv");
		try {
			Files.write(path, updatedCsvList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> getPersonList() {
		return personList;
	}
}