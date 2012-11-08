/**
 * 
 */
package states;

import models.Game;
import interfaces.IState;

/**
 * @author Lukas
 *
 */
public abstract class State implements IState
{
	protected final Game game;
	
	/**
	 * 
	 */
	public State(Game game) 
	{
		this.game = game;
	}

}
