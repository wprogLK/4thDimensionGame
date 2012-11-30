/**
 * 
 */
package models;

import org.lwjgl.opengl.GL11;

/**
 * @author Lukas
 *
 */
public class Player 
{
	public enum Colour
	{
		BLUE(0.0f,0.0f,1.0f,1.0f),
		RED(1.0f,0.0f,0.0f,1.0f),
		YELLOW(0.0f,1.0f,1.0f,1.0f),
		GREEN(1.0f,0.0f,1.0f,1.0f);
		
		private float colourRed;
		private float colourGreen;
		private float colourBlue;
		private float colourAlpha;
		
		private float[] colours;
		
		Colour(float red, float green, float blue, float alpha)
		{
			this.colourRed = red;
			this.colourBlue = blue;
			this.colourGreen = green;
			
			this.colourAlpha = alpha;
			
			this.colours = new float[4];
			this.colours[0] = this.colourRed;
			this.colours[1] = this.colourGreen;
			this.colours[2] = this.colourBlue;
			this.colours[3] = this.colourAlpha;
		}
		
		public float[] getColour()
		{
			return this.colours;
		}
	}
	
	private final Colour colour;
	private  String name;
	
	private int x;
	private int y;
	private int z;
	
	/**
	 * @param colorOfPlayer 
	 * @param name 
	 * 
	 */
	public Player(String name, Colour colour) 
	{
		this.name = name;
		this.colour = colour;
		
		this.x = -1;
		this.y = -1;
		this.z = -1;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Player.Colour getColour()
	{
		return this.colour;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getZ()
	{
		return this.z;
	}
	
	public void setPos(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////
	//VIEW//
	////////
	
	public void setColour()
	{
		float[] colour = this.colour.getColour();
		GL11.glColor4f(colour[0], colour[1], colour[2], colour[3]);
	}
	
	public void render()
	{
		//TODO
	}

}
