/**
 * 
 */
package models;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

/**
 * @author Lukas
 *
 */
public class Cube
{
	//rel. cordinates (index in board)
	private int x;
	private int y;
	private int z;
	
	//abs. coordinates 
	private int xCoordinate;
	private int yCoordinate;
	private int zCoordinate;
	
	private int distX = 0;
	private int distY = 0;
	private int distZ = 0;
	
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
		LOCKED("L",0.0f,0.0f,0.0f,0.0f),
		PLACEHOLDER("P",0.0f,1.0f,0.0f,0.1f),
		REAL("R",0.0f,0.0f,1.0f,1.0f);
		
		private float colourRed;
		private float colourGreen;
		private float colourBlue;
		private float colourAlpha;
		
		private float[] colours;
		
		private String shortString = "   ";

		CubeState(String shortString, float colourRed, float colourGreen, float colourBlue, float alpha)
		{
			this.shortString = shortString;
			this.colourRed = colourRed;
			this.colourBlue = colourBlue;
			this.colourGreen = colourGreen;
			this.colourAlpha = alpha;
			
			this.colours = new float[4];
			this.colours[0] = this.colourRed;
			this.colours[1] = this.colourGreen;
			this.colours[2] = this.colourBlue;
			this.colours[3] = this.colourAlpha;
		}
		
		public String getShort() 
		{
			return this.shortString;
		}
		
		public float[] getColour()
		{
			return this.colours;
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
		
		this.initAbsCoordinates();
		
		this.state = state;
	}
	
	public Cube(int xCoordiante, int yCoordinate, int zCoordinate)
	{
		this.x = xCoordiante;
		this.y = yCoordinate;
		this.z = zCoordinate;
		
		this.initAbsCoordinates();
		
		this.state = CubeState.REAL;
	}
	
	private void initAbsCoordinates()
	{
		this.xCoordinate = this.x*this.width+this.distX*x;
		this.yCoordinate = this.y*this.height+this.distY*y;
		this.zCoordinate = this.z*this.deepth+this.distZ*z;
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
		GL11.glPushMatrix(); //Push and Pop important otherwise the cubes aren't sync rotating!
		
		GL11.glRotatef(rot, 0.0f, 1.0f, 0.0f);
		
		this.setColour();
		
		this.addFrontSide();
		this.addBackSide();
		this.addTopSide();
		this.addDownSide();
		this.addRightSide();
		this.addLeftSide();
		
		GL11.glPopMatrix();
	}
	
	private void addFrontSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
		this.setColour();
			this.addCornerLeftFrontTop(); //H
			this.addCornerLeftFrontDown(); //G
			this.addCornerRightFrontDown(); //F
			this.addCornerRightFrontTop(); //E
		GL11.glEnd();
	}
	
	private void addBackSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
		this.setColour();
			this.addCornerLeftBackTop(); //B
			 this.addCornerRightBackTop();//C
			 this.addCornerRightBackDown(); //D
			this.addCornerLeftBackDown(); //A
		GL11.glEnd();
	}
	
	private void addTopSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
		this.setColour();
			this.addCornerLeftBackTop(); //B
			this.addCornerLeftFrontTop(); //H
			this.addCornerRightFrontTop(); //E
			this.addCornerRightBackTop(); //C
		GL11.glEnd();
	}
	
	private void addDownSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
		this.setColour();
			this.addCornerRightBackDown(); //D
			this.addCornerRightFrontDown(); //F
			this.addCornerLeftFrontDown(); //G
			this.addCornerLeftBackDown(); //A
		GL11.glEnd();
	}
	
	private void addRightSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
		this.setColour();
			this.addCornerRightFrontDown(); //F
			this.addCornerRightBackDown(); //D
			this.addCornerRightBackTop(); //C
			this.addCornerRightFrontTop(); //E
		GL11.glEnd();
	}
	
	private void addLeftSide()
	{
		GL11.glBegin(GL11.GL_QUADS);
		this.setColour();
			this.addCornerLeftBackDown(); //A
			this.addCornerLeftFrontDown(); //G
			this.addCornerLeftFrontTop(); //H
			this.addCornerLeftBackTop(); //B
		GL11.glEnd();
	}
	
	private void setColour()
	{
		float[] colour = this.state.getColour();
		GL11.glColor4f(colour[0], colour[1], colour[2], colour[3]);
	}
	
	///////////
	//CORNERS//
	///////////
	
	private void addCornerLeftFrontTop() //H
	{
		GL11.glVertex3d(this.xCoordinate, this.yCoordinate+this.height, this.zCoordinate+this.deepth);
	}

	private void addCornerLeftFrontDown() //G
	{
		GL11.glVertex3d(this.xCoordinate, this.yCoordinate, this.zCoordinate+this.deepth);
	}
	
	private void addCornerRightFrontDown() //F
	{
		GL11.glVertex3d(this.xCoordinate+this.width, this.yCoordinate, this.zCoordinate+this.deepth);
	}
	
	private void addCornerRightFrontTop() //E
	{
		GL11.glVertex3d(this.xCoordinate+this.width, this.yCoordinate+this.height, this.zCoordinate+this.deepth);
	}
	
	private void addCornerLeftBackTop() //B
	{
		GL11.glVertex3d(this.xCoordinate, this.yCoordinate+this.height, this.zCoordinate);
	}
	
	private void addCornerRightBackTop() //C
	{
		GL11.glVertex3d(this.xCoordinate+this.width, this.yCoordinate+this.height, this.zCoordinate);
	}
	
	private void addCornerRightBackDown() //D
	{
		GL11.glVertex3d(this.xCoordinate+this.width, this.yCoordinate, this.zCoordinate);
	}
	
	private void addCornerLeftBackDown() //A
	{
		GL11.glVertex3d(this.xCoordinate, this.yCoordinate, this.zCoordinate);
	}
}
