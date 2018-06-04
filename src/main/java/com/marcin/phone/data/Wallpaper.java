package com.marcin.phone.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class responsible for loading an image placed over the search bar 
 * in the application main window.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@Component
public class Wallpaper {

	public Wallpaper() {
	}

	/**
	 * Loads picture displayed in the application main window.
	 * 
	 * @return ImageView picture to be displayed in the application main window
	 */
	public ImageView setWallpaper() {
		Path path = FileSystems.getDefault().getPath("src/main/resources/contacts-shutterstock.jpg");
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(path.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image image = new Image(inputstream);
		ImageView imageView = new ImageView(image);
		/*
		 * BackgroundImage backgroundImage = new BackgroundImage(image,
		 * BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
		 * BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		 */
		return imageView;
	}
}