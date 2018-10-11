package mapMaker.map;

public class ToolState {

	private static final ToolState STATE = new ToolState();
	private Tools tool = getTool();
	int option = 0;
	
	private ToolState() {}

	public final static ToolState getState() {
		return STATE;
	}
	
	public Tools getTool() {
		return tool;
	}
	
	public int getOption() {
		return option;
	}
	
	public void setTool(Tools tool) {
		this.tool = tool;
	}
	
	public void setOption(int option) {
		this.option = option;
	}
	
}
