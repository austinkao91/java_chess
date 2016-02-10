import java.util.*;
public class Steppable extends Piece {
	public int[][] vectors;
	public Steppable(char in_color, int[] in_pos, Board in_board) {
		super(in_color, in_pos, in_board);
	}
	
	protected boolean valid_moves(int[] position) {
		return board.in_bounds(position) && (!board.get_piece(position).occupied || board.get_piece(position).color != this.color);
	}
	
	public ArrayList<int[]> get_valid_moves() {
		ArrayList<int[]> validated_moves = new ArrayList<int[]>();
		for(int[] vector : vectors) {
			int dx = vector[0];
			int dy = vector[1];
			int[] possible_pos = new int[] {pos[0] + dx, pos[1] + dy};
			if(valid_moves(possible_pos)) {
				validated_moves.add(possible_pos);
			}
		}
		return validated_moves;
	}
}
