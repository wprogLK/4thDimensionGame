/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.swing.event.ListSelectionEvent;

import models.Board.SelectionMode;

import org.lwjgl.opengl.GL11;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * @author Lukas
 *
 */
public class Cube
{
	private boolean isSelected;
	
	//rel. cordinates (index in board) (board coordinates)
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
	
	private CubeFace faceUp; 
	private CubeFace faceDown; 
	private CubeFace faceLeft; 
	private CubeFace faceRight; 
	private CubeFace faceFront; 
	private CubeFace faceBack; 
	
	private ArrayList<CubeVertex> cubeVertices;
	
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
	private static int height = 10;
	private static int width = 10;
	private static int deepth = 10;
	
	public enum CubeState
	{
		LOCKED("L",0.0f,0.0f,0.0f,0.0f),
		PLACEHOLDER("P",0.0f,1.0f,0.0f,0.1f),
		REAL("R",0.0f,0.0f,1.0f,1.0f),
		OCCUPIED("O",1.0f,1.0f,1.0f,0.3f); //if one or more players are on a face of a neighbour cube
		
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
		this.initFaces();
		
		this.state = state;
		this.isSelected = false;
	}
	
	public Cube(int xCoordiante, int yCoordinate, int zCoordinate)
	{
		this.x = xCoordiante;
		this.y = yCoordinate;
		this.z = zCoordinate;
		
		this.initAbsCoordinates();
		this.initFaces();
		
		this.state = CubeState.REAL;
		this.isSelected = false;
	}
	
	private void initAbsCoordinates()
	{
		this.xCoordinate = this.x*width+this.distX*x;
		this.yCoordinate = this.y*height+this.distY*y;
		this.zCoordinate = this.z*deepth+this.distZ*z;
	}
	
	private void initFaces()
	{
		this.cubeVertices = new ArrayList<CubeVertex>();
		
		this.cubeVertices.add(new CubeVertex(this.xCoordinate, this.yCoordinate, this.zCoordinate, "LeftBackDown - A"));
		this.cubeVertices.add(new CubeVertex(this.xCoordinate, this.yCoordinate+this.height, this.zCoordinate, "LeftBackTop - B"));
		this.cubeVertices.add(new CubeVertex(this.xCoordinate+this.width, this.yCoordinate+this.height, this.zCoordinate, "RightBackTop - C"));
		this.cubeVertices.add(new CubeVertex(this.xCoordinate+this.width, this.yCoordinate, this.zCoordinate, "RightBackDown - D"));
		this.cubeVertices.add(new CubeVertex(this.xCoordinate+this.width, this.yCoordinate+this.height, this.zCoordinate+this.deepth, "RightFrontTop - E"));
		this.cubeVertices.add(new CubeVertex(this.xCoordinate+this.width, this.yCoordinate, this.zCoordinate+this.deepth, "RightFrontDown - F"));
		this.cubeVertices.add(new CubeVertex(this.xCoordinate, this.yCoordinate, this.zCoordinate+this.deepth, "LeftFrontDown - G"));
		this.cubeVertices.add(new CubeVertex(this.xCoordinate, this.yCoordinate+this.height, this.zCoordinate+this.deepth, "LeftFrontTop - H"));
	
		
		ArrayList<CubeVertex> tmpVert = new ArrayList<CubeVertex>();
		
		this.faceBack = new CubeFace(FaceDirection.BACK);
		
		tmpVert.add(this.cubeVertices.get(1));
		tmpVert.add(this.cubeVertices.get(2));
		tmpVert.add(this.cubeVertices.get(3));
		tmpVert.add(this.cubeVertices.get(0));
		this.faceBack.setVertices(tmpVert);
		tmpVert.clear();
		
		
		this.faceFront = new CubeFace(FaceDirection.FRONT);
		
		tmpVert.add(this.cubeVertices.get(7));
		tmpVert.add(this.cubeVertices.get(6));
		tmpVert.add(this.cubeVertices.get(5));
		tmpVert.add(this.cubeVertices.get(4));
		this.faceFront.setVertices(tmpVert);
		tmpVert.clear();
		
		
		this.faceLeft = new CubeFace(FaceDirection.LEFT);
		
		tmpVert.add(this.cubeVertices.get(0));
		tmpVert.add(this.cubeVertices.get(6));
		tmpVert.add(this.cubeVertices.get(7));
		tmpVert.add(this.cubeVertices.get(1));
		this.faceLeft.setVertices(tmpVert);
		tmpVert.clear();
		
		
		this.faceRight = new CubeFace(FaceDirection.RIGHT);
		
		tmpVert.add(this.cubeVertices.get(5));
		tmpVert.add(this.cubeVertices.get(3));
		tmpVert.add(this.cubeVertices.get(2));
		tmpVert.add(this.cubeVertices.get(4));
		this.faceRight.setVertices(tmpVert);
		tmpVert.clear();
		
		
		this.faceUp = new CubeFace(FaceDirection.UP);
		
		tmpVert.add(this.cubeVertices.get(1));
		tmpVert.add(this.cubeVertices.get(7));
		tmpVert.add(this.cubeVertices.get(4));
		tmpVert.add(this.cubeVertices.get(2));
		this.faceUp.setVertices(tmpVert);
		tmpVert.clear();
		
		
		this.faceDown = new CubeFace(FaceDirection.DOWN);
		
		tmpVert.add(this.cubeVertices.get(3));
		tmpVert.add(this.cubeVertices.get(5));
		tmpVert.add(this.cubeVertices.get(6));
		tmpVert.add(this.cubeVertices.get(0));
		this.faceDown.setVertices(tmpVert);
		tmpVert.clear();
	}
	
