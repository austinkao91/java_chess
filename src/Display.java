import java.util.*;
import java.io.*;
import java.awt.event.KeyEvent;
public class Display {
	ArrayList<int[]> move_pos;
	boolean selected;
	int[] selected_square;
	Board board;

	public Display(Board in_board) {
		board = in_board;

		move_pos = new ArrayList<int[]>();
		selected = false;
		selected_square = new int[2];
	}
	
	public int[] get_input() {
		Scanner sc = new Scanner(System.in);
		int[] input = new int[2];
		Piece selected = new NullPiece();
		int row = -1;
		int col = -1;
		do {
			
			try {
				System.out.println("Input row number between 0 and 7");
				row = sc.nextInt();
				
			} catch(InputMismatchException e) {
				System.out.println("Please input a valid number!");
			}
			
		} while((row < 0 || row > 7));
		
		do {
			
			try {
				System.out.println("Input col number between 0 and 7");
				col = sc.nextInt();
				
			} catch(InputMismatchException e) {
				System.out.println("Please input a valid number!");
			}
			
		} while((col < 0 || col > 7));
		input[0] = row;
		input[1] = col;
		return input;

	}
	
	public void render() {
		clearConsole();
		for(int i = 0; i < board.rowLen(); i ++ ) {
			StringBuilder rowString = new StringBuilder();
				for(int j = 0; j < board.colLen(); j++ ) {
				int[] position = new int[] {i,j};
				String piece_s = board.get_piece(position).toString();
				String color = colorize(i,j);
				rowString.append(color);
				rowString.append(piece_s);
			}
			System.out.println(rowString);
		}
		System.out.println("\u001B[40m");
	}
	
	public void show_move_options(int[] position) {
		Piece selected = board.get_piece(position);
		ArrayList<int[]> move_positions = selected.move();
		for(int[] move_position : move_positions) {
			move_pos.add(move_position);
		}
		render();
	}
	
	public boolean is_move_pos(int row, int col) {
		for(int[] position : move_pos) {
			if(position[0] == row && position[1] == col) {
				return true;
			}
		}
		return false;
	}
	public void print_move_pos() {
		System.out.println("Possible move coordinates are:");
		for(int[] pos : move_pos) {
			System.out.println(String.format("[ %d , %d ]", pos[0], pos[1]));
		}
		
	}
	public boolean is_move_pos(int[] possible_position) {
		int row = possible_position[0];
		int col = possible_position[1];
		for(int[] position : move_pos) {
			if(position[0] == row && position[1] == col) {
				return true;
			}
		}
		return false;
	}
	
	public void set_selected(int row, int col) {
		selected_square[0] = row;
		selected_square[1] = col;
	}
	
	public void set_selected(int[] pos) {
		selected_square[0] = pos[0];
		selected_square[1] = pos[1];
	}
	
	public void clear_selected() {
		selected_square[0] = -1;
		selected_square[1] = -1;
	}
	
	public String colorize(int row, int col) {
		String color;
		
		if(selected_square[0] == row && selected_square[1] == col) {
			color = "\u001B[45m";
		} else if(is_move_pos(row,col)) {
			color = "\u001B[43m";
		}else if((row+col)%2 == 0) {
			color = "\u001B[41m";
		} else {
			color = "\u001B[44m";
		}
		return color;
	}
	
	public final static void clearConsole(){
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
