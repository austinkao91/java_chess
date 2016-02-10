import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	public String name;
	private Display display;
	private char color;
	public Player(String in_name, char in_color) {
		name = in_name;
		color = in_color;
	}
	
	public char get_color() {
		return color;
	}
	
	public void get_display(Display in_display) {
		display = in_display;
	}
	
	public void take_turn(){
		selection();
		player_prompt();
	}
	
	private boolean selection() {
		int[] end_pos = new int[2];
		int[] start_pos = new int[2];
		boolean confirm = false;
		while(!confirm) {
			start_pos = make_first_selection();
			display.show_move_options(start_pos);
			confirm = make_second_selection(start_pos);
			display.move_pos = new ArrayList<int[]>();
		}
		
		
		return true;
	}
	
	private int[] make_first_selection() {
		System.out.println("Making First Selection");
		player_prompt();
		
		Piece selected = new NullPiece();
		int[] input = new int[2];
		while(!selected.occupied || selected.color != this.color) {
			player_prompt();
			System.out.println("Please select a valid piece!");
			
			input = display.get_input();
			selected = display.board.get_piece(input);
		}
		display.set_selected(input);
		System.out.println("First Selection Made!");
		return input;
	}
	
	
	
	private boolean make_second_selection(int[] start_pos) {
		if(display.move_pos.size() == 0) { 
			System.out.println("Selected piece has no moves");
			return false; 
		}
		System.out.println("Making Second Selection");
		int[] input = new int[] {-1,-1};
		while(!display.is_move_pos(input)) {
			player_prompt();
			display.print_move_pos();
			input = display.get_input();
		}
		display.clear_selected();
		boolean check = display.board.make_move(start_pos, input, this.color);
		return check;
	}
	
	private void player_prompt(){ 
		display.render();
		System.out.println("It is " + this.color + " to move!");
		if(display.board.check()) {
			System.out.println(this.name + " is in check!");
		} else if(display.board.checkmate()) {
			System.out.println(this.name + " is in checkmate!");
		}
	}
}


