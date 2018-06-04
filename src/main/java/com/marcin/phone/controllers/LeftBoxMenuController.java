package com.marcin.phone.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.marcin.phone.data.DataSearch;
import com.marcin.phone.model.Person;
import com.marcin.phone.views.CenterGridView;
import com.marcin.phone.views.MainView;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

/**
 * Controller for the application left Vbox menu.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Controller
public class LeftBoxMenuController {

	private MainView mainview;
	private DataSearch dataSearch;
	private CenterGridView grid;
	private Label[] appInfo;
	private RadioButton[] radioButtonArray;
	@Autowired
	private PersonComparator phonePersonComparator;
	@Autowired
	private FirstNamePersonComparator firstNamePersonComparator;
	@Autowired
	private LastNamePersonComparator lastNamePersonComparator;

	@Autowired
	public LeftBoxMenuController(MainView mainview, CenterGridView grid, DataSearch dataSearch) {
		this.mainview = mainview;
		this.grid = grid;
		this.dataSearch = dataSearch;
	}

	/**
	 * Initializes controller for left VBox menu.
	 */
	public void initController() {
		appInfo = grid.getAppInfo();
		radioButtonArray = grid.getRadioButtonArray();

		// showing contacts sorted by first name
		mainview.getOptions()[0].setOnAction(t -> {
			showSortedContacts("firstNameOrder");
		});

		// showing contacts sorted by last name
		mainview.getOptions()[1].setOnAction(t -> {
			showSortedContacts("lastNameOrder");
		});

		// showing contacts sorted by phone number
		mainview.getOptions()[2].setOnAction(t -> {
			showSortedContacts("phoneNumberOrder");
		});

		// showing random contacts
		mainview.getOptions()[3].setOnAction(t -> {
			showRandomContacts();
		});
	}

	/**
	 * Clears app info displayed after taking new action by user.
	 * 
	 * @param appInfo
	 *            app info to be cleared
	 */
	public void clearAppInfo(Label[] appInfo) {
		for (int i = 0; i < 11; i++) {
			appInfo[i].setText("");
			radioButtonArray[i].setVisible(false);
		}
	}

	/**
	 * Displays contacts in a way chosen by user:
	 * <ul>
	 * <li>sorted by first name or</li>
	 * <li>sorted by last name or</li>
	 * <li>sorted by phone number.</li>
	 * <ul>
	 * 
	 * @param sortingIndentifier
	 *            identifier denoting the chosen way of sorting the phone base
	 *            contacts for the purpose of displaying in the application main
	 *            window
	 */
	public void showSortedContacts(String sortingIndentifier) {
		clearAppInfo(appInfo);
		Set<Person> allContacts = dataSearch.showAllBase();

		Person[] sortedContacts = allContacts.stream()
				.sorted(sortingIndentifier.equals("firstNameOrder") ? firstNamePersonComparator
						: sortingIndentifier.equals("lastNameOrder") ? lastNamePersonComparator : phonePersonComparator)
				.toArray(Person[]::new);

		int i = 0;
		if (sortedContacts.length > 10) {
			appInfo[0].setText("Showing up to 10 first contacts:");
			i++;
		}
		for (Person x : sortedContacts) {
			if (x.getFirstName().equals("No contact found."))
				continue;
			appInfo[i].setText(x.toString());
			i++;
			if (i > 10)
				break;
		}
		dataSearch.getFoundContacts().clear();
	}

	/**
	 * Displays random contacts.
	 */
	public void showRandomContacts() {
		clearAppInfo(appInfo);
		Set<Person> allContacts = dataSearch.showAllBase();

		Person[] converted = allContacts.stream().toArray(Person[]::new);
		int j = 0;
		if (converted.length > 10) {
			appInfo[0].setText("Showing up to 10 random contacts:");
			j++;
		}
		for (int k = j; k < converted.length && k < 11; k++) {
			int index = (int) (Math.random() * converted.length - 1);
			appInfo[k].setText(converted[index].toString());
		}
		dataSearch.getFoundContacts().clear();
	}
}