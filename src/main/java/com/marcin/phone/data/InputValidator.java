package com.marcin.phone.data;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.marcin.phone.model.Person;
import com.marcin.phone.repository.DataOperations;
import com.marcin.phone.views.CenterGridView;
import com.marcin.phone.views.FillingFormView;

import javafx.scene.control.Label;

@Component
public class InputValidator {

	private FillingFormView formView;
	private DataOperations dataOperations;
	private Label[] appInfo;
	private CenterGridView grid;
	private DataSearch dataSearch;
	
	public InputValidator(FillingFormView formView, DataOperations dataOperations, 
			CenterGridView grid, DataSearch dataSearch) {
		this.formView=formView;
		this.dataOperations=dataOperations;
		this.grid=grid;
		this.dataSearch = dataSearch;
	}

	/**
	 * Checks out if user input is valid and forwards user changes to update the contact list
	 * either to add new contact or to modify existing contact.
	 * @param userChoice flag checking if user wants to add or or change existing contact
	 */
	public void checkAndSaveInput(int userChoice) {		
		// checking if contact is correctly typed
		String userInputCheck = checkInput(formView.getFirstText().getText(), formView.getLastText().getText(), 
				formView.getNumberText().getText());		
		if(userInputCheck.equals("")) {		
			int number = Integer.parseInt(formView.getNumberText().getText().trim());
			Person modifiedContact = new Person(formView.getFirstText().getText().trim(),
					formView.getLastText().getText().trim(), number);
			// adding new contact
			if(userChoice==-1) {
				if(isPhoneNumberUnique(number)) {
					dataOperations.addToList(modifiedContact);
					formView.getNewWindow().close();
					appInfo = grid.getAppInfo();
					appInfo[0].setText("Contact added.");
				} else {
					formView.getFormAppInfo().setText("Phone number " + number 
							+ " already exists in the phonebase.\nYou cannot use it again.");
				}	
			// updating existing contact
			} else {
				dataOperations.updateList(formView.getChosenContact(), modifiedContact);	
				formView.getNewWindow().close();	
				appInfo = grid.getAppInfo();
				appInfo[0].setText("Contact modified.");
			}	
		} else {
			formView.getFormAppInfo().setText(userInputCheck);
		}
	}
	
	/**
	 * Checks out if user input is correct
	 * @param firstName first name input typed by user
	 * @param lastName last name input typed by user
	 * @param number number input typed by user
	 * @return empty String if input is correct or error info concerning user input
	 */
	public String checkInput(String firstName, String lastName, String number) {
		String result = "";
		try {
			int numbercheck = Integer.parseInt(formView.getNumberText().getText());
			// avoiding the phone number to be less than 6 digit long 
			if(numbercheck/100000000 < 1) {
				result = "Phone number should be 9-digit long.\nNo spaces and hyphens allowed.\n";
			}
		} catch (Exception e){
			// all other numbers input errors (letters or too big integers).
			result = "Phone number should be 9-digit long.\nNo spaces and hyphens allowed.\n";
			e.getMessage();

		}
		
		String[] one = firstName.split(" ");
		String[] two = lastName.split(" ");
		
		if(one.length>1 || two.length>1) {
			result += "Too many words entered in first or last name field.\n";
		}
		// handling with inserting space(s) in firstName or lastName text fields
		if(one.length==0 || two.length==0) {
			result += "Did you enter whitespace only in some field?";
		}	
		return result;
	}
	
	public boolean isPhoneNumberUnique(int number) {	
		Set<Person> x = dataSearch.searchByNumber(number);
		if(x.contains(new Person("No contact found.", ""))) {
			return true;
		} else {	
			return false;			
		}		
	}
	
	
}
