package mapMaker.map.shapes;

import java.util.function.DoubleUnaryOperator;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;

/**
 * http://dimitroff.bg/generating-vertices-of-regular-n-sided-polygonspolyhedra-and-circlesspheres/
 * 
 * @author Shahriar (Shawn) Emami
 * @version Sep 27, 2018
 */
public class PolyShape extends Polygon{

	private final ObservableList< Double> POLY_POINTS;

	private int sides;
	private double angle;
	private double dx, dy;
	private double x1, y1;

	private ControlPoint[] cPoints;

	public PolyShape( int sides){
		super();
		POLY_POINTS = getPoints();
		//Initialize sides
		this.sides = sides; // << Not positive this is right
	}

	private void cacluatePoints(){
		for( int side = 0; side < sides; side++){
			POLY_POINTS.addAll( point( Math::cos, dx / 2, angle, side, sides) + x1,
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
		cPoints = new ControlPoint[ POLY_POINTS.size() / 2];
		for ( int i = 0; i < POLY_POINTS.size(); i+=2) {
			final int j = i;
			cPoints[ i / 2] = new ControlPoint( POLY_POINTS.get( i), POLY_POINTS.get( i + 1));
			cPoints[ i / 2].addChangeListener((value, oldV, newV) -> POLY_POINTS.set( j, newV),
											  (value, oldV, newV) -> POLY_POINTS.set( j + 1, newV));
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
	public void reDraw( double x1, double y1, double x2, double y2, boolean symmetrical){
		//using radianShift to measure the drawing angle
		angle = radianShift(x1, x2, y1, y2);
		//if shape is symmetrical measure the distance between x1,y1 and x2,y2 and assign it to dx and dy
		if( PolyShape = symmetrical) {
			
		} else {
			
		}
		//if not dx is difference between x1 and x2 and dy is difference between y1 and y2
		//calculate the center of your shape:
		//x1 is x1 plus half the difference between x1 and x2
		//y1 is y1 plus half the difference between y1 and y2
		//clear points
		//call calculate
	}
	public void translate( double dx, double dy) {
		for ( ControlPoint c : cPoints) {
			c.translate(dx, dy);
		}
	}

	public Node[] getControlPoints(){
		return cPoints;
	}
}
