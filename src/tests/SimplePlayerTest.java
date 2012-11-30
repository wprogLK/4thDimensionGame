package tests;

import models.Game;
import models.Player;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class SimplePlayerTest 
{
	@Test
	public Player SimplePlayerTest() 
	{
		Player player = new Player("wprogLK",Player.Colour.BLUE);
		return player;
	}
	
	@Given("SimplePlayerTest")
	public Player playerNameShouldBeWprogLK(Player player)
	{
		assert(player.getName().equals("wprogLK"));
		return player;
	}
	
	@Given("SimplePlayerTest")
	public Player playerColorShouldBeBlue(Player player)
	{
		assert(player.getColour().equals(Player.Colour.BLUE));
		return player;
	}

}
