package mapMaker.map.shapes;

import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

/**
 * http://dimitroff.bg/generating-vertices-of-regular-n-sided-polygonspolyhedra-and-circlesspheres/
 * 
 * @author Shahriar (Shawn) Emami
 * @version Sep 27, 2018
 */
public class PolyShape extends Polygon{
	
	private static final String POINTS_COUNT = "sides";
	private static final String FILL = "fill";
	private static final String STROKE = "stroke";
	private static final String WIDTH = "strokeWidth";
	private static final String POINTS = "points";


	private final ObservableList< Double> pPoints;
	private int sides;
	private double angle;
	private double dx, dy;
	private double x1, y1;
	private ControlPoint[] cPoints;

	public PolyShape( int sides){
		this();
		this.sides = sides;
	}
	
	public PolyShape( List< String> list){
		this();
		convertFromString( list);
		registerControlPoints();
	}
	
	private PolyShape(){
		super();
		pPoints = getPoints();
	}
	
	private void cacluatePoints(){
		for( int side = 0; side < sides; side++){
			pPoints.addAll( point( Math::cos, dx / 2, angle, side, sides) + x1,
					point( Math::sin, dy / 2, angle, side, sides) + y1);
		}
	}
	
	private double radianShift( double x1, double y1, double x2, double y2){
		return Math.atan2( y2 - y1, x2 - x1);
	}
	
	private double point( DoubleUnaryOperator operation, double radius, double shift, double side, final int SIDES){
		return radius * operation.applyAsDouble( shift + side * 2.0 * Math.PI / SIDES);
	}

	public void registerControlPoints(){
		cPoints = new ControlPoint[ pPoints.size() / 2];
		for ( int i = 0; i < pPoints.size(); i += 2) {
			final int j = i;
			cPoints[ i / 2] = new ControlPoint( pPoints.get( i), pPoints.get( i + 1));
			cPoints[ i / 2].addChangeListener(( value, oldV, newV) -> pPoints.set( j, newV.doubleValue()),
											  ( value, oldV, newV) -> pPoints.set( j + 1, newV.doubleValue()));
		};
	}

	private double distance( double x1, double y1, double x2, double y2){
		return Math.sqrt( (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	public void reDraw( double x1, double y1, double x2, double y2, boolean symmetrical){
		angle = radianShift( x1, y1, x2, y2);
		dx = symmetrical ? distance( x1, y1, x2, y2) : x2 - x1;
		dy = symmetrical ? dx : y2 - y1;
		this.x1 = x1 + (x2 - x1) / 2;
		this.y1 = y1 + (y2 - y1) / 2;
		pPoints.clear();
		cacluatePoints();
	}
	
	public void translate( double dx, double dy) {
		for ( ControlPoint c : cPoints) {
			c.translate(dx, dy);
		}
	}

	public Node[] getControlPoints(){
		return cPoints;
	}
	
	public String convertToString(){
		String newLine = System.lineSeparator();
		StringBuilder builder = new StringBuilder();
		builder.append( POINTS_COUNT).append( " ").append( sides).append( newLine);
		builder.append( FILL).append( " ").append( colorToString( getFill())).append( newLine);
		builder.append( STROKE).append( " ").append( colorToString( getStroke())).append( newLine);
		builder.append( WIDTH).append( " ").append( getStrokeWidth()).append( newLine);
		builder.append( POINTS).append( " ").append( pPoints.stream().map( e -> Double.toString( e)).collect( Collectors.joining( " ")));

		return builder.toString();
	}
	
	private void convertFromString( List< String> list){
		list.forEach( line -> {
			String[] tokens = line.split( " ");
			switch( tokens[0]){
				case POINTS_COUNT:
					sides = Integer.valueOf( tokens[1]);
					break;
				case FILL:
					setFill( stringToColor( tokens[1], tokens[2]));
					break;
				case STROKE:
					setStroke( stringToColor( tokens[1], tokens[2]));
					break;
				case WIDTH:
					setStrokeWidth( Double.valueOf( tokens[1]));
					break;
				case POINTS:
					Stream.of( tokens).skip( 1).mapToDouble( Double::valueOf).forEach( pPoints::add);
					break;
				default:
					throw new UnsupportedOperationException( "\"" + tokens[0] + "\" is not supported");
			}
		});
	}
	
	private String colorToString( Paint p){
		return colorToString( Color.class.cast( p));
	}
	
	private String colorToString( Color c){
		return String.format( "#%02X%02X%02X %f",
				(int) (c.getRed() * 255),
				(int) (c.getGreen() * 255),
				(int) (c.getBlue() * 255),
				c.getOpacity());
	}
	
	private Color stringToColor( String color, String alpha){
		return Color.web( color, Double.valueOf( alpha));
	}
}
