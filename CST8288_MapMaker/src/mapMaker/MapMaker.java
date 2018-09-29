package mapMaker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MapMaker extends Application{
	

	public static final String INFO_PATH = "resources/icons/info.txt";
	public static final String HELP_PATH = "resources/icons/help.txt";
	public static final String CREDITS_PATH = "resources/icons/credits.txt";

	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		
		MenuBar menuBar = new MenuBar(
				new Menu("File", null,
						createMenuItem( "New",(e)->{}),
						createMenuItem( "Save",(e)->{}),
						new SeparatorMenuItem(),
						createMenuItem( "Exit",(e)->{})
						),
				new Menu("Help", null,
						createMenuItem( "Credit",(e)->displayCredit()),
						createMenuItem( "Info",(e)->{}),
						new SeparatorMenuItem(),
						createMenuItem( "Help",(e)->{})
						)
				);
		
		MapArea map = new MapArea();
		
		root.setTop(menuBar);
		root.setCenter( map);
		
		Scene scene = new Scene(root, 800, 800);
		scene.getStylesheets().add(new File("resources/css/style.css").toURI().toString());
		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
			if (e.getCode() == KeyCode.ESCAPE)
				primaryStage.hide();
		});
		primaryStage.setScene(scene);
		primaryStage.setTitle("Map Maker Skeleton");
		primaryStage.show();
	}

	private void displayCredit() {
		displayAlert("Credit", loadFile( CREDITS_PATH));
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
	
	private void displayAlert( String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.show();
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}
	
	private MenuItem createMenuItem( String name, EventHandler<ActionEvent> handler) {
		Label icon = new Label();
		icon.setId(name+"-icon");
		MenuItem item = new MenuItem(name, icon);
		item.setOnAction(handler);
		item.setId(name);
		return item;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
