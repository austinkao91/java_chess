import java.util.*;
public class Slidable extends Piece{
	public static int[][] HOR_VECTORS = new int[][]{{1,0},{-1,0}, {0,1}, {0,-1}};
	public static int[][] DIAG_VECTORS = new int[][] {{1,1},{-1,-1}, {-1,1}, {1,-1}};
	protected boolean horizontal;
	protected boolean diagonal;
	
	public Slidable(char in_color, int[] in_pos, Board in_board) {
		super(in_color, in_pos, in_board);
	}
	
	protected boolean valid_moves(int[] position) {
		return board.in_bounds(position) && !board.get_piece(position).occupied;
	}
	
	public ArrayList<int[]> get_valid_moves(){
		ArrayList<int[]> vectors = new ArrayList<int[]>();
		if(horizontal) {
			for(int i = 0; i < HOR_VECTORS.length; i++) {
				vectors.add(HOR_VECTORS[i]);
			}
		}
		if(diagonal) {
			for(int i = 0; i < DIAG_VECTORS.length; i++) {
				vectors.add(DIAG_VECTORS[i]);
			}
		}
		ArrayList<int[]> validated_moves = new ArrayList<int[]>();
		for(int[] vector : vectors) {
			int dx = vector[0];
			int dy = vector[1];
			int[] possible_pos = new int[] {pos[0] + dx, pos[1] + dy };
			while(valid_moves(possible_pos)) {
				validated_moves.add(possible_pos);
				possible_pos = new int[] {possible_pos[0] + dx, possible_pos[1] + dy };
			}
		}
		return validated_moves;
	}
}