	public void addPlayer(Player player, FaceDirection direction)
	{
		switch(direction)
		{
			case UP:
			{
				this.faceUp.addPlayer(player);
				
				break;
			}
			case DOWN:
			{
				this.faceDown.addPlayer(player);
				
				break;
			}
			case LEFT:
			{
				this.faceLeft.addPlayer(player);
				
				break;
			}
			case RIGHT:
			{
				this.faceRight.addPlayer(player);
				
				break;
			}
			case FRONT:
			{
				this.faceFront.addPlayer(player);
				
				break;
			}
			case BACK:
			{
				this.faceBack.addPlayer(player);
				
				break;
			}
		}
	}
	
	public void removePlayer(Player player, FaceDirection direction)
	{
		switch(direction)
		{
			case UP:
			{
				this.faceUp.removePlayer(player);
				
				break;
			}
			case DOWN:
			{
				this.faceDown.removePlayer(player);
				
				break;
			}
			case LEFT:
			{
				this.faceLeft.removePlayer(player);
				
				break;
			}
			case RIGHT:
			{
				this.faceRight.removePlayer(player);
				
				break;
			}
			case FRONT:
			{
				this.faceFront.removePlayer(player);
				
				break;
			}
			case BACK:
			{
				this.faceBack.removePlayer(player);
				
				break;
			}
		}
	}
	
//	public boolean isUpFree()
//	{
//		return this.faceUp.isEmtpy();
//	}
//	
//	public boolean isDownFree()
//	{
//		return this.faceDown.isEmtpy();
//	}
//	
//	public boolean isLeftFree()
//	{
//		return this.faceLeft.isEmtpy();
//	}
//	
//	public boolean isRightFree()
//	{
//		return this.faceRight.isEmtpy();
//	}
//	
//	public boolean isFrontFree()
//	{
//		return this.faceFront.isEmtpy();
//	}
//	
//	public boolean isBackFree()
//	{
//		return this.faceBack.isEmtpy();
//	}
	
