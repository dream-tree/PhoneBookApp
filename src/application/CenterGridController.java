package application;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

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

public class CenterGridController {
	
	private DataModel model;
	private CenterGridView grid;
	private ToggleGroup radioButtonGroup;
	private RadioButton[] radioButtonArray;
	private Person[] obtained = new Person[1];
	private Label[] appInfo;
	
	public CenterGridController(DataModel model, CenterGridView grid) {
		this.model = model;
		this.grid = grid;
	}
	
	public void initController() {
		appInfo = grid.getAppInfo();
		radioButtonArray = grid.getRadioButtonArray();
		radioButtonGroup = grid.getRadioButtonGroup();
	    
		/**
		 * setting an action for the Submit button (mouse)
		 */
		grid.getSubmit().setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent e) {
	    		// clearing previous "searches" (all Label[] entries and RadioButton[] buttons)
				clearAppInfo(appInfo);
		        if((grid.getSearchBar().getText() != null && !grid.getSearchBar().getText().isEmpty())) {    // !!! important stub
		        	processInput();        	
		        } else {
		        	appInfo[0].setText("You have not typed anything.");
		        }
			}
		});
		
		/**
	     * setting an action for pressing Enter key while Submit button is active
	     * Enter key is default active button set by submit.setDefaultButton(true);
		 */
		grid.getSubmit().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
		        if(keyEvent.getCode() == KeyCode.ENTER) {
		    		// clearing previous "searches" (all Label[] entries and RadioButton[] buttons)
		        	clearAppInfo(appInfo);
		            if ((grid.getSearchBar().getText() != null && !grid.getSearchBar().getText().isEmpty())) {
		            	processInput();
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
		grid.getClear().setOnAction(new EventHandler<ActionEvent>() {		
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
		grid.getClear().setOnKeyPressed(new EventHandler<KeyEvent>() {
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
				FillingFormView formView = new FillingFormView(model, obtained[0]);	
				new FillingFormController(model, formView);
			}
		}); 
		
		grid.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {	
				// initializing 'obtained' with "no person", so no info from radioButtonGroup is passed to FillingFormController
				FillingFormView formView = new FillingFormView(model, new Person("", "", -1));
				new FillingFormController(model, formView);
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
						model.removeFromList(obtained[0]);
						clearAppInfo(appInfo);
						appInfo[0].setText("Contact was deleted.");
					};

				}				
			}
		});		
	}
	
	/**
	 * initializes searching process in Model component
	 * and proceeds with incoming results
	 */	
	public void processInput() {
		Set<Person> foundContacts = model.getBaseSearching().initSearch(grid.getSearchBar().getText());
	   // removing "No contact found.." residing in DataSearch' Set<Person> foundContacts from extra search of phone number uniqueness
		foundContacts.remove(new Person("No contact found.", ""));
    	if(foundContacts.size()==0) {
    		appInfo[0].setText("Nothing was found.");
    	} else if(foundContacts.contains(new Person("Incorrect input (too many words).", "", -1))) {
    		appInfo[0].setText("Incorrect input (too many words).");
    	} else {
    		initRadioButtonAgain();
    		appInfo[0].setText("Search results: ");
    		int i = 1;
        	for(Iterator<Person> it = foundContacts.iterator(); it.hasNext();) {	
        		Person personFound = it.next();
        		appInfo[i].setText(personFound.toString());
        		radioButtonArray[i].setVisible(true);
        		radioButtonArray[i].setUserData(personFound); // binds selected toggle with 'object' in given Label field
        		i++;
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
		            	final Person obt = obtained[0] = (Person) radioButtonGroup.getSelectedToggle().getUserData();
		            }                
		        }
		});
	}
	
	public void clearAppInfo(Label[] appInfo) {
    	for(int i = 0; i < 10; i++) {
    		appInfo[i].setText("");	
    		radioButtonArray[i].setVisible(false);
    	}
	}
	
	public void showContactNotSelectedAlertDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setX(MainView.getPrimaryStage().getX() + 250);
		alert.setY(MainView.getPrimaryStage().getY() + 270);
        alert.setTitle("PhoneBook app message");	 
        alert.setHeaderText(null);
        alert.setContentText("Search and select contact first!");	 
        alert.showAndWait();
	}
	
	public boolean isToDeleteAlertDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setX(MainView.getPrimaryStage().getX() + 250);
		alert.setY(MainView.getPrimaryStage().getY() + 270);
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
