package mapMaker;

import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;

public class StatusBar extends ToolBar{
	
	private Text toolSelected;

	public StatusBar() {
		this.toolSelected = new Text("Tool: ");
		super.getItems().add(toolSelected);
	}

	public Text getToolSelected() {
		return toolSelected;
	}

	public void setToolSelected(Text toolSelected) {
		this.toolSelected = toolSelected;
	}
	
}
