/**
 * 
 */
package states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.event.ListSelectionEvent;

import annotations.OnlyForTesting;
import exceptions.GameException;
import exceptions.InvalidColourException;
import models.Game;
import models.Player;

/**
 * @author Lukas
 *
 */
public class StateSetupGame extends State 
{
	private ArrayList<Player.Colour> possibleColours;
	
	private ArrayList<Player> players;
	
	/**
	 * @param game
	 */
	public StateSetupGame(Game game) 
	{
		super(game);
		
		this.possibleColours = new ArrayList<Player.Colour>(Arrays.asList(Player.Colour.values()));
		
		this.players = new ArrayList<Player>();
	}

	@Override
	public void startState() 
	{
		//TODO: add players
		randomizePlayers();
		this.game.addPlayers(this.players);
	}
	
	public void randomizePlayers()
	{
		Collections.shuffle(players);
		
	}
	
	@OnlyForTesting
	public ArrayList<Player.Colour> getPossibleColours() 
	{
		return this.possibleColours;
	}

	public Player createPlayer(String name, Player.Colour colourOfPlayer) throws InvalidColourException
	{
		if(this.possibleColours.contains(colourOfPlayer))
		{
			Player player = new Player(name,colourOfPlayer);

			this.players.add(player);
			
			this.possibleColours.remove(colourOfPlayer);
			
			return player;
		}
		else
		{
			throw new InvalidColourException();
		}
	}
	
	@OnlyForTesting
	public int getNumberOfPlayers() 
	{
		return this.players.size();
	}
	
	///////////////////
	//PRIVATE METHODS//
	///////////////////
}
