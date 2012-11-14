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
	private int height = 50;
	private int width = 50;
	private int deepth = 50;
	
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
		this.xCoordinate = xCoordiante*100;
		this.yCoordinate = yCoordinate*100;
		this.zCoordinate = zCoordinate*100;
		
		this.state = state;
	}
	
	public Cube(int xCoordiante, int yCoordinate, int zCoordinate)
	{
		this.xCoordinate = xCoordiante*100;
		this.yCoordinate = yCoordinate*100;
		this.zCoordinate = zCoordinate*100;
		
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
		int[] coordinates = {this.xCoordinate/100,this.yCoordinate/100,this.zCoordinate/100};
		
		return coordinates;
		
	}
	
	@Override
	public String toString()
	{
		return "CUBE: State: " + this.state + " at [ " + this.xCoordinate + " | " + this.yCoordinate + " | " + this.zCoordinate + " ]";
	}

	public void update(int delta)
	{
		rot +=0.15f*delta ; //TODO 
		
	}
	
	//////////
	//RENDER//
	//////////
	
	public void render() 
	{
		System.out.println("RENDERING CUBE...");
		GL11.glPushMatrix();
		GL11.glTranslated(100, 100, 0);
		GL11.glBegin(GL11.GL_QUADS);
		//Front:
//		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate, this.zCoordinate); //top left corner
//		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate); //top right corner
//		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate, this.zCoordinate-this.height); //lower left corner
//		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate-this.height); //lower right corner
		
//		//Right:
//		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate); //top right corner front
//		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate-this.height); //lower right corner front
//		GL11.glVertex3d(this.xCoordinate, this.yCoordinate+this.width, this.zCoordinate); //top right corner back
//		GL11.glVertex3d(this.xCoordinate, this.yCoordinate+this.width, this.zCoordinate-this.height); //lower right corner back
		
		GL11.glVertex3i(0, 0, 0); //A
		GL11.glVertex3i(100, 0, 0); //B
		GL11.glVertex3i(100, 100, 0); //C
		GL11.glVertex3i(0, 100, 0); //D
//		GL11.glVertex3i(150, 150, 150); //A
//		GL11.glVertex3i(250, 150, 150); //B
//		GL11.glVertex3i(250, 250, 150); //C
//		GL11.glVertex3i(150, 250, 150); //D
		
		GL11.glEnd();
		GL11.glPopMatrix();
////		GL11.glLoadIdentity();
//		GL11.glPushMatrix();
//		GL11.glTranslated(100, 100, 0);
//		GL11.glBegin(GL11.GL_QUADS);
//		//Front:
////		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate, this.zCoordinate); //top left corner
////		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate); //top right corner
////		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate, this.zCoordinate-this.height); //lower left corner
////		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate-this.height); //lower right corner
//		
////		//Right:
////		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate); //top right corner front
////		GL11.glVertex3d(this.xCoordinate+this.deepth, this.yCoordinate+this.width, this.zCoordinate-this.height); //lower right corner front
////		GL11.glVertex3d(this.xCoordinate, this.yCoordinate+this.width, this.zCoordinate); //top right corner back
////		GL11.glVertex3d(this.xCoordinate, this.yCoordinate+this.width, this.zCoordinate-this.height); //lower right corner back
//		
//	
//		
//		
//	
//		GL11.glVertex3i(150, 150, 150); //A
//		GL11.glVertex3i(250, 150, 150); //B
//		GL11.glVertex3i(250, 250, 150); //C
//		GL11.glVertex3i(150, 250, 150); //D
//		
//		GL11.glEnd();
//		GL11.glPopMatrix();
	}


}
