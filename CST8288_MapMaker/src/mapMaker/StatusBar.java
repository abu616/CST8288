package mapMaker;

import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;
import mapMaker.map.ToolState;

public class StatusBar extends ToolBar{
	
	private Text toolSelected;

	public StatusBar() {
		this.toolSelected = new Text("Tool: " + ToolState.getState().getTool());
		super.getItems().add( toolSelected);
	}
	
}
