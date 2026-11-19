import java.util.Arrays;

/**
 * This captures the state of the pointer and the trace of its movement.
 * IMPORTANT: This class is incomplete. Please look for "TODO" comments.
 *
 * @author huy.pham
 */
public class Screen {
    public static final String NON_VISITED_MARK = "#";
    public static final String VISITED_MARK = ".";

	public int noOfRows;
	public int noOfColumns;
	public Pointer pointer;
	public String[][] trace;
	
	public Screen (int noOfRows, int noOfColumns, Pointer pointer) throws OutOfScreenException {
		this.noOfRows = noOfRows;
		this.noOfColumns = noOfColumns;
		this.pointer = pointer;
		
		this.trace = new String[noOfRows][noOfColumns];
		for (String[] row : trace) {
			Arrays.fill(row, NON_VISITED_MARK);
		}
		
		// mark initial position
		if (this.pointer.isPenDown) this.markVisistedPos(pointer.position);
	}
	
	/**
	 * Mark the current position as visited.
	 * @param position
	 * @throws Exception 
	 */
	public void markVisistedPos(Position p) throws OutOfScreenException {
		if (p.x >= 0 && p.x < noOfRows && p.y >= 0 && p.y < noOfColumns) {
			trace[p.x][p.y] = VISITED_MARK;
		} else {
			throw new OutOfScreenException();
		}
	}
	
	/**
	 * NOTE: It inserts a new line character after every row including the last one.
	 * @see {@link ScreenTest} for the expected result
	 * @return a string representing the screen including pointer's visited positions, its current position and direction.
	 */
	public String trace() {
        String icon;
        if (pointer.direction == Direction.Upward) icon = "^";
        else if (pointer.direction == Direction.Downward) icon = "v";
        else if (pointer.direction == Direction.Left) icon = "<";
        else icon = ">";
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < noOfRows; row++) {
            for (int column = 0; column < noOfColumns; column++) {
                // 指针图标只影响显示，不覆盖已经记录的轨迹。
                if (row == pointer.position.x && column == pointer.position.y) result.append(icon);
                else result.append(trace[row][column]);
            }
            result.append("\n");
        }
        return result.toString();
    }
	
	private String getPointerIcon() {
		switch (pointer.direction) {
		case Upward:
			return "^";
		case Downward: 
			return "v";
		case Left: 
			return "<";
		case Right:
			return ">";
		default:
			return null;
		}
	}
}
