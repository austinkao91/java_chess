
public class King extends Steppable {
	public King(char in_color, int[] in_pos, Board in_board) {
		super(in_color, in_pos, in_board);
		vectors = new int[][] {{-1,1}, {-1, 0}, {-1,1}, {0,-1},{0,1}, {1,-1}, {1,0}, {1,1}};
	}
	public String toString() {
		if(color == 'w') {
			return " \u2654 ";
		} else {
			return " \u265A ";
		}
			
	}
}
