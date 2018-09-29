package mapMaker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.application.Platform;
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
import mapMaker.map.MapArea;

public class MapMaker extends Application{

	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane rootPane = new BorderPane();
		

		
		MapArea map = new MapArea();
		
		rootPane.setTop(new MapMakerMenu());
		rootPane.setCenter( map);
		
		Scene scene = new Scene(rootPane, 800, 800);
		scene.getStylesheets().add(new File("resources/css/style.css").toURI().toString());
		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
			if (e.getCode() == KeyCode.ESCAPE)
				primaryStage.hide();
		});
		primaryStage.setScene(scene);
		primaryStage.setTitle("Map Maker 1.0");
		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
