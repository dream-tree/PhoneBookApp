package com.marcin.phone;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.marcin.phone.controllers.CenterGridController;
import com.marcin.phone.controllers.LeftBoxMenuController;
import com.marcin.phone.controllers.MenuController;
import com.marcin.phone.views.MainView;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Starting point of the PhoneBookJavaFX application. 
 * It initializes the JavaFX platform start() method 
 * and constructs the primary Stage for the application.
 * This class also initializes the Spring container and loads the beans
 * necessary for the dependency injection.
 * 
 * This project uses "plain" Java code for GUI building (for exercising purposes). 
 * JavaFX Scene Builder haven't been used. Project uses some basic
 * Spring Framework features, and Lombok project features.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
public class StartApplication extends Application {

	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		StartApplication.primaryStage = primaryStage;
		try {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
			MainView view = context.getBean(MainView.class);
			view.initView(primaryStage);
			CenterGridController centerGridController = context.getBean(CenterGridController.class);
			centerGridController.initController();
			MenuController menuController = context.getBean(MenuController.class);
			menuController.initMenuController();
			LeftBoxMenuController leftBoxMenuController = context.getBean(LeftBoxMenuController.class);
			leftBoxMenuController.initController();
			context.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Allows binding secondary stage with primary stage in the FillFormView class.
	 * 
	 * @return the primaryStage
	 */
	public static Stage getPrimaryStage() {
		return StartApplication.primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}