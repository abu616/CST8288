package mapMaker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import mapMaker.map.MapArea;

public class MapMakerMenu extends MenuBar {
	
	public static final String REGEX_DECIMAL = "-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?";
	public static final String REGEX_POSITIVE_INTEGER = "([1-9][0-9]*)";
	
	public static final Pattern P = Pattern.compile( REGEX_POSITIVE_INTEGER);
	
	public static final String MAPS_DIRECTORY = "resources/maps";
	public static final String INFO_PATH = "resources/icons/info.txt";
	public static final String HELP_PATH = "resources/icons/help.txt";
	public static final String CREDITS_PATH = "resources/icons/credits.txt";
	
	private MapArea map;
	
	public MapMakerMenu() {
		super.getMenus().addAll( getFileMenu(), getHelpMenu());
	}

	private Menu getFileMenu() {
		Menu fileMenu = new Menu( "File");
		fileMenu.getItems().addAll(
						getMenuItem( "New", (e) -> {}),
						getMenuItem( "Save", (e) -> saveMap( null)),
						getMenuItem( "Open-archive", (e) -> showInputDialog(currentSkinClassName, currentSkinClassName, currentSkinClassName, null ) ), 
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
		icon.setId( name + "-icon");
		MenuItem item = new MenuItem( name, icon);
		item.setOnAction( handler);
		item.setId( name);
		return item;
	}
	
	private void displayAlert( String title, String message) {
		Alert alert = new Alert( Alert.AlertType.INFORMATION);
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
	
	private void saveMap( Stage primary){
		File file = getFileChooser( primary, true);
		if (file==null)
			return;
		try{
			if( !file.exists())
				file.createNewFile();
			Files.write( file.toPath(), map.convertToString().getBytes());
		}catch( IOException e){
			e.printStackTrace();
		}
	}
	
	private void loadMap( Stage primary){
		//get the file object to load from
		File file = getFileChooser( primary, false);
		if (file==null || !file.exists())
			return;
		try{
			AtomicInteger index = new AtomicInteger(0);  
			map.convertFromString( Files.lines( file.toPath()).collect( Collectors.groupingBy( l->index.getAndIncrement()/5)));
		}catch( IOException e){
			e.printStackTrace();
		}
	}
	
	private File getFileChooser( Stage primary, boolean save){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add( new ExtensionFilter( "Maps", "*.map"));
		fileChooser.setInitialDirectory( Paths.get( MAPS_DIRECTORY).toFile());
		return save?fileChooser.showSaveDialog( primary):fileChooser.showOpenDialog( primary);
	}
	
	private void showInputDialog( String title, String content, String match, Consumer<String> callBack){
		TextInputDialog input = new TextInputDialog();
		input.setTitle( title);
		input.setHeaderText( null);
		input.setContentText( content);
		input.getEditor().textProperty().addListener( (value, oldV, newV)->{
			if(!newV.isEmpty() && !Pattern.matches( match, newV)){
				input.getEditor().setText( oldV);
			}
		});
		input.showAndWait().ifPresent(e->{if(e.matches( match))callBack.accept( e);});
	}
	
	private void displayCredit() {
		displayAlert("Credit", loadFile( CREDITS_PATH));
	}
	
	private void displayInfo() {
		displayAlert("Info", loadFile( INFO_PATH));
	}
	
	private void displayHelp() {
		displayAlert("Help", loadFile( HELP_PATH));
	}
	
	
}