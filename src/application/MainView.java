package application;

import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView extends Application {
	
	private DataModel model = new DataModel();
	private CenterGridView centerGrid;
	private Label[] appInfo;
	private RadioButton[] radioButtonArray;
	private static Stage primaryStage;
	private static HBox hbox;
	
	@Override
	public void start(Stage primaryStage) {
			MainView.primaryStage = primaryStage;
		
			BorderPane root = new BorderPane();
			    
		    MenuBar menuBar = addMenuBar();
		    root.setTop(menuBar);
		    		   		    
		    VBox vboxLeft = addLeftVBox();
		    root.setLeft(vboxLeft);
		    
		    VBox vboxRight = addRightVBox();
		    root.setRight(vboxRight);
		    
		    HBox hbox = addHBox();
		    root.setBottom(hbox);
		    
		    centerGrid = new CenterGridView(model);
		    appInfo = centerGrid.getAppInfo();
		    root.setCenter(centerGrid);
		    radioButtonArray = centerGrid.getRadioButtonArray();		    
		    
			CenterGridController controller = new CenterGridController(model, centerGrid);
			controller.initController();
		    
		    primaryStage.setTitle("PhoneBook ver. 2.0");	
			Scene scene = new Scene(root, 900, 700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
	}
		
	public MenuBar addMenuBar() {		
		MenuBar menuBar = new MenuBar();
		
		final Menu menuFile = new Menu("File");
		final Menu menuOptions = new Menu("Options");
		final Menu menuAbout = new Menu("About");
		
		MenuItem menuFileItem1 = new MenuItem("Open");
		menuFileItem1.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		        System.out.println("Opening Database Connection...");
		    }
		});		
		
		MenuItem menuFileItem2 = new MenuItem("Edit");
		menuFileItem2.setOnAction(t -> System.out.println("unavailable"));		
		MenuItem menuFileItem3 = new MenuItem("Exit");
		menuFileItem3.setOnAction(t -> System.exit(0));		
		menuFile.getItems().addAll(menuFileItem1, menuFileItem2, new SeparatorMenuItem(), menuFileItem3);			
				
	//	menuFileItem1.setGraphic(new ImageView(new Image("flower.png")));

		menuBar.getMenus().addAll(menuFile, menuOptions, menuAbout);	
		
		return menuBar;
	}
	
	public HBox addHBox() {
	    hbox = new HBox();
	    hbox.setPadding(new Insets(30, 12, 30, 12));
	    hbox.setSpacing(25);
	    hbox.setStyle("-fx-background-color: #336699;");

hbox.setAlignment(Pos.CENTER);
	    
	    return hbox;
	}
		
	public VBox addRightVBox() {
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10));
	    vbox.setSpacing(8);
	      
	    Text title = new Text("Recent searches:");
	    title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	    vbox.getChildren().add(title);

	    Hyperlink options[] = new Hyperlink[] {
	        new Hyperlink("Extras"),
	        new Hyperlink("Super"),
	        new Hyperlink("Delicious"),
	        new Hyperlink("Marvelous")};

	    for (int i=0; i<4; i++) {
	        VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
	        vbox.getChildren().add(options[i]);
	    }
	    return vbox;
	}
	
	public VBox addLeftVBox() {
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10));
	    vbox.setSpacing(8);

	    Text title = new Text("Miscellaneous");
	    title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	    vbox.getChildren().add(title);

	    Hyperlink options[] = new Hyperlink[] {
		      new Hyperlink("All contacts"),
		      new Hyperlink("First names"),
		      new Hyperlink("Last Names"),
		      new Hyperlink("Numbers"),
		      new Hyperlink("Recent searches")
		};
	       
	    options[0].setOnAction(t -> {
	    	clearAppInfo(appInfo);
	    	Set<Person> allContacts = model.getBaseSearching().showAllBase();
	    	int i = 0;
	    	for(Person x : allContacts) {
	    		appInfo[i].setText(x.toString());
	    		i++;
	    	}	
	    	model.getBaseSearching().getFoundContacts().clear();
	    });
	    
	    options[1].setOnAction(t -> {
	    	clearAppInfo(appInfo);
	    	Set<Person> allContacts = model.getBaseSearching().showAllBase();
	    	int i = 0;
	    	for(Person x : allContacts) {
	    		appInfo[i].setText("First name: " + x.firstName);
	    		i++;
	    	}	
	    	model.getBaseSearching().getFoundContacts().clear();
	    });
	    
	    options[2].setOnAction(t -> {
	    	clearAppInfo(appInfo);
	    	Set<Person> allContacts = model.getBaseSearching().showAllBase();
	    	int i = 0;
	    	for(Person x : allContacts) {
	    		appInfo[i].setText("Last name: " + x.lastName);
	    		i++;
	    	}	
	    	model.getBaseSearching().getFoundContacts().clear();
	    });
	    
	    options[3].setOnAction(t -> {
	    	clearAppInfo(appInfo);
	    	Set<Person> allContacts = model.getBaseSearching().showAllBase();
	    	int i = 0;
	    	for(Person x : allContacts) {
	    		appInfo[i].setText("Phone: " + x.number);
	    		i++;
	    	}	
	    	model.getBaseSearching().getFoundContacts().clear();
	    });
	    
	    options[4].setOnAction(t -> {
	    	clearAppInfo(appInfo);
	    	Set<Person> allContacts = model.getBaseSearching().showAllBase();
	    	int i = 0;
	    	for(Person x : allContacts) {
	    		appInfo[i].setText("not implemented yet");
	    		i++;
	    	}
	    	model.getBaseSearching().getFoundContacts().clear();
	    });
	    
	    for (int i=0; i<5; i++) {
	        VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
	        vbox.getChildren().add(options[i]);
	    }
	    return vbox;
	}
	
	public void clearAppInfo(Label[] appInfo) {
    	for(int i = 0; i < 10; i++) {
    		appInfo[i].setText("");	
    	}
	}
	
	/**
	 * Allows binding secondary stage with primary stage in Fillform class.
	 * @return the primaryStage
	 */
	public static Stage getPrimaryStage() {
		return MainView.primaryStage;
	}
	
	/**
	 * @return the hbox
	 */
	public static HBox getHbox() {
		return hbox;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
