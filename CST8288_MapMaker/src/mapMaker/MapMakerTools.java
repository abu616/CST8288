package mapMaker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import mapMaker.map.Tools;

public class MapMakerTools  extends ToolBar{
	
	public MapMakerTools() {
		super.getItems().addAll(
				getButton( "Select", (e) -> {}),
				getButton( "Move", (e) -> {}),
				getMenuButton( "Room", (e) -> {}),
				getButton( "Path", (e) -> {}),
				getButton( "Erase", (e) -> {}),
				getButton( "Door", (e) -> {}));
	}
	
	public Button getButton( String name, EventHandler<ActionEvent> handler) {
		Label icon = new Label();
		icon.setId( name + "-icon");
		Button button = new Button(name, icon);
		button.setId( name);
		button.setText("");
		button.setOnAction( handler);
		return button;
	}
	
	public MenuButton getMenuButton( String name, EventHandler<ActionEvent> handler) {
		Label icon = new Label();
		icon.setId( name + "icon");
		MenuButton menuButton = new MenuButton();
		menuButton.setId( name);
		menuButton.setOnAction( handler);
		menuButton.getItems().addAll(			
			createMenuItem( "Line", (e) -> {}),
			createMenuItem( "Triangle", (e) -> {}),
			createMenuItem( "Rectangle", (e) -> {}),
			createMenuItem( "Pentagon", (e) -> {}),
			createMenuItem( "Hexagon", (e) -> {}));
		return menuButton;
	}

	private MenuItem createMenuItem( String name, EventHandler< ActionEvent> handler){
		Label icon = new Label();
		icon.setId( name + "-icon");
		MenuItem item = new MenuItem( name, icon);
		item.setId( name);
		item.setOnAction( handler);
		return item;
	}
	

}
