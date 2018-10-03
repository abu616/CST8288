package mapMaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MapMakerMenu extends MenuBar {

	public static final String INFO_PATH = "resources/icons/info.txt";
	public static final String HELP_PATH = "resources/icons/help.txt";
	public static final String CREDITS_PATH = "resources/icons/credits.txt";
	
	public MapMakerMenu() {
		super.getMenus().addAll( getFileMenu(), getHelpMenu());
	}

	private static Menu getFileMenu() {
		Menu fileMenu = new Menu( "File");
		fileMenu.getItems().addAll(
						getMenuItem( "New", (e) -> {}),
						getMenuItem( "Save", (e) -> {}),
						new SeparatorMenuItem(),
						getMenuItem( "Exit", (e) -> Platform.exit()));
		return fileMenu;
		}
	
	private Menu getHelpMenu() {
		Menu helpMenu = new Menu( "Help");
		helpMenu.getItems().addAll(
						getMenuItem( "Credit", (e) -> displayCredit()),
						getMenuItem( "Info", (e) -> displayInfo()),
						new SeparatorMenuItem(),
						getMenuItem( "Help", (e) -> displayHelp()));
		return helpMenu;
	}
	
	private static MenuItem getMenuItem( String name, EventHandler<ActionEvent> handler) {
		Label icon = new Label();
		icon.setId( name+"-icon");
		MenuItem item = new MenuItem( name, icon);
		item.setOnAction( handler);
		item.setId( name);
		return item;
	}
	
	private void displayAlert( String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("Map Maker");
		alert.setContentText(message);
		alert.show();
	}
	
	private String loadFile( String path) {
		String message = "";
		try {
			message = Files.lines( Paths.get(path)).reduce("", (a,b)->a+System.lineSeparator()+b+System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}
	
	public void displayCredit() {
		displayAlert("Credit", loadFile( CREDITS_PATH));
	}
	
	public void displayInfo() {
		displayAlert("Info", loadFile( INFO_PATH));
	}
	
	public void displayHelp() {
		displayAlert("Help", loadFile( HELP_PATH));
	}
	
	
}