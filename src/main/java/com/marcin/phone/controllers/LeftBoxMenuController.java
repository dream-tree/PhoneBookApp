package com.marcin.phone.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.marcin.phone.data.DataSearch;
import com.marcin.phone.model.Person;
import com.marcin.phone.repository.DataOperations;
import com.marcin.phone.views.CenterGridView;
import com.marcin.phone.views.MainView;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

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
	
	public void clearAppInfo(Label[] appInfo) {
    	for(int i = 0; i < 11; i++) {
    		appInfo[i].setText("");	
    		radioButtonArray[i].setVisible(false);
    	}
	}

	public void showSortedContacts(String sortingIndentifier) {
		clearAppInfo(appInfo);
    	Set<Person> allContacts = dataSearch.showAllBase();
 
    // 	Comparator<Person> firstNamePersonComparator = new FirstNamePersonComparator();
    	Person[] sortedContacts = allContacts
    			.stream()
    			.sorted(sortingIndentifier.equals("firstNameOrder") ? firstNamePersonComparator
    				  : sortingIndentifier.equals("lastNameOrder") ? lastNamePersonComparator
    				  : phonePersonComparator)
    			.toArray(Person[]::new);
    	
    	int i = 0;
    	if(sortedContacts.length > 10) {
    		appInfo[0].setText("Showing up to 10 first contacts:");
    		i++;
    	}     	
    	for(Person x : sortedContacts) {
    		appInfo[i].setText(x.toString());
    		i++;
    		if(i > 10) break;
    	}	
    	
    	dataSearch.getFoundContacts().clear();
	}
	
	public void showRandomContacts() {
    	clearAppInfo(appInfo);
    	Set<Person> allContacts = dataSearch.showAllBase();
    	Person[] converted = allContacts
    			.stream()
    			.toArray(Person[]::new);
    	int j = 0;
    	if(converted.length > 10) {
    		appInfo[0].setText("Showing up to 10 random contacts:");
    		j++;
    	}     	
    //	Random rand = new Random();
    	for(Person x : converted) {
    	//	int index = rand.nextInt(converted.length);
    		int index = (int) (Math.random()*converted.length-1);
    		appInfo[j].setText(converted[index].toString());
    		j++;
    		if(j > 10) break;
    	}	
    	dataSearch.getFoundContacts().clear();
	}
}