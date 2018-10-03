package mapMaker.map.shapes;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class ControlPoint extends Circle {

	public ControlPoint(double x, double y) {
		super(x, y, 5);
	}

	public void move(Point2D vector) {
		super.setCenterX(super.getCenterX() + vector.getX());
		super.setCenterY(super.getCenterY() + vector.getY());
	}

}
