
public class Knight extends Steppable {
	public Knight(char in_color, int[] in_pos, Board in_board) {
		super(in_color, in_pos, in_board);
		vectors = new int[][] {{-2,-1}, {-2, 1}, {2,-1}, {2,1},{-1,-2}, {1,-2}, {-1,2}, {1,2}};
	}
	public String toString() {
		if(color == 'w') {
			return " \u2658 ";
		} else {
			return " \u265E ";
		}
			
	}
}
