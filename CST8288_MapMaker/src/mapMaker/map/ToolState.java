package mapMaker.map;

public class ToolState {

	private Tools tool = Tools.SELECTION;
	int option = 0;
	private static ToolState state = null;
	
	private ToolState() {}
	public static ToolState getState() {
		if (state == null) {
			synchronized(ToolState.class) {
				if (state == null) {
					state = new ToolState();
				}
			}
		}
		
		return state;
	}

	public Tools getTool() {
		return tool;
	}
	
	public int getOption() {
		return option;
	}
	
	public void setTool() {
		this.tool = tool;
	}
	
	public void setOption() {
		this.option = option;
	}
	
}
