package mapMaker.map.shapes;

import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ControlPoint extends Circle {

	public ControlPoint(double x, double y) {
		super(x, y, 5, Color.BLACK);
	}

	public void addChangeListener( ChangeListener<Number> x, ChangeListener<Number> y){
		centerXProperty().addListener( x);
		centerYProperty().addListener( y);
	}
	
	public void translate( double dx, double dy) {
		centerXProperty().set(centerXProperty().get() + dx);
		centerYProperty().set(centerYProperty().get() + dy);
	}
}
