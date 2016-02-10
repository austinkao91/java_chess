
public class Queen extends Slidable {
	public Queen(char in_color, int[] in_pos, Board in_board) {
		super(in_color, in_pos, in_board);
		horizontal = true;
		diagonal = true;
	}
	
	public String toString() {
		if(color == 'w') {
			return " \u2655 ";
		} else {
			return " \u265B ";
		}
			
	}
}


