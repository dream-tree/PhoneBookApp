package com.marcin.phone.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcin.phone.data.Wallpaper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import lombok.Getter;

/**
 * Class creates the GridPane container used as a center part of the BorderPane 
 * constructing the application main window (MainView).
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Getter
@Component
public class CenterGridView extends GridPane {

	private Label[] appInfo;
	private Button submitButton;
	private Button clearButton;
	private Button addButton;
	private Button changeButton;
	private Button deleteButton;
	private TextField searchBar;
	private RadioButton[] radioButtonArray;
	private ToggleGroup radioButtonGroup;
	private HBox hBox;
	private Wallpaper wallpaper;
	private ImageView topImageView;

	@Autowired
	public CenterGridView(Wallpaper wallpaper) {
		radioButtonGroup = new ToggleGroup();
		radioButtonArray = new RadioButton[11];
		appInfo = new Label[11];
		topImageView = wallpaper.setWallpaper();

		// defining the MainView bottom HBox
		// moved here for better cooperation
		hBox = new HBox();
		hBox.setPadding(new Insets(30, 12, 30, 12));
		hBox.setSpacing(25);
		hBox.setStyle("-fx-background-color: #336699;");
		hBox.setAlignment(Pos.CENTER);

		addButton = new Button("Add new contact");
		addButton.setPrefSize(200, 40);
		changeButton = new Button("Change contact info");
		changeButton.setPrefSize(200, 40);
		deleteButton = new Button("Delete contact");
		deleteButton.setPrefSize(200, 40);
		hBox.getChildren().addAll(addButton, changeButton, deleteButton);

		// defining search output section; maximum output is 11 lines:
		// 1 line for potential info and 10 (or 11) lines for search results
		for (int i = 0; i < 11; i++) {
			RadioButton rb = new RadioButton();
			radioButtonArray[i] = rb;
			rb.setToggleGroup(radioButtonGroup);
			rb.setVisible(false);
			GridPane.setConstraints(rb, 0, i + 5);
			this.getChildren().add(rb);

			appInfo[i] = new Label();
			appInfo[i].setMaxWidth(450);
			appInfo[i].setFont(Font.font("TAHOMA", FontPosture.ITALIC, 14));
			GridPane.setConstraints(appInfo[i], 1, i + 5);
			GridPane.setColumnSpan(appInfo[i], 5);
			this.getChildren().add(appInfo[i]);
			// this.setGridLinesVisible(true);
		}
		createGrid();
	}

	/**
	 * Creates the content of the application main window.
	 */
	public void createGrid() {
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setAlignment(Pos.TOP_CENTER);
		this.setVgap(5);
		this.setHgap(5);

		// defining the top image
		GridPane.setConstraints(topImageView, 0, 0, 5, 1);
		this.getChildren().addAll(topImageView);

		// defining the searching bar text field
		searchBar = new TextField();
		searchBar.setPromptText("Enter your query..");
		searchBar.setMinWidth(190);
		searchBar.getText();

		searchBar.setAlignment(Pos.CENTER);
		GridPane.setConstraints(searchBar, 0, 1, 3, 1);
		this.getChildren().add(searchBar);

		// defining the Submit button
		submitButton = new Button("Submit");
		submitButton.setDefaultButton(true);
		submitButton.setMinWidth(85);
		GridPane.setConstraints(submitButton, 3, 1);
		this.getChildren().add(submitButton);

		// defining the Clear button
		clearButton = new Button("Clear");
		clearButton.setMinWidth(85);
		GridPane.setConstraints(clearButton, 4, 1);
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
}