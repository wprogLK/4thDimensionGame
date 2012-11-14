/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import annotations.OnlyForTesting;

import exceptions.BoardException;
import exceptions.GameException;
import exceptions.InvalidColourException;

/**
 * @author Lukas
 *
 */
public class Game 
{ 
	private Queue<Player> players;
	@OnlyForTesting
	private Player currentPlayer;
	 
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ArrayList<Cube> basicCubes = new ArrayList<Cube>();
		
		basicCubes.add(new Cube(2,2,2));
		
		Board board = new Board(basicCubes);
		
		board.showLayer(1);
		board.showLayer(2);
		board.showLayer(3);
	}
	
	/**
	 * 
	 */
	public Game() 
	{	
		this.players = new LinkedList<Player>();
		this.currentPlayer = null;
	}

	public void play() throws GameException
	{
		if(this.players.size()<2)
		{
			throw new GameException("You'r forever alone? You can't play this game with only yourself!");
		}
		else
		{
			this.start();
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
	
	public void addPlayers(ArrayList<Player> players) 
	{
		this.players.addAll(players);
		
	}

	///////////////////
	//PRIVATE METHODS//
	///////////////////
	
	private void start()
	{
		//TODO
	}

	
	
}
