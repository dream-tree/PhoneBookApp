package com.marcin.phone.views;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.marcin.phone.StartApplication;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Class creates menu bar for the application main view.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Getter
@Component
public class MenuView {

	@Getter(AccessLevel.NONE)
	private Menu menuFile;
	@Getter(AccessLevel.NONE)
	private Menu menuAbout;
	@Getter(AccessLevel.NONE)
	private Menu menuExit;
	private MenuItem menuFileItem1;
	private MenuItem menuFileItem2;
	private MenuItem menuFileItem3;
	private MenuItem menuAboutItem;
	private MenuItem menuExitItem;
	@Getter(AccessLevel.NONE)
	private MenuBar menuBar;
	
	@Value("${menuView.userInfo}")
	String userInfo;
	@Value("${menuView.aboutInfo}")
	String aboutInfo;
	
	public MenuView() {	
	}
	
	/**
	 * Creates MenuBar for the application main view.
	 * @return menu bar in the main window of the application
	 */
	public MenuBar initMenuBar() {
		menuBar = new MenuBar();
		
		menuFile = new Menu("File");
		menuAbout = new Menu("About");
		menuExit = new Menu("Exit");
		
		menuFileItem1 = new MenuItem("User info");
		menuFileItem2 = new MenuItem("Options");		
		menuFileItem3 = new MenuItem("Exit");
		
		menuAboutItem = new MenuItem("About");
		
		menuExitItem = new MenuItem("Exit");
		
		menuFile.getItems().addAll(menuFileItem1, menuFileItem2, new SeparatorMenuItem(), menuFileItem3);	
		menuAbout.getItems().addAll(menuAboutItem);
		menuExit.getItems().addAll(menuExitItem);

		menuBar.setPrefSize(800.0, 30.0);
		menuBar.getMenus().addAll(menuFile, menuAbout, menuExit);	
	
		return menuBar;	
	}
	
	/**
	 * Shows the basic information about the application rules.
	 */
	public void userInfoAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.getDialogPane().setPrefSize(750, 500);
		alert.getDialogPane().setPadding(new Insets(0, 30, 0, 0));
		alert.setX(StartApplication.getPrimaryStage().getX() + 30);
		alert.setY(StartApplication.getPrimaryStage().getY() + 75);
		alert.setTitle("---User info---");	 
		alert.setHeaderText(null);
		alert.setContentText(userInfo);	 
		alert.showAndWait();
	}

	/**
	 * Shows information about the author of the application.
	 */
	public void userAboutAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.getDialogPane().setPrefSize(450, 170);
		alert.setX(StartApplication.getPrimaryStage().getX() + 200);
		alert.setY(StartApplication.getPrimaryStage().getY() + 200);
		alert.setTitle("---About---");	 
		alert.setHeaderText(null);
		alert.setContentText(aboutInfo);	 
		alert.showAndWait();
	}
}