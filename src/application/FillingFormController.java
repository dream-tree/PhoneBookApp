package application;

import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FillingFormController {
	
	DataModel model;
	FillingFormView formView;
	
	public FillingFormController(DataModel model, FillingFormView formView) {
		this.model = model;
		this.formView = formView;
		initController();
	}

	public void initController() {
		
		formView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent arg0) {
				// clearing previous info displayed in info label
				formView.getInfo().setText("");
				// flag checking if user wants to add or or change existing contact:
				// phone number if changeButton was pressed or -1 if addButton was pressed
				int userChoice = formView.getChosenContact().getNumber();
				if(isFormFilled()) {
					checkAndSaveInput(userChoice);				
				} else {
					formView.getInfo().setText("You haven't fill all fields.");
				}			
			}
		});
		
		formView.getSaveButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.SPACE) {
					// clearing previous info displayed in info label
					formView.getInfo().setText("");
					// flag checking if user wants to add or or change existing contact:
					// phone number if changeButton was pressed or -1 if addButton was pressed
					int userChoice = formView.getChosenContact().getNumber();
					if(isFormFilled()) {
						checkAndSaveInput(userChoice);
					} else {
						formView.getInfo().setText("You haven't fill all fields.");
					}
					keyEvent.consume();
				}			
			}
		});

		formView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {
				formView.getNewWindow().close();
			}
		});
		
		formView.getCancelButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent event) {
				formView.getNewWindow().close();
			}
		});	
	}
	
	/**
	 * checking if all text fields are filled by user
	 * @return true if all fields are filled by user
	 */	
	public boolean isFormFilled() {
		if(formView.getFirstText().getText()!=null && !formView.getFirstText().getText().isEmpty()
				&& formView.getLastText().getText()!=null && !formView.getLastText().getText().isEmpty()
				&& formView.getNumberText().getText()!=null && !formView.getNumberText().getText().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}	
	
	/**
	 * checks correctness of user input and forwards user changes to model to update contact list
	 * either to add new contact or to modify existing contact
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
					model.addToList(modifiedContact);
					formView.getNewWindow().close();
				} else {
					formView.getInfo().setText("Phone number " + number 
							+ " already exists in the phonebase.\nYou cannot use it again.");
				}	
			// updating existing contact
			} else {
				if(isPhoneNumberUnique(number)) {
					model.updateList(formView.getChosenContact(), modifiedContact);	
					formView.getNewWindow().close();
				} else {	
					formView.getInfo().setText("Phone number " + number 
							+ " already exists in the phonebase.\nYou cannot use it again.");
				}	
			}	
		} else {
			formView.getInfo().setText(userInputCheck);
		}
	}
	
	
	/**
	 * checking if user input is correct
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
		Set<Person> x = model.getBaseSearching().searchByNumber(number);
		if(x.contains(new Person("No contact found.", ""))) {
			return true;
		} else {	
			return false;			
		}		
	}
}
