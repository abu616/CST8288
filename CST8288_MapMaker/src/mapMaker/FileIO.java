package mapMaker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import mapMaker.MapMaker;
import mapMaker.map.MapArea;

public class FileIO {
	
	public static final String REGEX_DECIMAL = "-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?";
	public static final String REGEX_POSITIVE_INTEGER = "([1-9][0-9]*)";
	public static final Pattern P = Pattern.compile( REGEX_POSITIVE_INTEGER);
	public static final String MAPS_DIRECTORY = "resources/maps";
	private MapArea map;
	
	/**
	 * <p>
	 * ask the user where they need to save then get the content to write from 
	 * {@link MapAreaSkeleton#convertToString()}.</br>
	 * </p>
	 * @param primary - {@link Stage} object that will own the {@link FileChooser}.
	 */
	private void saveMap( Stage primary){
		//get the file object to save to
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

	/**
	 * <p>
	 * ask the user what file they need to open then pass the content to 
	 * {@link MapAreaSkeleton#convertFromString(java.util.Map)}.</br>
	 * </p>
	 * @param primary - {@link Stage} object that will own the {@link FileChooser}.
	 */
	private void loadMap( Stage primary){
		//get the file object to load from
		File file = getFileChooser( primary, false);
		if (file==null || !file.exists())
			return;
		try{
			//no parallel (threading) here but this is safer
			AtomicInteger index = new AtomicInteger(0);  
			//index.getAndIncrement()/5 means every 5 elements increases by 1
			//allowing for every 5 element placed in the same key
			//for each line in file group every 5 and pass to map area
			map.convertFromString( Files.lines( file.toPath()).collect( Collectors.groupingBy( l->index.getAndIncrement()/5)));
		}catch( IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>
	 * using the {@link FileChooser} open a new window only showing .map extension;
	 * in starting path of {@link MapMakerSkleton#MAPS_DIRECTORY}.</br>
	 * this function can be used to save or open file depending on the boolean argument.</br>
	 * </p>
	 * @param primary - {@link Stage} object that will own the {@link FileChooser}.
	 * @param save - if true show {@link FileChooser#showSaveDialog(javafx.stage.Window)} 
	 * 					else {@link FileChooser#showOpenDialog(javafx.stage.Window)}
	 * @return a {@link File} representing the save or load file object
	 */
	private File getFileChooser( Stage primary, boolean save){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add( new ExtensionFilter( "Maps", "*.map"));
		fileChooser.setInitialDirectory( Paths.get( MAPS_DIRECTORY).toFile());
		return save?fileChooser.showSaveDialog( primary):fileChooser.showOpenDialog( primary);
	}

	/**
	 * <p>
	 * show an input dialog which to ask user for an input that matches the given regex.</br>
	 * </p>
	 * @param title - {@link String} object containing the title of dialog.
	 * @param content - {@link String} object containing the body of dialog.
	 * @param match - {@link String} object containing the regex to test against input.
	 * @param callBack - {@link Consumer} object to be called when there is a valid input.
	 */
	private void showInputDialog( String title, String content, String match, Consumer<String> callBack){
		TextInputDialog input = new TextInputDialog();
		input.setTitle( title);
		input.setHeaderText( null);
		input.setContentText( content);
		input.getEditor().textProperty().addListener( (value, oldV, newV)->{
			//check if the inputed text matched the given regex
			if(!newV.isEmpty() && !Pattern.matches( match, newV)){
				input.getEditor().setText( oldV);
			}
		});
		//show dialog and wait for an input, if valid call callBack
		input.showAndWait().ifPresent(e->{if(e.matches( match))callBack.accept( e);});
	}

}
