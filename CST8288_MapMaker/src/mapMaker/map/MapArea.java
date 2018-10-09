package mapMaker.map;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mapMaker.map.shapes.PolyShape;

public class MapArea extends Pane {

	private ObservableList<Node> children;
	private PolyShape activeShape;
	private double startX, startY;
	private ToolState tool;

	public MapArea(){
		super();
		tool = ToolState.getState();
		children = this.getChildren();
		registerMouseEvents();
	}

	private void registerMouseEvents(){
		addEventHandler( MouseEvent.MOUSE_PRESSED, this::pressClick);
		addEventHandler( MouseEvent.MOUSE_RELEASED, this::releaseClick);
		addEventHandler( MouseEvent.MOUSE_DRAGGED, this::dragClick);
	}

	private void pressClick( MouseEvent e){
		e.consume();
		startX = e.getX();
		startY = e.getY();
		switch( activeTool()){
			case DOOR:
				break;
			case MOVE:
				break;
			case PATH:
				break;
			case SELECTION:
				break;
			case ERASE:
				break;
			case ROOM:
				activeShape = new PolyShape( tool.getOption());
				children.add( activeShape);
				/*
				int option = tool.getOption();
				activeShape = new PolyShape( option);
				activeShape.setStroke( Color.BLACK);
				activeShape.setStrokeWidth(2.5);
				activeShape.setFill( Color.AQUAMARINE);
				children.add( activeShape);
				*/
				break;
			default:
				throw new UnsupportedOperationException( "Cursor for Tool \"" + activeTool().name() + "\" is not implemneted");
		}
	}

	private void dragClick( MouseEvent e){
		e.consume();
		switch( activeTool()){
			case DOOR:
				break;
			case PATH:
				break;
			case ERASE:
				break;
			case SELECTION:
				break;
			case MOVE:
				startX = e.getX();
				startY = e.getY();
				break;
			case ROOM:
				//redraw the active shape if it is not null
				if ( activeShape != null) {
					activeShape.reDraw(e.getX(), e.getY(), ((startX + startY) - (e.getX()+e.getY())));
				}
				break;
			default:
				throw new UnsupportedOperationException( "Drag for Tool \"" + activeTool().name() + "\" is not implemneted");
		}
	}

	private void releaseClick( MouseEvent e){
		e.consume();
		switch( activeTool()){
			case DOOR:
			case MOVE:
			case PATH:
			case SELECTION:
			case ERASE:
			case ROOM:
				break;
			default:
				throw new UnsupportedOperationException( "Release for Tool \"" + activeTool().name() + "\" is not implemneted");
		}
		activeShape = null;
	}
	
	private double distance ( double x1, double y1, double x2, double y2){
	    return Math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1));
	}

	private Tools activeTool(){
		return tool.getTool();
	}
	
	public String convertToString(){
		//for each node in children
		return children.stream()
				//filter out any node that is not PolyShape
				.filter( PolyShape.class::isInstance)
				//cast filtered nodes to PolyShapes
				.map( PolyShape.class::cast)
				//convert each shape to a string format
				.map( PolyShape::convertToString)
				//join all string formats together using new line
				.collect( Collectors.joining( System.lineSeparator()));
	}
	/**
	 * <p>
	 * create all shapes that are stored in given map. each key contains one list representing on PolyShape.</br>
	 * </p>
	 * @param map - a data set which contains all shapes in this object.
	 */
	public void convertFromString( Map< Object, List< String>> map){
		//for each key inside of map
		map.keySet().stream()
		//create a new PolyShape with given list in map
		.map( k->new PolyShape( map.get( k)))
		//for each created PolyShape
		.forEach( s->{
			children.add( s);
			children.addAll( s.getControlPoints());
		});;
	}
	
	/**
	 * <p>
	 * call this function to clear all shapes in {@link MapAreaSkeleton}.</br>
	 * </p>
	 */
	public void clearMap(){
		children.clear();
	}
}

