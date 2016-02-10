import java.util.ArrayList;

public class Pawn extends Piece {
	boolean moved;
	int delta;
	public Pawn(char in_color, int[] in_pos, Board in_board) {
		super(in_color, in_pos, in_board);
		moved = false;
		if(in_color == 'w') {
			delta = 1;
		} else {
			delta = -1;
		}
	}
	public String toString() {
		if(color == 'w') {
			return " \u2659 ";
		} else {
			return " \u265F ";
		}
			
	}
	
	public void set_pos(int[] new_pos) {
		super.set_pos(new_pos);
		moved = true;
	}
	
	public void set_pos(int row, int col) {
		super.set_pos(row,col);
		moved = true;
	}
	public ArrayList<int[]> get_valid_moves() {
		ArrayList<int[]> validated_moves = new ArrayList<int[]>();
		int[] possible_pos = new int[] {pos[0] + delta, pos[1]};
		if(valid_move(possible_pos)) {
			validated_moves.add(possible_pos);
			
			if(!moved) {
				int[] next_pos = new int[] {pos[0] + 2 * delta, pos[1]};
				if(valid_move(next_pos)) {
					validated_moves.add(next_pos);
				}
			}
		}
		ArrayList<int[]> capturable_moves = capturable_pos();
		for(int[] move : capturable_moves) {
			if(valid_move(move)) {
				validated_moves.add(move);
			}
		}
		
		return validated_moves;
	}
	
	private ArrayList<int[]> capturable_pos() {
		ArrayList<int[]> capture_moves = new ArrayList<int[]>();
		int[] capl_pos = new int[] {pos[0] + delta, pos[1]+ delta};
		int[] capr_pos = new int[] {pos[0] + delta, pos[1]- delta};
		if(capturable(capl_pos)) { capture_moves.add(capl_pos); }
		if(capturable(capr_pos)) { capture_moves.add(capr_pos); }
		return capture_moves;
	}	
	
	protected boolean capturable(int[] check_pos) {
		return (board.in_bounds(check_pos) && board.get_piece(check_pos).occupied && board.get_piece(check_pos).color != this.color);
	}
	
	protected boolean valid_move(int[] check_pos) {
		return board.in_bounds(check_pos) && !board.get_piece(check_pos).occupied;
	}
}
