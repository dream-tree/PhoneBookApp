package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class reads data from a .txt file containing all contacts (phone numbers base)
 * and saves contacts into a .txt file again if the base was changed while using it.
 * @author dream-tree
 */
public class PhoneBase  {
	
	private static List<Person> personList;
	
	public PhoneBase() {
		personList = readEntries();
	}
	
	/** 
	 * reads all data from a .txt file containing all contacts (phone numbers base)
	 * and converts all String entries into List<Person> object
	 */
	public static List<Person> readEntries() {
		List<String> txtList = new ArrayList<>();		
		Path path = FileSystems.getDefault().getPath("src/application/phonebookList.txt");
		try {
			txtList = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
		}		

		String firstName = "";
		String lastName = "";
		int number = 0;		
		Scanner in = null;
		
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
	 * @return the personList
	 */
//	public List<Person> getPersonList() {
//		return personList;
//	}

	/**
	 * @param personList the personList to set
	 */
//	public void setPersonList(List<Person> personList) {
//		this.personList = personList;
//	}
	
	public static List<Person> getStaticPersonList() {
		return personList;
	}
	 
	
	/** 
	 * saves new person data in the base (PhoneBook)
	 * and creates .txt file containing all contacts (whole phone numbers base) altogether with freshly added contact(s)
	 * by converting List<Person> into String entries
	 * @param firstName first name of new person
	 * @param lastName last name of new person
	 * @param nick nickname name of new person
	 * @param phoneNumber phone number of new person
	 */
	public void addNewEntry(String firstName, String lastName, String nick, int phoneNumber) {
		personList.add(new Person(firstName, lastName, phoneNumber));
	}
	
	public static void saveUpdatedList(List<Person> updatedList) {
		List<String> updatedTxtList = new ArrayList<>();
		// TODO
		// use StringBuilder
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
