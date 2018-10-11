package mapMaker.map;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import mapMaker.map.shapes.PolyShape;
import mapMaker.map.shapes.SelectionArea;

public class MapArea extends Pane {

	private ObservableList< Node> children;
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
			case SELECT:
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
			case SELECT:
			case MOVE:
				if ( e.getTarget() instanceof PolyShape) {
					activeShape = (PolyShape) e.getTarget();
					activeShape.translate(e.getX()-startX, e.getY()-startY);
					startX = e.getX();
					startY = e.getY();
				}
				break;
			case ROOM:
				activeShape.reDraw( startX, startY, e.getX(), e.getY(), true);
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
			case SELECT:
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

	private Tools activeTool(){
		return tool.getTool();
	}
	
	public String convertToString(){
		return children.stream()
				.filter( PolyShape.class::isInstance)
				.map( PolyShape.class::cast)
				.map( PolyShape::convertToString)
				.collect( Collectors.joining( System.lineSeparator()));
	}
	
	public void convertFromString( Map< Object, List< String>> map){
		map.keySet().stream()
		.map( k->new PolyShape( map.get( k)))
		.forEach( s->{
			children.add( s);
			children.addAll( s.getControlPoints());
		});;
	}
	
	public void clearMap(){
		children.clear();
	}
}

