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
	
	@Given("SimpleGameTest")
	public Game allColoursShouldBeAvailable(Game game)
	{
		assert(game.getPossibleColours().equals(Arrays.asList(Player.Colour.values())));
		return game;
	}
	
	@Given("allColoursShouldBeAvailable")
	public Game shouldCreatePlayerAWithColourBLUE(Game game) throws InvalidColourException
	{
		this.playerA = game.createPlayer("Player A", Player.Colour.BLUE);
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
	
	@Given("gameShouldHaveOnePlayer")
	@Test(expected=GameException.class)
	public void tryStartGameWithOnlyOnePlayerShouldThrowGameException(Game game) throws GameException
	{
		game.play();
	}
	
	@Given("colorBlueShouldNotBeAvailable")
	@Test(expected=InvalidColourException.class)
	public Game tryCreateASecondPlayerWithColourBlueShouldThrowInvalidColourException(Game game) throws InvalidColourException
	{
		game.createPlayer("Player B", Player.Colour.BLUE);
		return game;
	}
	
	@Given("gameShouldHaveOnePlayer")
	public Game shouldCreatePlayerBWithColorRED(Game game) throws InvalidColourException
	{
		this.playerB = game.createPlayer("Player B", Player.Colour.RED);
		return game;
	}
	
	@Given("shouldCreatePlayerBWithColorRED")
	public Game gameShouldHaveTwoPlayers(Game game)
	{
		assert(game.getNumberOfPlayers()==2);
		return game;
	}
	
	@Given("shouldCreatePlayerBWithColorRED")
	public Game colorRedShouldNotBeAvailable(Game game)
	{
		assert(!game.getPossibleColours().contains(Player.Colour.RED));
		return game;
	}
	
	@Given("gameShouldHaveTwoPlayers")
	public Game shouldPlayTheGame(Game game) throws GameException
	{
		game.play();
		return game;
	}
}
