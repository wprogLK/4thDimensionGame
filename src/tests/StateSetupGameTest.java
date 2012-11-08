package tests;

import java.util.Arrays;

import models.Game;
import models.Player;

import org.junit.Test;
import org.junit.runner.RunWith;

import states.StateSetupGame;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import exceptions.GameException;
import exceptions.InvalidColourException;

@RunWith(JExample.class)
public class StateSetupGameTest 
{
	private Player playerA;
	private Player playerB;
	
	@Test
	public StateSetupGame SimpleSetupGameTest() 
	{
		Game game = new Game();
		StateSetupGame state = new StateSetupGame(game);
		return state;
	}
	
	@Given("SimpleSetupGameTest")
	public StateSetupGame allColoursShouldBeAvailable(StateSetupGame state)
	{
		assert(state.getPossibleColours().equals(Arrays.asList(Player.Colour.values())));
		return state;
	}
	
	@Given("allColoursShouldBeAvailable")
	public StateSetupGame shouldCreatePlayerAWithColourBLUE(StateSetupGame state) throws InvalidColourException
	{
		this.playerA = state.createPlayer("Player A", Player.Colour.BLUE);
		return state;
	}
	
	@Given("shouldCreatePlayerAWithColourBLUE")
	public StateSetupGame gameShouldHaveOnePlayer(StateSetupGame state)
	{
		assert(state.getNumberOfPlayers()==1);
		return state;
	}
	
	@Given("shouldCreatePlayerAWithColourBLUE")
	public StateSetupGame colorBlueShouldNotBeAvailable(StateSetupGame state)
	{
		assert(!state.getPossibleColours().contains(Player.Colour.BLUE));
		return state;
	}
	
//TODO
//	@Given("gameShouldHaveOnePlayer")
//	@Test(expected=GameException.class)
//	public void tryStartGameWithOnlyOnePlayerShouldThrowGameException(Game game) throws GameException
//	{
//		game.play();
//	}
	
	@Given("colorBlueShouldNotBeAvailable")
	@Test(expected=InvalidColourException.class)
	public StateSetupGame tryCreateASecondPlayerWithColourBlueShouldThrowInvalidColourException(StateSetupGame state) throws InvalidColourException
	{
		state.createPlayer("Player B", Player.Colour.BLUE);
		return state;
	}
	
	@Given("gameShouldHaveOnePlayer")
	public StateSetupGame shouldCreatePlayerBWithColorRED(StateSetupGame state) throws InvalidColourException
	{
		this.playerB = state.createPlayer("Player B", Player.Colour.RED);
		return state;
	}
	
	@Given("shouldCreatePlayerBWithColorRED")
	public StateSetupGame gameShouldHaveTwoPlayers(StateSetupGame state)
	{
		assert(state.getNumberOfPlayers()==2);
		return state;
	}
	
	@Given("shouldCreatePlayerBWithColorRED")
	public StateSetupGame colorRedShouldNotBeAvailable(StateSetupGame state)
	{
		assert(!state.getPossibleColours().contains(Player.Colour.RED));
		return state;
	}
	
	@Given("gameShouldHaveTwoPlayers")
	public StateSetupGame shouldPlayTheGame(StateSetupGame state) throws GameException
	{
		state.play();
		return state;
	}
}
