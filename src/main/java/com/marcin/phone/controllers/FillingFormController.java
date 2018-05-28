package com.marcin.phone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.marcin.phone.data.InputValidator;
import com.marcin.phone.views.FillingFormView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Controller
public class FillingFormController {
	
	private FillingFormView formView;
	private InputValidator validator;
	
	@Autowired
	public FillingFormController(FillingFormView formView, InputValidator validator) {
		this.formView = formView;
		this.validator = validator;
	}

	public void initController() {		
		formView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				// clearing previous info displayed in the application info field (label)
				formView.getFormAppInfo().setText("");
				// flag checking if user wants to add or change existing contact:
				// gets existing phone number if changeButton was pressed or -1 if addButton was pressed
				int userChoice = formView.getChosenContact().getNumber();
				if(isFormFilled()) {
					validator.checkAndSaveInput(userChoice);				
				} else {
					formView.getFormAppInfo().setText("You haven't fill all fields.");
				}			
			}
		});
		
		formView.getSaveButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				if(keyEvent.getCode() == KeyCode.SPACE) {
					// clearing previous info displayed in info label
					formView.getFormAppInfo().setText("");
					// flag checking if user wants to add or change existing contact:
					// gets existing phone number if changeButton was pressed or -1 if addButton was pressed
					int userChoice = formView.getChosenContact().getNumber();
					if(isFormFilled()) {
						validator.checkAndSaveInput(userChoice);
					} else {
						formView.getFormAppInfo().setText("You haven't fill all fields.");
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
}
