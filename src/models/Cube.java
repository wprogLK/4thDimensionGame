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
	private int x;
	private int y;
	private int z;
	
	/*
	 * the coordinate system is:
	 *    y
	 *    | 
	 * 	  |
	 * 	  |
	 *    /---------x
	 *   /
	 *  /
	 * z
	 */
	
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
		this.x = xCoordiante;
		this.y = yCoordinate;
		this.z = zCoordinate;
		
		this.state = state;
	}
	
	public Cube(int xCoordiante, int yCoordinate, int zCoordinate)
	{
		this.x = xCoordiante;
		this.y = yCoordinate;
		this.z = zCoordinate;
		
		this.state = CubeState.REAL;
	}
	
	public int getCubeIndex()
	{
		return this.y; //it's y coordinate because the lane size is the yCoordinate (-> see board.class)
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
		int[] coordinates = {this.x,this.y,this.z};
		
		return coordinates;
		
	}
	
	@Override
	public String toString()
	{
		return "CUBE: State: " + this.state + " at [ " + this.x + " | " + this.y + " | " + this.z + " ]";
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
	
		GL11.glRotatef(rot, 0.0f, 0.0f, 1.0f);
		
		this.addFrontSide();
		this.addBackSide();
		this.addTopSide();
		this.addDownSide();
		this.addRightSide();
		this.addLeftSide();
		
	}
	
	private void addFrontSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
			this.addCornerLeftFrontTop(); //H
			this.addCornerLeftFrontDown(); //G
			this.addCornerRightFrontDown(); //F
			this.addCornerRightFrontTop(); //E
		GL11.glEnd();
	}
	
	private void addBackSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
			this.addCornerLeftBackTop(); //B
			 this.addCornerRightBackTop();//C
			 this.addCornerRightBackDown(); //D
			this.addCornerLeftBackDown(); //A
		GL11.glEnd();
	}
	
	private void addTopSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
			this.addCornerLeftBackTop(); //B
			this.addCornerLeftFrontTop(); //H
			this.addCornerRightFrontTop(); //E
			this.addCornerRightBackTop(); //C
		GL11.glEnd();
	}
	
	private void addDownSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
			this.addCornerRightBackDown(); //D
			this.addCornerRightFrontDown(); //F
			this.addCornerLeftFrontDown(); //G
			this.addCornerLeftBackDown(); //A
		GL11.glEnd();
	}
	
	private void addRightSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
			this.addCornerRightFrontDown(); //F
			this.addCornerRightBackDown(); //D
			this.addCornerRightBackTop(); //C
			this.addCornerRightFrontTop(); //E
		GL11.glEnd();
	}
	
	private void addLeftSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
			this.addCornerLeftBackDown(); //A
			this.addCornerLeftFrontDown(); //G
			this.addCornerLeftFrontTop(); //H
			this.addCornerLeftBackTop(); //B
		GL11.glEnd();
	}
	
	///////////
	//CORNERS//
	///////////
	
	private void addCornerLeftFrontTop() //H
	{
		GL11.glVertex3d(this.x, this.y+this.height, this.z+this.deepth);
	}

	private void addCornerLeftFrontDown() //G
	{
		GL11.glVertex3d(this.x, this.y, this.z+this.deepth);
	}
	
	private void addCornerRightFrontDown() //F
	{
		GL11.glVertex3d(this.x+this.width, this.y, this.z+this.deepth);
	}
	
	private void addCornerRightFrontTop() //E
	{
		GL11.glVertex3d(this.x+this.width, this.y+this.height, this.z+this.deepth);
	}
	
	private void addCornerLeftBackTop() //B
	{
		GL11.glVertex3d(this.x, this.y+this.height, this.z);
	}
	
	private void addCornerRightBackTop() //C
	{
		GL11.glVertex3d(this.x+this.width, this.y+this.height, this.z);
	}
	
	private void addCornerRightBackDown() //D
	{
		GL11.glVertex3d(this.x+this.width, this.y, this.z);
	}
	
	private void addCornerLeftBackDown() //A
	{
		GL11.glVertex3d(this.x, this.y, this.z);
	}
}
