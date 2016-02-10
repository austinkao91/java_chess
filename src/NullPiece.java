import java.util.ArrayList;


public class NullPiece extends Piece {
	public NullPiece(char in_color, int[] in_pos, Board in_board) {
		pos = in_pos;
		color = ' ';
		occupied = false;
	}
	
	public NullPiece() {
		pos = new int[] {-1,-1};
		color = ' ';
		occupied = false;
	}
	
	public String toString() {
		return "   ";
	}
	public ArrayList<int[]> move() {
		return new ArrayList<int[]>();
	}
}

