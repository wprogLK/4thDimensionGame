/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import annotations.OnlyForTesting;

import exceptions.InvalidColourException;

import models.Player.Colour;

/**
 * @author Lukas
 *
 */
public class Game 
{
	private ArrayList<Player.Colour> possibleColours;
	
	private Queue<Player> players;
	@OnlyForTesting
	private Player currentPlayer;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}

	
	/**
	 * 
	 */
	public Game() 
	{	
		this.possibleColours = new ArrayList<Player.Colour>(Arrays.asList(Player.Colour.values()));
		
		this.players = new LinkedList<Player>();
		this.currentPlayer = null;
	}


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
			
			return player;
		}
		else
		{
			throw new InvalidColourException();
		}
	}
	
	@OnlyForTesting
	public Player getCurrentPlayer()
	{
		if(this.currentPlayer==null)
		{
			this.currentPlayer = this.players.peek();
		}
		
		return this.currentPlayer;
	}
	
	public Player nextPlayer()
	{
		Player currentPlayer = this.players.poll();
		this.players.add(currentPlayer);
		this.currentPlayer = currentPlayer;
		
		return currentPlayer;
	}


	public int getNumberOfPlayers() 
	{
		return this.players.size();
	}
	
	///////////////////
	//PRIVATE METHODS//
	///////////////////

}
