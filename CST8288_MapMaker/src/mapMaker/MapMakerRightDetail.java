package mapMaker;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MapMakerRightDetail extends VBox {
	
	private ListView<Node> shapeList;
	private GridPane detailEditor;
	
	public MapMakerRightDetail(){
		this.shapeList = new ListView<>();
		this.detailEditor = new GridPane();
		super.getChildren().addAll(shapeList, detailEditor);
	}
	
	public ListView<Node> getShapeList(){
		return shapeList;
	}
	
	public void setShapeList(){
		this.shapeList = shapeList;
	}
	
	public GridPane getDetailEditor() {
		return detailEditor;
	}
	
	public void setDetailEditor(GridPane detailEditor) {
		this.detailEditor = detailEditor;
	}
	
}
