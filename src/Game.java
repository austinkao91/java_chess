import java.io.IOException;
import java.lang.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Game {
	private Player[] player = new Player[2];
	public Board board;
	private Display display;
	public Game(String player1, String player2) {
		player[0] = new Player(player1, 'w');
		player[1] = new Player(player2, 'b');
		
		board = new Board();
		display = new Display(board);
	}
	
	public void play() {
		start_game();
		while(!over()) {
			render();
			take_turn();
			rotate_players();
		}
		game_over_message();
	}
	
	private void start_game() {
		System.out.println("Welcome! White player " + player[0].name + " starts!");
		player[0].get_display(display);
		player[1].get_display(display);
	}
	
	private void game_over_message() {
		System.out.println(player[0].name + " has put " + player[1].name + " in checkmate!" );
		System.out.println(player[0].get_color() + " has won!");
	}
	
	private void take_turn() {
		player[0].take_turn();
	}
	
	private void rotate_players() {
		Player temp = player[0];
		player[0] = player[1];
		player[1] = temp;
	} 
	
	private void render() {
		clearConsole();
		display.render();
	}
	
	private boolean over() {
		return board.checkmate();
	}
	
	private static void clearConsole() {
	    try {
	      final String os = System.getProperty("os.name");
	      if (os.contains("Windows")) {
	          Runtime.getRuntime().exec("cls");
	      } else {
	          Runtime.getRuntime().exec("clear");
	      }
	    } catch (final Exception e) {
	        System.out.print("something went wrong :(");
	    }
	  }
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Game game = new Game("totoro", "piggy");
		game.play();
		

	}
}
