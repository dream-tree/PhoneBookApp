package com.marcin.phone.controllers;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.marcin.phone.StartApplication;
import com.marcin.phone.data.DataSearch;
import com.marcin.phone.model.Person;
import com.marcin.phone.repository.DataOperations;
import com.marcin.phone.views.CenterGridView;
import com.marcin.phone.views.FillingFormView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Controller
public class CenterGridController {
	
	private DataOperations dataOperations;
	private CenterGridView grid;
	private FillingFormView formView;
	private ToggleGroup radioButtonGroup;
	private RadioButton[] radioButtonArray;
	private Person[] obtained = new Person[1];
	private Label[] appInfo;
	private FillingFormController fillingFormController;
	private DataSearch dataSearch;
	
	@Autowired
	public CenterGridController(DataOperations model, CenterGridView grid, 
			FillingFormView formView, FillingFormController fillingFormController, DataSearch dataSearch) {
		this.dataOperations = dataOperations;
		this.grid = grid;
		this.formView = formView;
		this.fillingFormController = fillingFormController;
		this.dataSearch = dataSearch;
	}
	
	public void initController() {
		appInfo = grid.getAppInfo();
		radioButtonArray = grid.getRadioButtonArray();
		radioButtonGroup = grid.getRadioButtonGroup();
	    
		/**
		 * setting an action for the Submit button (mouse)
		 */
		grid.getSubmitButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent e) {
	    		// clearing previous "searches" (all Label[] entries and RadioButton[] buttons)
				clearAppInfo(appInfo);
		        if((grid.getSearchBar().getText() != null && !grid.getSearchBar().getText().isEmpty())) {    // !!! important stub
		        	processUserInput();        	
		        } else {
		        	appInfo[0].setText("You have not typed anything.");
		        }
			}
		});
		
		/**
	     * setting an action for pressing Enter key while Submit button is active
	     * Enter key is default active button set by submit.setDefaultButton(true);
		 */
		grid.getSubmitButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
		        if(keyEvent.getCode() == KeyCode.ENTER) {
		    		// clearing previous "searches" (all Label[] entries and RadioButton[] buttons)
		        	clearAppInfo(appInfo);
		            if ((grid.getSearchBar().getText() != null && !grid.getSearchBar().getText().isEmpty())) {
		            	processUserInput();
		            } else {
		            	appInfo[0].setText("You have not typed anything.");
		            }
		            keyEvent.consume();
		        }
			}		
		});
		
	    /**
	     * setting an action for the Clear button (mouse)
	     */		
		grid.getClearButton().setOnAction(new EventHandler<ActionEvent>() {		
			 @Override
		     public void handle(ActionEvent e) {
				grid.getSearchBar().clear();
				// clearing Person[] obtained (waiting for new user action)
				initRadioButtonAgain();
				clearAppInfo(appInfo);
		     }					
		});
		
		/**
	     * setting an action for pressing Enter key while Clear button is active
	     * default active button is Enter key set by submit.setDefaultButton(true); 
		 */
		grid.getClearButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
		        if(keyEvent.getCode() == KeyCode.ENTER) {
		        	grid.getSearchBar().clear();
					// clearing Person[] obtained (waiting for new user action)
					initRadioButtonAgain();
					clearAppInfo(appInfo);
		            keyEvent.consume();
		        }
			}
			
		});	
			 	
		/**
	     * setting an action for pressing Change Button;
	     * if no toggle is selected, pops up a new window with text: search and select a contact first else
	     * if any toggle is selected, creates a new pop-up window (a form to modify or add new data)
		 */
		grid.getChangeButton().setOnAction(x -> {
			if(obtained[0]==null) {
				showContactNotSelectedAlertDialog();
			}
			else {		    	
				// it passes selected Person to pop up window constructing FillingForm object (controller/view) 
				// to keep in memory chosen contact by user
				formView.createGrid(obtained[0]);	
				System.out.println("CenterGridController #1: " + formView.getChosenContact().getNumber());
				fillingFormController.initController();
			}
		}); 
		
		grid.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {	
				// initializing 'obtained' with "no person", so no info from radioButtonGroup is passed to FillingFormController
				formView.createGrid(new Person("", "", -1));		
				fillingFormController.initController();
			}		
		});	
				
		/**
		 * deletes contact selected by user
		 */	
		grid.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(obtained[0]==null) {
					showContactNotSelectedAlertDialog();
				}
				else {
					if(isToDeleteAlertDialog()) {
						dataOperations.removeFromList(obtained[0]);
						clearAppInfo(appInfo);
						appInfo[0].setText("Contact was deleted.");
					};

				}				
			}
		});		
	}
	
	/**
	 * Initializes searching process in DataManipulation component
	 * and proceeds with the incoming results.
	 */	
	public void processUserInput() {
		Set<Person> foundContacts = dataSearch.initSearch(grid.getSearchBar().getText());
	   // removing "No contact found.." residing in DataSearch' Set<Person> foundContacts from extra search of phone number uniqueness
		foundContacts.remove(new Person("No contact found.", ""));
    	if(foundContacts.size()==0) {
    		appInfo[0].setText("Nothing was found.");
    	} else if(foundContacts.contains(new Person("Incorrect input (too many words).", "", -1))) {
    		appInfo[0].setText("Incorrect input (too many words).");
    	} else {
    		if(foundContacts.size()>9) {
    			initRadioButtonAgain();
    			appInfo[0].setText("More than 9 contacts found. Showing first 9 contacts:");
    			int i = 1;
    			for(Iterator<Person> it = foundContacts.iterator(); i< 10; it.hasNext()) {	
    				Person personFound = it.next();
    				appInfo[i].setText(personFound.toString());
    				radioButtonArray[i].setVisible(true);
    				radioButtonArray[i].setUserData(personFound); // binds selected toggle with 'object' in given Label field
    				i++;
    			}
    		} else {
    			initRadioButtonAgain();
    			appInfo[0].setText("Search results: ");
    			int i = 1;
    			for(Iterator<Person> it = foundContacts.iterator(); it.hasNext();) {	
    				Person personFound = it.next();
    				appInfo[i].setText(personFound.toString());
    				radioButtonArray[i].setVisible(true);
    				radioButtonArray[i].setUserData(personFound); 
    				i++;
    			}
    		}
    	}	
    	// without clear(), new searches are added to foundContacts set and previous searches are also displayed
    	foundContacts.clear();    
	}
	
	
	/**
	 * deselects all radiobutton toggles after "submit" button is pressed (important if it is pressed more than once);
	 * clears 'Person[] obtained' member variable waiting for new user action 
	 * (this task must be done in separate method, cannot be done in initController() - if user changes more than 1 contact, 
	 * "old" info is saved in Person[] obtained and cannot be changed then)
	 */	
	public void initRadioButtonAgain() {
		radioButtonGroup.selectToggle(null);
		obtained = new Person[1];
		radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		    public void changed(ObservableValue<? extends Toggle> ov,Toggle old_toggle, Toggle new_toggle) {
		            if(radioButtonGroup.getSelectedToggle() != null) {
		            	@SuppressWarnings("unused")
						final Person obt = obtained[0] = (Person) radioButtonGroup.getSelectedToggle().getUserData();
		            }                
		        }
		});
	}
	
	public void clearAppInfo(Label[] appInfo) {
    	for(int i = 0; i < 11; i++) {
    		appInfo[i].setText("");	
    		radioButtonArray[i].setVisible(false);
    	}
	}
	
	public void showContactNotSelectedAlertDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setX(StartApplication.getPrimaryStage().getX() + 250);
		alert.setY(StartApplication.getPrimaryStage().getY() + 270);
        alert.setTitle("PhoneBook app message");	 
        alert.setHeaderText(null);
        alert.setContentText("Search and select contact first!");	 
        alert.showAndWait();
	}
	
	public boolean isToDeleteAlertDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setX(StartApplication.getPrimaryStage().getX() + 250);
		alert.setY(StartApplication.getPrimaryStage().getY() + 270);
        alert.setTitle("PhoneBook app message");	 
        alert.setHeaderText(null);
        alert.setContentText("Do you really want to delete selected contact?");	 
       
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            // user chose CANCEL or closed the alert dialog
        	return false;
        }        
	}
}