package com.marcin.phone.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcin.phone.repository.DataOperations;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@Component
public class MainView {
	
	private DataOperations operations;
	private CenterGridView centerGrid;
	private Label[] appInfo;
	private RadioButton[] radioButtonArray;	
	private HBox hbox;
	private MenuView menuView;
	private Hyperlink[] options;
	
	@Autowired
	public MainView(CenterGridView centerGrid, MenuView menuView, DataOperations operations) {
		this.centerGrid = centerGrid;
		this.menuView = menuView;
		this.operations = operations;
	}
	
		
	public void initView(Stage primaryStage) {
			
		BorderPane root = new BorderPane();
			    
		MenuBar menuBar = menuView.initMenuBar();
		root.setTop(menuBar);
		    		   		    
		VBox vboxLeft = addLeftVBox();
		root.setLeft(vboxLeft);
		    
		VBox vboxRight = addRightVBox();
		root.setRight(vboxRight);
		    
		HBox hbox = centerGrid.getHBox();
		root.setBottom(hbox);
		    
		appInfo = centerGrid.getAppInfo();
		root.setCenter(centerGrid);
		radioButtonArray = centerGrid.getRadioButtonArray();		    
		    
		primaryStage.setTitle("PhoneBook ver. 2.0");	
		Scene scene = new Scene(root, 900, 630);
	//	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}	
	
	public HBox addHBox() {
		hbox = new HBox();
	    hbox.setPadding(new Insets(50, 12, 30, 12));
	    hbox.setSpacing(25);
	    hbox.setStyle("-fx-background-color: #336699;");
	    hbox.setAlignment(Pos.CENTER);	    
	    return hbox;
	}
		
	public VBox addRightVBox() {
	    VBox vBox = new VBox();
	    vBox.setPadding(new Insets(30, 50, 20, 0));
	    vBox.setSpacing(8);
	      
	    Text title = new Text("Extras:");
	    title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	    vBox.getChildren().add(title);

	    Hyperlink[] options = new Hyperlink[] {
	        new Hyperlink("Extra"),
	        new Hyperlink("Super"),
	        new Hyperlink("Delicious"),
	        new Hyperlink("Marvelous")};

	    for (int i=0; i<4; i++) {
	    	VBox.setMargin(options[i], new Insets(5, 0, 5, 0));
	    	vBox.getChildren().add(options[i]);
	    }
	    return vBox;
	}
	
	public VBox addLeftVBox() {
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(30, 20, 20, 30));
		vBox.setSpacing(8);

		Text title = new Text("Miscellaneous contacts views");
		title.setWrappingWidth(110);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		vBox.getChildren().add(title);

		Hyperlink link1 = new Hyperlink("Sorted by first name");
		Hyperlink link2 = new Hyperlink("Sorted by last name");
		Hyperlink link3 = new Hyperlink("Sorted by phone number");
		Hyperlink link4 = new Hyperlink("Random contacts");
		
		options = new Hyperlink[] {link1, link2, link3, link4};
		
		for(int i = 0; i < 4; i++) {
			VBox.setMargin(options[i], new Insets(5, 0, 5, 0));
			options[i].setWrapText(true);
			options[i].setTextAlignment(TextAlignment.CENTER);
			options[i].setPrefWidth(110);	
			vBox.getChildren().add(options[i]);
	    }	
		return vBox;
	}
	
	
	/**
	 * @return the hbox
	 */
	public HBox getHbox() {
		return hbox;
	}
	
	public Hyperlink[] getOptions() {
		return options;
	}
	
}
