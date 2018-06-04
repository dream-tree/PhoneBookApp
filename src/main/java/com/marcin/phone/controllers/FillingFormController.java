package com.marcin.phone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.marcin.phone.data.InputValidator;
import com.marcin.phone.views.FillingFormView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller for the application Filling Form window, where user can add a new
 * or modify an existing contact. Mechanism does not allow to save more than one
 * contact with the same phone number. In this way the phone number is the kind
 * of "unique id" in the phone base. There are no addition restrictions on first
 * and last name i.j., there might be an arbitrary number of contacts whose
 * first and last names are the same.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Controller
public class FillingFormController {

	private FillingFormView formView;
	private InputValidator validator;

	@Autowired
	public FillingFormController(FillingFormView formView, InputValidator validator) {
		this.formView = formView;
		this.validator = validator;
	}

	/**
	 * Initializes the controller for the application main window i.e., controller
	 * for Save button and controller for Cancel button.
	 */
	public void initController() {
		/**
		 * Setting an action for the Save button (mouse).
		 */
		formView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// clearing previous info displayed in the application info field (label)
				formView.getFormAppInfo().setText("");
				// flag checking if user wants to add or change existing contact:
				// gets existing phone number if changeButton was pressed or -1 if addButton was
				// pressed
				int userChoice = formView.getChosenContact().getNumber();
				if (isFormFilled()) {
					validator.proceedUserInput(userChoice);
				} else {
					formView.getFormAppInfo().setText("You haven't fill all fields.");
				}
			}
		});

		/**
		 * Setting an action for pressing Enter key while Save button is active. 
		 * Enter key is the default active button set by submit.setDefaultButton(true).
		 */
		formView.getSaveButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.SPACE) {
					// clearing previous info displayed in info label
					formView.getFormAppInfo().setText("");
					// flag checking if user wants to add or change existing contact:
					// gets existing phone number if changeButton was pressed or -1 if addButton was
					// pressed
					int userChoice = formView.getChosenContact().getNumber();
					if (isFormFilled()) {
						validator.proceedUserInput(userChoice);
					} else {
						formView.getFormAppInfo().setText("You haven't fill all fields.");
					}
					keyEvent.consume();
				}
			}
		});

		/**
		 * Setting an action for the Cancel button (mouse).
		 */
		formView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {
				formView.getNewWindow().close();
			}
		});

		/**
		 * Setting an action for pressing Enter key while Cancel button is active.
		 * Enter key is the default active button set by submit.setDefaultButton(true).
		 */
		formView.getCancelButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent event) {
				formView.getNewWindow().close();
			}
		});
	}

	/**
	 * Checks if all text fields are filled by the user.
	 * 
	 * @return true if all fields are filled by user
	 */
	public boolean isFormFilled() {
		if (formView.getFirstText().getText() != null && !formView.getFirstText().getText().isEmpty()
				&& formView.getLastText().getText() != null && !formView.getLastText().getText().isEmpty()
				&& formView.getNumberText().getText() != null && !formView.getNumberText().getText().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}