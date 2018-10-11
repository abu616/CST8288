package mapMaker;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
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
	public void start( Stage primaryStage) throws Exception {
		
		BorderPane rootPane = new BorderPane();
		MapMakerTools toolBar = new MapMakerTools();
			toolBar.setOrientation(Orientation.VERTICAL);
			toolBar.setMinWidth(50);
		MapArea map = new MapArea();
		
		rootPane.setTop( new MapMakerMenu());
		rootPane.setBottom( new StatusBar());
		rootPane.setLeft( toolBar);
		rootPane.setRight( new MapMakerRightDetail());
		rootPane.setCenter( map);
		
		Scene scene = new Scene(rootPane, 1000, 800);
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
