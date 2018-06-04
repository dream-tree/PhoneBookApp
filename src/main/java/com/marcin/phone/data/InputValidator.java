package com.marcin.phone.data;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.marcin.phone.model.Person;
import com.marcin.phone.repository.DataOperations;
import com.marcin.phone.views.CenterGridView;
import com.marcin.phone.views.FillingFormView;

import javafx.scene.control.Label;

/**
 * Class is responsible for validation of input entered by user in the filling
 * form window constructed by {@link com.marcin.phone.views.FillingFormView} class. 
 * It allows to check 3 text fields being filled by user: first name field, 
 * last name field and phone number field.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Component
public class InputValidator {

	private FillingFormView formView;
	private DataOperations dataOperations;
	private Label[] appInfo;
	private CenterGridView grid;
	private DataSearch dataSearch;

	public InputValidator(FillingFormView formView, DataOperations dataOperations, CenterGridView grid,
			DataSearch dataSearch) {
		this.formView = formView;
		this.dataOperations = dataOperations;
		this.grid = grid;
		this.dataSearch = dataSearch;
	}

	/**
	 * Initial point of checking out user input typed in the filling form window.
	 * For the phone number text field:
	 * <ul>
	 * <li>it checks out if input is a valid integer,</li>
	 * <li>it examines the number of digits (9 only allowed).</li>
	 * </ul>
	 * For the text fields:
	 * <ul>
	 * <li>it examines the number of single tokens entered by user (must not exceed
	 * 2),</li>
	 * <li>it checks out if user typed only white spaces in the text field what is
	 * forbidden.</li>
	 * </ul>
	 * 
	 * @param firstName
	 *            first name input typed by user
	 * @param lastName
	 *            last name input typed by user
	 * @param number
	 *            number input typed by user
	 * @return empty String if input is correct or error info concerning user input
	 */
	public String checkInput(String firstName, String lastName, String number) {
		String result = "";
		String warning = "Phone number should be 9 digits long\n" + "and mustn't start with 0.\n"
				+ "No spaces or hyphens allowed.\n";
		try {
			int numbercheck = Integer.parseInt(formView.getNumberText().getText());
			// avoiding the phone number to be less than 9 digit long or 10 digits long
			if (numbercheck / 100000000 < 1 || numbercheck / 100000000 >= 10) {
				result = warning;
			}
		} catch (Exception e) {
			// all other numbers input errors (letters or integers exceeding the Integer
			// type range).
			result = warning;
			e.getMessage();
		}

		String[] one = firstName.split(" ");
		String[] two = lastName.split(" ");
		if (one.length > 1 || two.length > 1) {
			result += "Too many words entered in first or last name field.\n";
		}
		// handling with inserting space(s) in firstName or lastName text fields
		if (one.length == 0 || two.length == 0) {
			result += "Did you enter whitespace only in some field?";
		}
		return result;
	}

	/**
	 * Gets the result of the initial user input check from
	 * {@link #checkInput(String, String, String)} and returns specialized info if
	 * input is not correct, or calls appropriate method for updating phone base:
	 * either for adding or modifying existing contact. Adding new contact requires
	 * additional validation operation - typed phone number must be unique in the
	 * phone base. If it is not, contact adding process is rejected.
	 * 
	 * @param userChoice
	 *            flag pointing out if user wants to add or to change existing
	 *            contact
	 */
	public void proceedUserInput(int userChoice) {
		// checking out if contact is correctly typed
		String userInputCheck = checkInput(formView.getFirstText().getText(), formView.getLastText().getText(),
				formView.getNumberText().getText());
		// input is correct
		if (userInputCheck.equals("")) {
			int number = Integer.parseInt(formView.getNumberText().getText().trim());
			Person modifiedContact = new Person(formView.getFirstText().getText().trim(),
					formView.getLastText().getText().trim(), number);
			// adding new contact
			if (userChoice == -1) {
				if (isPhoneNumberUnique(number)) {
					dataOperations.addToList(modifiedContact);
					formView.getNewWindow().close();
					appInfo = grid.getAppInfo();
					appInfo[0].setText("Contact added.");
				} else {
					formView.getFormAppInfo().setText(
							"Phone number " + number + " already exists in the phonebase.\nYou cannot use it again.");
				}
				// modifying existing contact
			} else {
				dataOperations.updateList(formView.getChosenContact(), modifiedContact);
				formView.getNewWindow().close();
				appInfo = grid.getAppInfo();
				appInfo[0].setText("Contact modified.");
			}
			// input is not correct; sets appropriate info for displaying in the info label
		} else {
			formView.getFormAppInfo().setText(userInputCheck);
		}
	}

	/**
	 * Performs additional validation operation if user wants to add a new contact
	 * to the phone base. Typed phone number must be unique in the phone base. If it
	 * is not, contact adding process is rejected.
	 * 
	 * @param userChoice
	 *            flag pointing out if user wants to add or to change existing
	 *            contact
	 */
	public boolean isPhoneNumberUnique(int number) {
		Set<Person> x = dataSearch.searchByNumber(number);
		if (x.contains(new Person("No contact found.", ""))) {
			return true;
		} else {
			return false;
		}
	}
}