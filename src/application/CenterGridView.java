package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

/**
 * Class creates GridPane container used as a center part of BorderPane from MainView class.
 */

public class CenterGridView extends GridPane {

	private DataModel model;
	private Label[] appInfo;
	private Button submitButton;
	private Button clearButton;
	private Button addButton;
	private Button changeButton; 
	private Button deleteButton;
	private TextField searchBar;
	private RadioButton[] radioButtonArray;
	private ToggleGroup radioButtonGroup;
	// MainView bottom HBox details moved here
	private static HBox downHBox;
	
	public CenterGridView(DataModel model) {
		this.model = model;	
		radioButtonGroup = new ToggleGroup();
		radioButtonArray = new RadioButton[10];
		appInfo = new Label[10];	
		
		// defining MainView bottom HBox
		downHBox = MainView.getHbox();
	    addButton = new Button("Add new contact");   
	    addButton.setPrefSize(200, 40);	    
	    changeButton = new Button("Change contact info");  
	    changeButton.setPrefSize(200, 40);       
	    deleteButton = new Button("Delete contact");
	    deleteButton.setPrefSize(200, 40);	   
	    downHBox.getChildren().addAll(addButton, changeButton, deleteButton);
		
	    // defining search output section; maximum output is 10 lines
	    for(int i = 0; i < 10; i++) {
	    	RadioButton rb = new RadioButton();
	    	radioButtonArray[i] = rb;
	    	rb.setToggleGroup(radioButtonGroup);
	    	rb.setVisible(false);
	    	GridPane.setConstraints(rb, 0, i+5);
	    	this.getChildren().add(rb);
	    	
	    	appInfo[i] = new Label();
	    	appInfo[i].setMaxWidth(450); 
	    	appInfo[i].setFont(Font.font("TAHOMA", FontPosture.ITALIC, 14)); 
	    	GridPane.setConstraints(appInfo[i], 1, i+5);
	    	GridPane.setColumnSpan(appInfo[i], 3);
	    	this.getChildren().add(appInfo[i]); 	
	    }
		createGrid();
	}
	
	public void createGrid() {		
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setAlignment(Pos.TOP_CENTER);
		this.setVgap(5);
		this.setHgap(7);
//	    centerGrid.setGridLinesVisible(true);
		Rectangle rect0 = new Rectangle(25.0, 100.0);
	    Rectangle rect1 = new Rectangle(150.0, 100.0);
	    Rectangle rect2 = new Rectangle(150.0, 100.0);
	    Rectangle rect3 = new Rectangle(75.0, 100.0);
	    Rectangle rect4 = new Rectangle(75.0, 100.0);
	    rect0.setFill(Color.LIGHTGREY);
	    rect1.setFill(Color.LIGHTGREY);
	    rect2.setFill(Color.LIGHTGREY);
	    rect3.setFill(Color.LIGHTGREY);
	    rect4.setFill(Color.LIGHTGREY);
	    GridPane.setConstraints(rect0, 0, 0);
	    GridPane.setConstraints(rect1, 1, 0);
	    GridPane.setConstraints(rect2, 2, 0);
	    GridPane.setConstraints(rect3, 3, 0);
	    GridPane.setConstraints(rect4, 4, 0);
	    this.getChildren().addAll(rect0, rect1, rect2, rect3, rect4);
	    
	    // defining the searching bar text field
	    searchBar = new TextField();
	    searchBar.setPromptText("Enter your query..");
	    searchBar.setPrefColumnCount(15);   // maximum number of characters it can display at one time - doesn't work?
	    searchBar.getText();            // !!! important method: the text data entered by a user into the text fields can be obtained by the
	    							    // getText method of the TextInput class.
	    searchBar.setAlignment(Pos.CENTER);
	    GridPane.setConstraints(searchBar, 0, 1, 3, 1);    // used instance method add() before
	    this.getChildren().add(searchBar);
	    // defining the Submit button
	    submitButton = new Button("Submit");
	    submitButton.setDefaultButton(true);     // !!! important method: activates chosen button for keyboard pressing (with setOnKeyPressed())
	    submitButton.setMinWidth(75);
	    GridPane.setConstraints(submitButton, 3, 1);
	    this.getChildren().add(submitButton);
	    // defining the Clear button
	    clearButton = new Button("Clear");
	    clearButton.setMinWidth(75);
	    GridPane.setConstraints(clearButton, 4, 1);    //  used add() before
	    this.getChildren().add(clearButton);

	    // defining info presented under searchBar control
	    final Label searchInfo1 = new Label();	
	    searchInfo1.setMaxWidth(450);
	    searchInfo1.setMaxHeight(14);
	    searchInfo1.setFont(Font.font("ARIAL", FontPosture.ITALIC, 13));
	    searchInfo1.setAlignment(Pos.TOP_CENTER);
	    searchInfo1.setText("max 2 words or phone number (no spaces or hyphens)"); 
	    
	    GridPane.setConstraints(searchInfo1, 0, 2);
	    GridPane.setColumnSpan(searchInfo1, 3);
	    this.getChildren().add(searchInfo1);
	}

	/**
	 * @return the appInfo
	 */
	public Label[] getAppInfo() {
		return appInfo;
	}

	/**
	 * @return the submit
	 */
	public Button getSubmit() {
		return submitButton;
	}

	/**
	 * @return the clear
	 */
	public Button getClear() {
		return clearButton;
	}

	/**
	 * @return the searchBar
	 */
	public TextField getSearchBar() {
		return searchBar;
	}

	/**
	 * @return the radioButtonArray
	 */
	public RadioButton[] getRadioButtonArray() {
		return radioButtonArray;
	}

	/**
	 * @return the radioButtonGroup
	 */
	public ToggleGroup getRadioButtonGroup() {
		return radioButtonGroup;
	}

	/**
	 * @return the changeButton
	 */
	public Button getChangeButton() {
		return changeButton;
	}

	/**
	 * @return the addButton
	 */
	public Button getAddButton() {
		return addButton;
	}

	/**
	 * @return the deleteButton
	 */
	public Button getDeleteButton() {
		return deleteButton;
	}
}
