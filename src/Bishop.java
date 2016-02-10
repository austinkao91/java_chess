
public class Bishop extends Slidable {
	public Bishop(char in_color, int[] in_pos, Board in_board) {
		super(in_color, in_pos, in_board);
		horizontal = false;
		diagonal = true;
	}
	public String toString() {
		if(color == 'w') {
			return " \u2657 ";
		} else {
			return " \u265D ";
		}
			
	}
	
}
