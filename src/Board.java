import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Board {
	private Piece[][] grid;
	public static char[] ROYALS = new char[] { 'R', 'N', 'B', 'Q', 'K', 'B',
			'N', 'R' };

	public Board() {
		grid = new Piece[8][8];
		set_pieces();
	}
	
	public Board(Piece[][] in_grid) {
		grid = in_grid;
	}

	public int rowLen() {
		return grid.length;
	}

	public int colLen() {
		return grid[0].length;
	}

	public void set_piece(Piece piece, int[] pos) {
		grid[pos[0]][pos[1]] = piece;
	}
	
	public Piece get_piece(int[] pos) {
		int row = pos[0];
		int col = pos[1];
		return grid[row][col];
	}

	public Piece get_piece(int row, int col) {
		return grid[row][col];
	}

	public boolean check() {
		return in_check('b') || in_check('w');
	}

	public boolean checkmate() {
		return in_check_mate('b') || in_check_mate('w');
	}
	
	public boolean in_check(char color) {
		int[] king_pos = find_king(color);
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Piece piece = get_piece(i,j);
				if(piece.color == color) {
					ArrayList<int[]> piece_moves = piece.get_valid_moves();
					for(int[] move : piece_moves) {
						if(move[0] == king_pos[0] && move[1] == king_pos[1]) {
							return true;
						}
					}
				}
				
			}
		}
		return false;
	}
	
	private boolean in_check_mate(char color) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Piece piece = get_piece(i,j);
				if(piece.color == color) {
					ArrayList<int[]> moves = piece.get_valid_moves();
					if(moves.size() > 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private int[] find_king(char color) {
		for(int i = 0; i< 8; i++) {
			for(int j = 0; j <8 ; j++) {
				Piece piece = get_piece(i,j);
				if(piece.color == color && piece instanceof King) {
					return piece.pos;
				}
			}
		}
		return new int[] {-1, -1};
	}

	private void set_pieces() {
		set_pawns('w');
		set_pawns('b');
		set_royals('w');
		set_royals('b');
		fill_empty();
	}

	private void set_pawns(char color) {
		int row_idx;
		if (color == 'w') {
			row_idx = 1;
		} else if (color == 'b') {
			row_idx = 6;
		} else {
			row_idx = 3;
		}
		for (int i = 0; i < grid[row_idx].length; i++) {
			int pos[] = new int[] { row_idx, i };
			grid[row_idx][i] = new Pawn(color, pos, this);
		}
	}

	private void set_royals(char color) {
		int row_idx;
		if (color == 'w') {
			row_idx = 0;
		} else if (color == 'b') {
			row_idx = 7;
		} else {
			row_idx = 4;
		}
		for (int i = 0; i < grid[row_idx].length; i++) {
			int pos[] = new int[] { row_idx, i };
			grid[row_idx][i] = royal_piece(ROYALS[i], color, pos);
		}
	}

	private Piece royal_piece(char piece, char color, int[] pos) {
		Piece new_piece;
		switch (piece) {
		case 'R':
			new_piece = new Rook(color, pos, this);
			break;
		case 'B':
			new_piece = new Bishop(color, pos, this);
			break;
		case 'N':
			new_piece = new Knight(color, pos, this);
			break;
		case 'Q':
			new_piece = new Queen(color, pos, this);
			break;
		case 'K':
			new_piece = new King(color, pos, this);
			break;
		default:
			new_piece = new Pawn(color, pos, this);
		}
		return new_piece;

	}

	private void fill_empty() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == null) {
					int[] in_pos = new int[] {i,j};
					grid[i][j] = new NullPiece(' ', in_pos, this);
				}

			}
		}
	}

	public boolean make_move(int[] start, int[] finish, char color) {
		Piece start_piece = get_piece(start);
		if(start_piece.color == color) {
			ArrayList<int[]> move_set = start_piece.move();
			if(Piece.move_includes(finish, move_set)) {
				test_move(start,finish,color);
				return true;
			} 
		}
		return false;
	}
	
	public void test_move(int[] start, int[] finish, char color) {
		if(get_piece(finish).occupied) {
			set_piece(new NullPiece(), finish);
		}
		Piece start_piece = get_piece(start);
		start_piece.set_pos(finish);
		Piece end_piece = get_piece(finish);
		end_piece.set_pos(start);
		set_piece(end_piece, start);
		set_piece(start_piece, finish);
		
	}
	
	

	protected boolean in_bounds(int[] pos) {
		int row = pos[0];
		int col = pos[1];
		return (row >= 0 && row < 8 && col >= 0 && col < 8);
	}
	

	
	public Board dup() {
		Piece[][] new_grid = new Piece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				new_grid[i][j] = grid[i][j];
			}
		}
		Board new_board = new Board(new_grid);
		for(int k = 0; k < 8; k++) {
			for(int l = 0; l < 8; l++) {
				int[] new_pos = new int[] {k,l};
				Piece old_piece = new_board.get_piece(k,l);
				Class<?> copy_class = null;
				try {
					copy_class = Class.forName(old_piece.getClass().getName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Constructor<?> cons = null;
				try {
					cons = copy_class.getConstructor(char.class, int[].class, Board.class);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Piece new_piece = null;
				try {
					int[] pos2 = new int[] {old_piece.pos[0], old_piece.pos[1]};
					new_piece = (Piece) cons.newInstance(old_piece.color, pos2, new_board);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new_board.set_piece(new_piece, new_pos);
			}
		}
		return new_board;
	};

	
}
