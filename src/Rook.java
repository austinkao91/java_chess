
public class Rook extends Slidable{
	public Rook(char in_color, int[] in_pos, Board in_board) {
		super(in_color, in_pos, in_board);
		horizontal = true;
		diagonal = false;
	}
	public String toString() {
		if(color == 'w') {
			return " \u2656 ";
		} else {
			return " \u265C ";
		}
			
	}
}
