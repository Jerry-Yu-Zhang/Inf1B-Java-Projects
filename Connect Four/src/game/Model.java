package game;

import interfaces.IModel;
import util.GameSettings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the state of the game.
 * It has been partially implemented, but needs to be completed by you.
 *
 * @author <s2029893>
 */
public class Model implements IModel
{
	// A reference to the game settings from which you can retrieve the number
	// of rows and columns the board has and how long the win streak is.
	private GameSettings settings;
	public Integer[][] board;
	private int index;
	
	// The default constructor.
	public Model()
	{
		// You probably won't need this.
	}
	
	// A constructor that takes another instance of the same type as its parameter.
	// This is called a copy constructor.
	public Model(IModel model)
	{
		// You may (or may not) find this useful for advanced tasks.
	}
	
	// Called when a new game is started on an empty board.
	public void initNewGame(GameSettings settings)
	{
		this.settings = settings;
		board = new Integer[getGameSettings().nrRows][getGameSettings().nrCols];
		for (int i = 0; i < getGameSettings().nrRows; i++) {
			for (int j = 0; j < getGameSettings().nrCols; j++) {
				board[i][j] = 0;
			}
		}
		index = 0;
		// This method still needs to be extended.
	}
	
	// Called when a game state should be loaded from the given file.
	public void initSavedGame(String fileName)
	{
		// This is an advanced feature. If not attempting it, you can ignore this method.
		/*int row;
		int col;
		int streak;
		int nextPlayer;
		try {
			Scanner scanner = new Scanner(new File("saves/" + fileName));
			List<Integer> integers = new ArrayList<>();
			while(scanner.hasNext()) {
				row = scanner.nextInt();
				col = scanner.nextInt();
				streak = scanner.nextInt();
				nextPlayer = scanner.nextInt();
				board = new Integer[row][col];

				for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						board[i][j] = scanner.next();
					}
				}
				settings = new GameSettings(row, col, streak);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		int row;
		int col;
		int streak;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("saves/" + fileName));
			List<String> lines = new ArrayList<String>();
			String line;
			while((line = reader.readLine()) != null) {
				lines.add(line);
			}
			row = Integer.parseInt(lines.get(0));
			col = Integer.parseInt(lines.get(1));
			streak = Integer.parseInt(lines.get(2));
			board = new Integer[row][col];
			index = 0;
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					board[i][j] = Character.getNumericValue(getCharFromString(lines.get(4 + i), j));
				}
			}
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if (board[i][j] == 1 || board[i][j] == 2) {
						index++;
					}
				}
			}
			settings = new GameSettings(row, col, streak);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}



	}
	
	// Returns whether or not the passed in move is valid at this time.
	public boolean isMoveValid(int move)
	{
		// Assuming all moves are valid.
		int count = 0;
		if (move >= 0 && move < getGameSettings().nrCols && count < getGameSettings().nrRows) {
			for (int i = 0; i < getGameSettings().nrRows; i++) {
				if (board[i][move] == 1 || board[i][move] == 2) {
					count++;
				}
			}
		}
		if (move >= -1 && move < getGameSettings().nrCols && count < getGameSettings().nrRows) {
			return true;
		} else {
			return false;
		}
	}
	
	// Actions the given move if it is valid. Otherwise, does nothing.
	public void makeMove(int move)
	{
		if (move == -1) {
			if (getActivePlayer() == 1) {
				index = -1;
			}else {
				index = -2;
			}
		}else {
			for (int i = getGameSettings().nrRows; i > 0; i--) {
				if (getPieceIn((i - 1), move) == 0) {
					board[i - 1][move] = Integer.valueOf(getActivePlayer());
					index++;
					break;
				}
			}
		}
	}

	// Returns one of the following codes to indicate the game's current status.
	// IModel.java in the "interfaces" package defines constants you can use for this.
	// 0 = Game in progress
	// 1 = Player 1 has won
	// 2 = Player 2 has won
	// 3 = Tie (board is full and there is no winner)
	public byte getGameStatus()
	{
		// Assuming the game is never ending.
		Byte activePlayer;
		int hIndicator = 0;
		int vIndicator = 0;
		int d1Indicator = 0;
		int d2Indicator = 0;

		if (getActivePlayer() == 1) {
			activePlayer = 2;
		} else {
			activePlayer = 1;
		}
		// Test the horizontal streak.
		for (int i = getGameSettings().nrRows - 1; i >= 0; i--) {
			for (int j = 0; j <= (getGameSettings().nrCols - getGameSettings().minStreakLength); j++) {
				int hIndex = 0;
				for (int k = j; k < j + getGameSettings().minStreakLength; k++) {
					if (board[i][k] == Integer.valueOf(activePlayer)) {
						hIndex++;
					}
				}
				if (hIndex == getGameSettings().minStreakLength) {
					hIndicator = 1;
				}
			}
		}
		// Test the vertical streak.
		for (int i = 0; i < getGameSettings().nrCols; i++) {
			for (int j = getGameSettings().nrRows - 1; j >= (getGameSettings().minStreakLength - 1); j--) {
				int vIndex = 0;
				for (int k = j; k > (j - getGameSettings().minStreakLength); k--) {
					if (board[k][i] == Integer.valueOf(activePlayer)) {
						vIndex++;
					}
				}
				if (vIndex == getGameSettings().minStreakLength) {
					vIndicator = 1;
				}
			}
		}
		// Test the type 1 diagonal streak.
		for (int i = 0; i <= (getGameSettings().nrCols - getGameSettings().minStreakLength); i++) {
			for (int j = getGameSettings().nrRows - 1; j >= (getGameSettings().minStreakLength - 1); j--) {
				int d1Index = 0;
				int offset = 0;
				while (offset < getGameSettings().minStreakLength) {
					if (board[j - offset][i + offset] == Integer.valueOf(activePlayer)) {
						d1Index++;
					}
					offset++;
				}
				if (d1Index == getGameSettings().minStreakLength) {
					d1Indicator = 1;
				}
			}
		}
		// Test the type 2 diagonal streak.
		for (int i = (getGameSettings().nrCols - 1); i >= (getGameSettings().minStreakLength - 1); i--) {
			for (int j = getGameSettings().nrRows - 1; j >= (getGameSettings().minStreakLength - 1); j--) {
				int d2Index = 0;
				int offset = 0;
				while (offset < getGameSettings().minStreakLength) {
					if (board[j - offset][i - offset] == Integer.valueOf(activePlayer)) {
						d2Index++;
					}
					offset++;
				}
				if (d2Index == getGameSettings().minStreakLength) {
					d2Indicator = 1;
				}
			}
		}

		if (index == (getGameSettings().nrRows) * (getGameSettings().nrCols)) {
			return IModel.GAME_STATUS_TIE;
		}else if (index == -1 || (activePlayer == 2 && (hIndicator == 1 || vIndicator == 1 || d1Indicator == 1 || d2Indicator == 1))){
			return IModel.GAME_STATUS_WIN_2;
		}else if (index == -2 || (activePlayer == 1 && (hIndicator == 1 || vIndicator == 1 || d1Indicator == 1 || d2Indicator == 1))){
			return IModel.GAME_STATUS_WIN_1;
		}else {
			return IModel.GAME_STATUS_ONGOING;
		}
	}
	
	// Returns the number of the player whose turn it is.
	public byte getActivePlayer()
	{
		// Assuming it is always the turn of player 1.
		/*if (index % 2 == 0) {
			return 1;
		}else {
			return 2;
		}*/
		int count = 0;
		for (int i = 0; i < getGameSettings().nrRows; i++) {
			for (int j = 0; j < getGameSettings().nrCols; j++) {
				if (board[i][j] == 1 || board[i][j] == 2) {
					count++;
				}
			}
		}
		if (count % 2 == 0) {
			return 1;
		} else {
			return 2;
		}
	}
	
	// Returns the owner of the piece in the given row and column on the board.
	// Return 1 or 2 for players 1 and 2 respectively or 0 for empty cells.
	public byte getPieceIn(int row, int column)
	{
		// Assuming all cells are empty for now.
		if (board[row][column] == 2) {
			return 2;
		}else if (board[row][column] == 1) {
			return 1;
		}else {
			return 0;
		}
	}
	
	// Returns a reference to the game settings, from which you can retrieve the
	// number of rows and columns the board has and how long the win streak is.
	public GameSettings getGameSettings()
	{
		return settings;
	}
	
	// =========================================================================
	// ================================ HELPERS ================================
	// =========================================================================
	
	// You may find it useful to define some helper methods here.
	public static char getCharFromString(String str, int index)
	{
		return str.toCharArray()[index];
	}
}
