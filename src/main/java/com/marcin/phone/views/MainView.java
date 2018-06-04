package com.marcin.phone.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
import lombok.Getter;

/**
 * Class creates the main window for the application.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Component
public class MainView {

	private CenterGridView centerGrid;
	private Label[] appInfo;
	private RadioButton[] radioButtonArray;
	private MenuView menuView;
	@Getter
	private HBox hbox;
	@Getter
	private Hyperlink[] options;

	@Value("${stage.title}")
	private String mainStageTitle;

	@Autowired
	public MainView(CenterGridView centerGrid, MenuView menuView) {
		this.centerGrid = centerGrid;
		this.menuView = menuView;
	}

	/**
	 * Initializes and creates the content of the application main window.
	 * 
	 * @param primaryStage
	 *            main stage for the application
	 */
	public void initView(Stage primaryStage) {

		BorderPane root = new BorderPane();

		MenuBar menuBar = menuView.initMenuBar();
		root.setTop(menuBar);

		VBox vboxLeft = addLeftVBox();
		root.setLeft(vboxLeft);

		HBox hbox = centerGrid.getHBox();
		root.setBottom(hbox);

		appInfo = centerGrid.getAppInfo();
		root.setCenter(centerGrid);
		radioButtonArray = centerGrid.getRadioButtonArray();

		primaryStage.setTitle(mainStageTitle);
		Scene scene = new Scene(root, 800, 660);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Creates and adds the HBox at the bottom of the main window.
	 * 
	 * @return Hbox container laying at the bottom of the application
	 */
	public HBox addHBox() {
		hbox = new HBox();
		hbox.setPadding(new Insets(50, 12, 30, 12));
		hbox.setSpacing(25);
		hbox.setStyle("-fx-background-color: #336699;");
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}

	/**
	 * Creates and adds the VBox on the left side of the main window.
	 * 
	 * @return Vbox container laying at the left side of the application
	 */
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

		options = new Hyperlink[] { link1, link2, link3, link4 };

		for (int i = 0; i < 4; i++) {
			VBox.setMargin(options[i], new Insets(5, 0, 5, 0));
			options[i].setWrapText(true);
			options[i].setTextAlignment(TextAlignment.CENTER);
			options[i].setPrefWidth(110);
			vBox.getChildren().add(options[i]);
		}
		return vBox;
	}
}