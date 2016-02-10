import java.util.*;
public class Piece {
	public char color;
	public Board board;
	public int[] pos;
	public boolean occupied;
	
	public Piece(char in_color, int[] in_pos, Board in_board) {
		color = in_color;
		pos = in_pos;
		board = in_board;
		occupied = true;
	}
	
	public void set_pos(int[] new_pos) {
		pos[0] = new_pos[0];
		pos[1] = new_pos[1];
	}
	
	public void set_pos(int row, int col) {
		pos[0] = row;
		pos[1] = col;
	}
	
	public Piece() {}
	public String toString() {
		return " " + color + " ";
	}
	
	protected boolean valid_moves(int[] position) {
		return false;
	}
	
	public ArrayList<int[]> get_valid_moves() {
		return new ArrayList<int[]>();
	}
	
	public static boolean move_includes(int[] pos_mov, ArrayList<int[]> move_set) {
		for(int[] move: move_set) {
			if(move[0] == pos_mov[0] && move[1] == pos_mov[1]) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<int[]> move() {
		ArrayList<int[]> possible_moves = get_valid_moves();
		ArrayList<int[]> checked_moves = new ArrayList<int[]>();
		for(int[] move : possible_moves) {
			Board test_board = board.dup();
			test_board.test_move(pos, move, color);
			if(!test_board.check()) {
				checked_moves.add(move);
			}
		}
		return possible_moves;
	}
}
