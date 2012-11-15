/**
 * 
 */
package models;

import org.lwjgl.opengl.GL11;

/**
 * @author Lukas
 *
 */
public class Cube
{
	//Cordinates are the upperleftBehindCorner!
	private int xCoordinate;
	private int yCoordinate;
	private int zCoordinate;
	
	//For rendering
	private int height = 10;
	private int width = 10;
	private int deepth = 10;
	
	private float rot = 0;
	public enum CubeState
	{
		
		LOCKED("L"),
		PLACEHOLDER("P"),
		REAL("R");

		private String shortString = "   ";

		CubeState(String shortString)
		{
			this.shortString = shortString;
		}
		
		public String getShort() 
		{
			return this.shortString;
		}
	}
	
	private CubeState state;
	
	/**
	 * 
	 */
	public Cube(int xCoordiante, int yCoordinate, int zCoordinate, CubeState state)
	{
		this.xCoordinate = xCoordiante;
		this.yCoordinate = yCoordinate;
		this.zCoordinate = zCoordinate;
		
		this.state = state;
	}
	
	public Cube(int xCoordiante, int yCoordinate, int zCoordinate)
	{
		this.xCoordinate = xCoordiante;
		this.yCoordinate = yCoordinate;
		this.zCoordinate = zCoordinate;
		
		this.state = CubeState.REAL;
	}
	
	public int getCubeIndex()
	{
		return this.yCoordinate; //it's y coordinate because the lane size is the yCoordinate (-> see board.class)
	}
	
	public CubeState getState()
	{
		return this.state;
	}
	
	public void setState(CubeState state)
	{
		this.state = state;
	}

	public int[] getCoordinates()
	{
		int[] coordinates = {this.xCoordinate,this.yCoordinate,this.zCoordinate};
		
		return coordinates;
		
	}
	
	@Override
	public String toString()
	{
		return "CUBE: State: " + this.state + " at [ " + this.xCoordinate + " | " + this.yCoordinate + " | " + this.zCoordinate + " ]";
	}

	public void update(int delta)
	{
		rot +=0.05f*delta ; //TODO 
		
	}
	
	//////////
	//RENDER//
	//////////
	
	public void render() 
	{
		System.out.println("RENDERING CUBE...");
	
		GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
		
		//Front:
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate, this.zCoordinate); //top left corner
			GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate, this.zCoordinate-this.height); //lower left corner
			GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate-this.height); //lower right corner
			GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate); //top right corner
			
		GL11.glEnd();

		//Right:
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate); //top right corner front
		GL11.glVertex3d(this.xCoordinate, this.yCoordinate+this.width, this.zCoordinate); //top right corner back
		GL11.glVertex3d(this.xCoordinate, this.yCoordinate+this.width, this.zCoordinate-this.height); //lower right corner back
			GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate-this.height); //lower right corner front

		GL11.glEnd();
	}


}
