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
	
	private int sides;
	private final ObservableList< Double> POLY_POINTS;
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
		POLY_POINTS = getPoints();
	}

	private void calculatePoints( double x, double y, double radius){
		final double shift = radianShift( sides);
		for( int side = 0; side < sides; side++){
			POLY_POINTS.addAll( point( Math::cos, radius, shift, side, sides) + x
					, point( Math::sin, radius, shift, side, sides) + y);
		}
	}

	private double radianShift( final int SIDES){
		return Math.PI / 2 - Math.PI / SIDES;
	}

	private double point( DoubleUnaryOperator operation, double radius, double shift, double side, final int SIDES){
		return radius * operation.applyAsDouble( shift + side * 2.0 * Math.PI / SIDES);
	}

	public void registerControlPoints(){
		cPoints = new ControlPoint[ POLY_POINTS.size() / 2];
		for ( int i = 0; i < POLY_POINTS.size(); i+=2) {
			final int j = i;
			cPoints[ i / 2] = new ControlPoint( POLY_POINTS.get( i), POLY_POINTS.get( i + 1));
			cPoints[ i / 2].addChangeListener((value, oldV, newV) -> POLY_POINTS.set( j, newV.doubleValue()),
											  (value, oldV, newV) -> POLY_POINTS.set( j + 1, newV.doubleValue()));
		};
	}

	/**
	 * measure the distance between 2 points
	 */
	private double distance( double x1, double y1, double x2, double y2){
		return Math.sqrt( (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	/**
	 * redraw the shape without the need to remake it. till all redrawing are done
	 * registerControlPoints should not be called.
	 */
	public void reDraw( double x1, double y1, double radius){	
		//clear your points then calculate the points again
		POLY_POINTS.clear();
		calculatePoints( x1, y1, radius);
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
		builder.append( POINTS).append( " ").append( POLY_POINTS.stream().map( e -> Double.toString( e)).collect( Collectors.joining( " ")));

		return builder.toString();
	}
	/**
	 * <p>
	 * convert array of strings to a PolyShape. called from constructor.</br>
	 * each property is located in one index of the list.</br>
	 * each index starts with a name of property and its value/s in front of it all separated by space.</br>
	 * </p>
	 * @param list - a list of properties for this shape
	 */
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
					Stream.of( tokens).skip( 1).mapToDouble( Double::valueOf).forEach( POLY_POINTS::add);
					break;
				default:
					throw new UnsupportedOperationException( "\"" + tokens[0] + "\" is not supported");
			}
		});
	}

	/**
	 * <p>
	 * convert a {@link Paint} to a string in hex format followed by a space and alpha channel.</br>
	 * this method just calls {@link PolyShapeSkeleton#colorToString(Color)}.</br>
	 * </p>
	 * @param p - paint object to be converted
	 * @return string format of {@link Paint} in hex format plus alpha
	 */
	private String colorToString( Paint p){
		return colorToString( Color.class.cast( p));
	}

	/**
	 * <p>
	 * convert a {@link Color} to a string in hex format followed by a space and alpha channel.</br>
	 * </p>
	 * @param c - color object to be converted
	 * @return string format of {@link Color} in hex format plus alpha
	 */
	private String colorToString( Color c){
		return String.format( "#%02X%02X%02X %f",
				(int) (c.getRed() * 255),
				(int) (c.getGreen() * 255),
				(int) (c.getBlue() * 255),
				c.getOpacity());
	}

	/**
	 * <p>
	 * convert a string and given alpha to a {@link Color} object using {@link Color#web(String, double)}.</br>
	 * </p>
	 * @param color - hex value of a color in #ffffff
	 * @param alpha - alpha value of color between 0 and 1
	 * @return color object created from input
	 */
	private Color stringToColor( String color, String alpha){
		return Color.web( color, Double.valueOf( alpha));
	}
}
