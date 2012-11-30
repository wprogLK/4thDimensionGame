package tests;

import java.util.Arrays;

import models.Game;
import models.Player;

import org.junit.Test;
import org.junit.runner.RunWith;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import exceptions.GameException;
import exceptions.InvalidColourException;

@RunWith(JExample.class)
public class GameTest 
{
	private Player playerA;
	private Player playerB;
	
	@Test
	public Game SimpleGameTest() 
	{
		Game game = new Game();
		return game;
	}
	
	//TODO
}
