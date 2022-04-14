package players;

import interfaces.IModel;
import interfaces.IPlayer;

/**
 * Implementing this player is an intermediate task.
 * See assignment instructions for what to do.
 * If not attempting it, just upload the file as it is.
 *
 * @author <s2029893>
 */
public class RoundRobinPlayer implements IPlayer
{
	// A reference to the model, which you can use to get information about
	// the state of the game. Do not use this model to make any moves!
	private IModel model;
	public int index;
	// The constructor is called when the player is selected from the game menu.
	public RoundRobinPlayer()
	{
		// You can leave this empty.
	}
	
	// This method is called when a new game is started or loaded.
	// You can use it to perform any setup that may be required before
	// the player is asked to make a move. The second argument tells
	// you if you are playing as player 1 or player 2.
	public void prepareForGameStart(IModel model, byte playerId)
	{
		this.model = model;
		index = model.getGameSettings().nrCols;
		// Extend this method if required.
	}
	
	// This method is called to ask the player to take their turn.
	// The move they choose should be returned from this method.
	public int chooseMove()
	{
		// Until you have implemented this player, it will always concede.
		index++;
		Integer[] indicator = new Integer[model.getGameSettings().nrCols];
		for (int i = 0; i < model.getGameSettings().nrCols; i++) {
			int num = 0;
			for (int j = 0; j < model.getGameSettings().nrRows; j++) {
				if (model.getPieceIn(j, i) == 1 || model.getPieceIn(j, i) == 2) {
					num++;
				}
			}
			indicator[i] = num;
		}
		if (index < model.getGameSettings().nrCols) {
			while (indicator[index] == model.getGameSettings().nrRows) {
				index++;
			}
			return index;
		} else {
			index = 0;
			while (indicator[index] == model.getGameSettings().nrRows) {
				index++;
			}
			return index;
		}
	}
}
