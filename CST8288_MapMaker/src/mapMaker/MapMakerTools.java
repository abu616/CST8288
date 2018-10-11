package mapMaker;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import mapMaker.map.ToolState;
import mapMaker.map.Tools;

public class MapMakerTools  extends ToolBar{

	
	public MapMakerTools() {
		super.getItems().addAll(
				getButton( "Select", (e) -> ToolState.getState().setTool( Tools.SELECT)),
				getButton( "Move", (e) -> ToolState.getState().setTool( Tools.MOVE)),
				getMenuButton( "Room", (e) -> {}),
				getButton( "Path", (e) -> ToolState.getState().setTool( Tools.PATH)),
				getButton( "Erase", (e) -> ToolState.getState().setTool( Tools.ERASE)),
				getButton( "Door", (e) -> ToolState.getState().setTool( Tools.DOOR)));
	}
	
	public Button getButton( String name, EventHandler<ActionEvent> handler) {
		Label icon = new Label();
		icon.setId( name + "-icon");
		Button button = new Button( name, icon);
		button.setId( name);
		button.setText( "");
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
			createMenuItem( "Line", (e) -> {
				ToolState.getState().setTool( Tools.ROOM);
				ToolState.getState().setOption(2);
			}),
			createMenuItem( "Triangle", (e) -> {
				ToolState.getState().setTool( Tools.ROOM);
				ToolState.getState().setOption(3);
			}),
			createMenuItem( "Rectangle", (e) -> {
				ToolState.getState().setTool( Tools.ROOM);
				ToolState.getState().setOption(4);
			}),
			createMenuItem( "Pentagon", (e) -> {
				ToolState.getState().setTool( Tools.ROOM);
				ToolState.getState().setOption(5);
			}),
			createMenuItem( "Hexagon", (e) -> {
				ToolState.getState().setTool( Tools.ROOM);
				ToolState.getState().setOption(6);
			}));
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
