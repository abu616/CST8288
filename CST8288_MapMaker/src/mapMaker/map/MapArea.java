package mapMaker.map;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import mapMaker.map.shapes.PolyShape;
import mapMaker.map.shapes.SelectionArea;

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
				break;
			case MOVE:
				break;
			case PATH:
				break;
			case SELECTION:
				new SelectionArea().start(e.getSceneX(), e.getSceneY());
				break;
			case ERASE:
				if ( e.getTarget() instanceof PolyShape) {
					children.remove(e.getTarget());
					children.removeAll(((PolyShape) e.getTarget()).getControlPoints());
				}
				break;
			case ROOM:
				int option = tool.getOption();
				activeShape = new PolyShape( option);
				activeShape.setStroke( Color.BLACK);
				activeShape.setStrokeWidth(2.5);
				activeShape.setFill( Color.AQUAMARINE);
				children.add( activeShape);
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
				if ( e.getTarget() instanceof PolyShape) {
					activeShape = (PolyShape) e.getTarget();
					activeShape.translate(e.getX()-startX, e.getY()-startY);
					startX = e.getX();
					startY = e.getY();
				}
				break;
			case ROOM:
				//redraw the active shape if it is not null
				if ( activeShape != null) {
					double endX = e.getX();
					double endY = e.getY();
					
					activeShape.reDraw(startX, startY, distance(startX, startY, endX, endY));
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
				break;
			case ROOM:
				activeShape.registerControlPoints();
				if( activeShape != null) {
					children.addAll(activeShape.getControlPoints());
				}
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

