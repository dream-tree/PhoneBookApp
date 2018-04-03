package application;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FillingFormView {
	
	private DataModel model;
	private Stage newWindow;
	private Label firstLabel;
	private Label lastLabel;
	private Label numberLabel;
	private Label formAppInfo;
	private TextField firstText;
	private TextField lastText;
	private TextField numberText;	
	private Button saveButton;
	private Button cancelButton;
	private Person chosenContact;
	
    public FillingFormView(DataModel model, Person chosenContact) {
    	this.model = model;
    	this.chosenContact = chosenContact;
    	createGrid(chosenContact);
    }
       
    /**
     * Creates new pop-up window to enable modifying or adding new data
     * @param chosenContactNumber phone number serving as a bridge between 'old' and 'new' data
     * @return new grid pane (form to modify or add new data)
     */
    
	public GridPane createGrid(Person chosenContact) { 
		
		GridPane grid = new GridPane();
		grid.setPrefWidth(600);
		grid.setPrefHeight(300);		
		grid.setPadding(new Insets(30, 10, 10, 10));
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setVgap(10);
		grid.setHgap(15);	

		firstLabel = new Label("First Name:");
		lastLabel = new Label("Last Name:");
		numberLabel = new Label("Phone Number:");
		firstText = new TextField(chosenContact.getFirstName());
		lastText = new TextField(chosenContact.getLastName());
		if(chosenContact.getNumber() == -1) {
			numberText = new TextField("");
		} else {
			numberText = new TextField(String.valueOf(chosenContact.getNumber()));	
		}	
		saveButton = new Button("Save");
		saveButton.setPrefWidth(85);
		saveButton.setDefaultButton(true);
		cancelButton = new Button("Cancel");
		cancelButton.setPrefWidth(85);
		formAppInfo = new Label();	
			
		grid.add(firstLabel, 0, 0);
		grid.add(lastLabel, 0, 1);		
		grid.add(numberLabel, 0, 2);	
		grid.add(firstText, 1, 0, 2, 1);
		grid.add(lastText, 1, 1, 2, 1);		
		grid.add(numberText, 1, 2, 2, 1);		
		grid.add(saveButton, 1, 4);
		grid.add(cancelButton, 2, 4);
		grid.add(formAppInfo, 0, 6, 9, 1);
		
        Scene secondScene = new Scene(grid);
      	 
        newWindow = new Stage();
        newWindow.setTitle("Phone Base Update Form");
        newWindow.setScene(secondScene);

        // specifies the modality for new window
        newWindow.initModality(Modality.WINDOW_MODAL);

        // specifies the owner Window (parent) for new window      
        newWindow.initOwner(MainView.getPrimaryStage());

        // set position of second window, related to primary window
        newWindow.setX(MainView.getPrimaryStage().getX() + 150);
        newWindow.setY(MainView.getPrimaryStage().getY() + 150);

        newWindow.show();
				
		return grid;
	}
	
	public void showDuplicateNumberAlertDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setX(MainView.getPrimaryStage().getX() + 250);
		alert.setY(MainView.getPrimaryStage().getY() + 270);
        alert.setTitle("PhoneBook app message");	 
        alert.setHeaderText(null);
        alert.setContentText("Phone number already exists in the phonebase.\n"
        		+ "You cannot assign it again.");	 
        alert.showAndWait();
	}
		

	/**
	 * @return the saveButton
	 */
	public Button getSaveButton() {
		return saveButton;
	}

	/**
	 * @return the cancelButton
	 */
	public Button getCancelButton() {
		return cancelButton;
	}

	/**
	 * @return the firstText
	 */
	public TextField getFirstText() {
		return firstText;
	}

	/**
	 * @return the lastText
	 */
	public TextField getLastText() {
		return lastText;
	}

	/**
	 * @return the numberText
	 */
	public TextField getNumberText() {
		return numberText;
	}

	/**
	 * @return the chosenContact
	 */
	public Person getChosenContact() {
		return chosenContact;
	}

	/**
	 * @return the newWindow
	 */
	public Stage getNewWindow() {
		return newWindow;
	}

	/**
	 * @return the info
	 */
	public Label getInfo() {
		return formAppInfo;
	}
}
