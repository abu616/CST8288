package mapMaker.map;

public enum ToolState {
		NONE_SELECTED("None Selected"),
		SELECT("Select"),
		MOVE("Move"),
		LINE("Line"),
		TRIANGLE("Triangle"),
		RECTANGLE("Rectangle"),
		PENTAGON("Pentagon"),
		HEXAGON("Hexagon"),
		PATH("Path"),
		ERASE("Erase"),
		DOOR("Door");

	private String option;

	private ToolState(String name) {
		this.option = name;
	}

	public String getValue() {
		return option;
	}

	@Override
	public String toString() {
		return "Tool: " + this.option;
	}

}