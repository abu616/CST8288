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

	private int sides;

	private final ObservableList< Double> pPoints;
	private ControlPoint[] cPoints;

	public PolyShape( int sides){
		super();
		this.sides = sides;
		pPoints = getPoints();
	}

	private void calculatePoints( double x, double y, double radius){
		final double shift = radianShift( sides);
		for( int side = 0; side < sides; side++){
			pPoints.addAll( point( Math::cos, radius, shift, side, sides) + x
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
		cPoints = new ControlPoint[ pPoints.size() / 2];
		for ( int i = 0; i < pPoints.size(); i+=2) {
			final int j = i;
			cPoints[ i / 2] = new ControlPoint( pPoints.get( i), pPoints.get( i + 1));
			cPoints[ i / 2].addChangeListener((value, oldV, newV) -> pPoints.set( j, newV.doubleValue()),
											  (value, oldV, newV) -> pPoints.set( j + 1, newV.doubleValue()));
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
		pPoints.clear();
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
}