	public boolean isFaceEmpty(FaceDirection face)
	{
		switch(face)
		{
			case UP:
			{
				return this.faceUp.isEmtpy();
			}
			case DOWN:
			{
				return this.faceDown.isEmtpy();
			}
			case LEFT:
			{
				return this.faceLeft.isEmtpy();
			}
			case RIGHT:
			{
				return this.faceRight.isEmtpy();
			}
			case BACK:
			{
				return this.faceBack.isEmtpy();
			}
			case FRONT:
			{
				return this.faceFront.isEmtpy();
			}
			default:
			{
				System.out.println("Error: unknown faceDirection in isFaceEmpty-Method in Cube!"); //TODO throw an exception
				return true;
			}
		}
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
	
	public int getBoardCoordinateX()
	{
		return this.x;
	}
	
	public int getBoardCoordinateY()
	{
		return this.y;
	}
	
	public int getBoardCoordinateZ()
	{
		return this.z;
	}
	

	public static int getWidth()
	{
		return width;
	}
	
	public static int getHeight()
	{
		return height;
	}
	
	public static int getDeepth()
	{
		return deepth;
	}
	
	public boolean isSelected()
	{
		return this.isSelected;
	}
	
	public void setSelected(boolean value)
	{
		this.isSelected = value;
	}
	
	@Override
	public String toString()
	{
		return "CUBE: State: " + this.state + " at [ " + this.x + " | " + this.y + " | " + this.z + " ]";
	}

	public void update(int delta)
	{

	}
	
	private void resetAllFaces()
	{
		this.faceUp.setSelected(false);
		this.faceDown.setSelected(false);
		this.faceLeft.setSelected(false);
		this.faceRight.setSelected(false);
		this.faceFront.setSelected(false);
		this.faceBack.setSelected(false);
	}
	
	public void setSelectedFace(FaceDirection face) 
	{
		switch(face)
		{
			case UP:
			{
				this.resetAllFaces();
				this.faceUp.setSelected(true);
				break;
			}
			case DOWN:
			{
				this.resetAllFaces();
				this.faceDown.setSelected(true);
				break;
			}
			case LEFT:
			{
				this.resetAllFaces();
				this.faceLeft.setSelected(true);
				break;
			}
			case RIGHT:
			{
				this.resetAllFaces();
				this.faceRight.setSelected(true);
				break;
			}
			case BACK:
			{
				this.resetAllFaces();
				this.faceBack.setSelected(true);
				break;
			}
			case FRONT:
			{
				this.resetAllFaces();
				this.faceFront.setSelected(true);
				break;
			}
			default:
			{
				System.out.println("Error: unknown faceDirection in setSelected() in Cube!"); //TODO throw an exception
			}
		}
		
	}
		
	
	
	//////////
	//RENDER//
	//////////
	
	//TODO: don't render the side of a placeholdercube where the neighbour is a realcube! (doesn't look nice)
	
	public void render() 
	{
//		GL11.glPushMatrix(); //Push and Pop important otherwise the cubes aren't sync rotating!
	
		this.setColour();
		
//		GL11.glPopMatrix();
		
		this.faceBack.render();
		this.faceFront.render();
		this.faceDown.render();
		this.faceUp.render();
		this.faceRight.render();
		this.faceLeft.render();
	}
	
	private void setColour()
	{
		if(!this.isSelected)
		{
			float[] colour = this.state.getColour();
			GL11.glColor4f(colour[0], colour[1], colour[2], colour[3]);
		}
		else
		{
			GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); //Color for selected cube
		}
	}
	
	
	
	public FaceDirection getNeighbourDirection(Cube cube)
	{
		int neighbourX = cube.getBoardCoordinateX();
		int neighbourY = cube.getBoardCoordinateY();
		int neighbourZ = cube.getBoardCoordinateZ();
		
		if(this.x==neighbourX && this.y == neighbourY && this.z == neighbourZ+1)
		{
			return FaceDirection.FRONT;		//Neighbour is in front of this cube
		}
		else if(this.x==neighbourX && this.y == neighbourY && this.z == neighbourZ-1)
		{
			return FaceDirection.BACK;		//Neighbour is on the back of this cube
		}
		else if(this.x==neighbourX && this.y == neighbourY+1 && this.z == neighbourZ)
		{
			return FaceDirection.UP;		
		}
		else if(this.x==neighbourX && this.y == neighbourY-1 && this.z == neighbourZ)
		{
			return FaceDirection.DOWN;		
		}
		else if(this.x==neighbourX+1 && this.y == neighbourY && this.z == neighbourZ)
		{
			return FaceDirection.RIGHT;		
		}
		else if(this.x==neighbourX-1 && this.y == neighbourY && this.z == neighbourZ)
		{
			return FaceDirection.LEFT;		
		}
		else
		{
			System.out.println("ERROR: The cube " + cube + " is not a neighbour of " + this);
			return null; //TODO throw an exception, because the cube is not a neighbour of this cube!
		}
	}
	//////////////
	//FACE CLASS//
	//////////////
	
