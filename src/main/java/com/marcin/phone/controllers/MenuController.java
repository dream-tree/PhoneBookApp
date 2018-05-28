package com.marcin.phone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.marcin.phone.views.MenuView;

@Controller
public class MenuController {

	private MenuView menuView;
	
	@Autowired
	public MenuController(MenuView menuView) {
		this.menuView = menuView;
	}
	
	public void initMenuController() {	
		menuView.getMenuFileItem1().setOnAction(t -> menuView.userInfoAlert());	
		menuView.getMenuFileItem2().setOnAction(t -> System.out.println("unavailable"));		
		menuView.getMenuFileItem3().setOnAction(t -> System.exit(0));	
		menuView.getMenuAboutItem().setOnAction(t -> menuView.userAboutAlert());
		menuView.getMenuExitItem().setOnAction(t -> System.exit(0));	
	}
}
