/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import annotations.OnlyForTesting;

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
//		LinkedList<String> linkedList = new LinkedList<String>();
//		linkedList.addFirst("C");
//		linkedList.addFirst("B");
//		linkedList.addFirst("B");
//		linkedList.addFirst("A");
//		
//		linkedList.addLast("D");
//		System.out.println("list before:\n"+Arrays.toString(linkedList.toArray()));
//		linkedList.
//		System.out.println("list after:\n"+Arrays.toString(linkedList.toArray()));
		
		
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