	private class CubeFace
	{
		private ArrayList<Player> playersOnFace;
		
		private final int DefaultMaxCapacity = 4;
		private int maxCapacity; 
		
		private FaceDirection direction;
		private boolean isSelected = false; 
		
		private CubeVertex[] vertices;
		
		public CubeFace(FaceDirection direction, int maxCapacity)
		{
			this.direction = direction;
			this.maxCapacity = maxCapacity;
			
			this.playersOnFace = new ArrayList<Player>();
			this.vertices = new CubeVertex[4];
		}
		
		public CubeFace(FaceDirection direction)
		{
			this.direction = direction;
			this.maxCapacity = this.DefaultMaxCapacity;
			
			this.playersOnFace = new ArrayList<Player>();
			this.vertices = new CubeVertex[4];
		}
		
		public void setSelected(boolean isSelected)
		{
			this.isSelected = isSelected;
		}
		
		public boolean isSelected()
		{
			return this.isSelected;
		}
		
		public void setVertices(ArrayList<CubeVertex> vertices)
		{
			vertices.toArray(this.vertices);
		}
		
		@Override
		public String toString()
		{
			return this.direction.name();
		}
		
		public boolean isFull()
		{
			return playersOnFace.size()>=this.maxCapacity;
		}
		
		public boolean isEmtpy()
		{
			return this.playersOnFace.isEmpty();
		}
		
		public int getNumberOfPlayers()
		{
			return this.playersOnFace.size();
		}
		
		public void update()
		{
			//TODO
		}
		
		public void addPlayer(Player player)
		{
			if(!this.playersOnFace.contains(player))
			{
				this.playersOnFace.add(player);
				
				//TODO update (check event? is face full? ... etc)
			}
		}
		
		public void removePlayer(Player player)
		{
			if(this.playersOnFace.contains(player))
			{
				this.playersOnFace.remove(player);
			}
		}
		
		public void render()
		{
			//TODO make a difference between selected and unselected face!
			if(this.isSelected)
			{
				GL11.glColor4f(1.0f,1.0f, 0.0f, 1.0f);
				System.out.println("render selected face!");
			}
			else
			{
//				System.out.println("DONT render selected face!");
			}
			
			if(!this.isEmtpy())
			{
				GL11.glColor4f(1.0f,0.0f, 1.0f, 1.0f);
				System.out.println("face is not empty");  //TODO IMPORTANT: DEBGU THIS!!!! The direction face is inverse (?)
			}
			//TODO render face & player
			GL11.glBegin(GL11.GL_QUADS);
				for(CubeVertex vertex:this.vertices)
				{
					vertex.render();
				}
			GL11.glEnd();
			
			for(int i = 0; i<this.playersOnFace.size();i++)
			{
				Player player = this.playersOnFace.get(i);
				
				player.setColour();
				
//				GL11.glPushMatrix();
//					player.render(); //TODO
//				GL11.glPopMatrix();
			}
		}
		
		public FaceDirection getFaceDirection()
		{
			return this.direction;
		}
	}
	
	private class CubeVertex
	{
		private int x;
		private int y;
		private int z;
		
		private String name;
		
		public CubeVertex(int x, int y, int z, String name)
		{
			this.x = x;
			this.y = y;
			this.z = z;
			
			this.name = name;
		}
		
		public void render()
		{
			GL11.glVertex3f(this.x, this.y, this.z);
		}
		
		@Override
		public String toString()
		{
			return this.name;
		}
		
		public void translateRender(int x, int y, int z)
		{
			GL11.glVertex3f(this.x+x, this.y+y, this.z+z);
		}
	}

	
}
