package tests;

import java.util.Arrays;

import models.Game;
import models.Player;

import org.junit.Test;
import org.junit.runner.RunWith;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import exceptions.InvalidColourException;

@RunWith(JExample.class)
public class GameTest 
{
	private Player playerA;
	
	@Test
	public Game SimpleGameTest() 
	{
		Game game = new Game();
		return game;
	}
	
	@Given("SimpleGameTest")
	public Game allColoursShouldBeAvailable(Game game)
	{
		assert(game.getPossibleColours().equals(Arrays.asList(Player.Colour.values())));
		return game;
	}
	
	@Given("allColoursShouldBeAvailable")
	public Game shouldCreatePlayerAWithColourBLUE(Game game)
	{
		try
		{
			this.playerA = game.createPlayer("Player A", Player.Colour.BLUE);
		}
		catch(InvalidColourException e)
		{
			assert(false); //TODO 
		}
		
		return game;
	}
	
	@Given("shouldCreatePlayerAWithColourBLUE")
	public Game currentPlayerShouldBePlayerA(Game game)
	{
		assert(game.getCurrentPlayer().equals(this.playerA));
		
		return game;
	}
	
	@Given("shouldCreatePlayerAWithColourBLUE")
	public Game gameShouldHaveOnePlayer(Game game)
	{
		assert(game.getNumberOfPlayers()==1);
		return game;
	}
	
	@Given("shouldCreatePlayerAWithColourBLUE")
	public Game colorBlueShouldNotBeAvailable(Game game)
	{
		assert(!game.getPossibleColours().contains(Player.Colour.BLUE));
		return game;
	}
	
	@Given("colorBlueShouldNotBeAvailable")
	@Test(expected=InvalidColourException.class)
	public Game tryCreateASecondPlayerWithColourBlueShouldThrowInvalidColourException(Game game) throws InvalidColourException
	{
		game.createPlayer("Player B", Player.Colour.BLUE);
		return game;
	}
//
//	@Given("tryCreateASecondPlayerWithColourBlueShouldThrowInvalidColourException")
//	public Game gameShouldHaveOnePlayerAfterFailToCreateASecondPlayer(Game game)
//	{
//		assert(game.getNumberOfPlayers()==1);
//		return game;
//	}
}
