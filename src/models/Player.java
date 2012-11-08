/**
 * 
 */
package models;

/**
 * @author Lukas
 *
 */
public class Player 
{
	public enum Colour
	{
		BLUE,
		RED,
		YELLOW,
		GREEN;
	}
	
	private final Colour colour;
	private  String name;
	/**
	 * @param colorOfPlayer 
	 * @param name 
	 * 
	 */
	public Player(String name, Colour colour) 
	{
		this.name = name;
		this.colour = colour;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Player.Colour getColour()
	{
		return this.colour;
	}

}
