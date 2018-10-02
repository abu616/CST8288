package mapMaker.map;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mapMaker.map.shapes.PolyShape;

public class MapArea extends Pane {

	/**
	 * instead of calling getChildren every time you can call directly the reference of it which is initialized in constructor
	 */
	private ObservableList<Node> children;
	/**
	 * active shape that is currently being manipulated
	 */
	private PolyShape activeShape;
	/**
	 * last location of the mouse
	 */
	private double startX, startY;
	/**
	 * Reference to ToolSate so you don't have to call ToolSate.getState() every time.
	 */
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
			case MOVE:
			case PATH:
			case SELECTION:
			case ERASE:
			case ROOM:
				//create new shape with number of given points?
				//add fill, stroke and strokeWidth as needed
				//finally add active shape to children of this class
				break;
			default:
				throw new UnsupportedOperationException( "Cursor for Tool \"" + activeTool().name() + "\" is not implemneted");
		}
	}

	private void dragClick( MouseEvent e){
		e.consume();
		switch( activeTool()){
			case DOOR:
			case PATH:
			case ERASE:
			case SELECTION:
			case MOVE:
				//start only needs to be updated for move , what we discussed in class we with out the need of PolyShape
				startX = e.getX();
				startY = e.getY();
				break;
			case ROOM:
				//redraw the active shape if it is not null
				if(activeShape == null) {} 
					//else ( redraw activeShape);
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
}

