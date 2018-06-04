package com.marcin.phone.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcin.phone.StartApplication;
import com.marcin.phone.model.Person;
import com.marcin.phone.repository.DataOperations;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * Class creates additional application window - filling form,
 * allowing the user to enter new or modified contact data.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Getter
@Component
public class FillingFormView {

	private DataOperations model;
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

	@Autowired
	public FillingFormView(DataOperations model) {
		this.model = model;
	}

	/**
	 * Creates a new pop-up window to enable modifying or adding new contact data.
	 * 
	 * @param chosenContactNumber
	 *            phone number serving as a bridge between 'old' and 'new' data
	 * @return new grid pane (form to modify or add new data)
	 */
	public GridPane createGrid(Person chosenContact) {
		this.chosenContact = chosenContact;
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
		// checking out if the Add button was pressed, if so no phone number is shown in
		// the text field;
		// first and last name are empty strings, so the text fields for them contain no
		// text
		if (chosenContact.getNumber() == -1) {
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
		newWindow.setTitle("Phone Base Filling Form");
		newWindow.setScene(secondScene);

		// specifies the modality for new window
		newWindow.initModality(Modality.WINDOW_MODAL);

		// specifies the owner Window (parent) for new window
		newWindow.initOwner(StartApplication.getPrimaryStage());

		// set position of second window, related to primary window
		newWindow.setX(StartApplication.getPrimaryStage().getX() + 150);
		newWindow.setY(StartApplication.getPrimaryStage().getY() + 150);

		newWindow.show();

		return grid;
	}

	/**
	 * Notifies user that the entered phone number exists already in the phone base
	 * and can't be used again.
	 */
	public void showDuplicateNumberAlertDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setX(StartApplication.getPrimaryStage().getX() + 250);
		alert.setY(StartApplication.getPrimaryStage().getY() + 270);
		alert.setTitle("PhoneBook app message");
		alert.setHeaderText(null);
		alert.setContentText("Phone number already exists in the phonebase.\n" + "You cannot assign it again.");
		alert.showAndWait();
	}
}