/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import annotations.OnlyForTesting;

import models.Cube.CubeState;

/**
 * @author Lukas
 *
 */
public class Board 
{
	private Cube[][][] board;
	
	private int[] center;
	
	private float angleX = 0;
	private float angleY = 0;
	private float angleStep = 1.75f;
	
	//Constrains for the dimensions of the board
	private final int maxZ;
	private final int maxX;
	private final int maxY;
	
	private final static int defaultMaxX = 10;
	private final static int defaultMaxY = 10;
	private final static int defaultMaxZ = 10;
	
	private ArrayList<Cube> possiblePositions;
	private ArrayList<Cube> realCubes;
	private ArrayList<Cube> occupiedCubes;
	
	private Cube currentSelectedCube;
	private FaceDirection currentSelectedFaceDirection;
	
	public enum SelectionMode
	{
		CUBEMode,
		FACEMode;
	}
	
	private SelectionMode currentSelectionMode = SelectionMode.CUBEMode;
	
	/*
	 * This constructor shouldn't used in the final game!
	 */
	public Board(int maxX, int maxY, int maxZ) 
	{
		assert(maxX>0 && maxY>0 && maxZ>0);
		
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		
		this.possiblePositions = new ArrayList<Cube>();
		this.realCubes = new ArrayList<Cube>();
		this.occupiedCubes = new ArrayList<Cube>();
		
		this.board = new Cube[this.maxX+1][this.maxY+1][this.maxZ+1];
		
		for(int x = 0; x<this.maxX+1;x++)
		{
			for(int y = 0; y<this.maxY+1; y++)
			{
				for(int z = 0; z<this.maxZ+1; z++)
				{
					Cube cube = new Cube(x,y,z,Cube.CubeState.LOCKED);
					
					this.board[x][y][z] = cube;
				}
			}
		}
		
		this.updateCenter();
		this.resetCurrentSelectedCube();
	}
	
	public Board(ArrayList<Cube> startCubes)
	{
		this(defaultMaxX,defaultMaxY,defaultMaxZ);
		
		this.addBasicCubes(startCubes);
		this.resetCurrentSelectedCube();
	}
	
	private void resetCurrentSelectedCube()
	{
		if(!this.possiblePositions.isEmpty())
		{
			this.setSelectedCube(this.possiblePositions.get(0));
			this.resetCurrentSelectedFaceDirection();
		}
	}
	
	private void resetCurrentSelectedFaceDirection()
	{
		ArrayList<Cube> neighbours = this.getNeighbourCubesOf(this.currentSelectedCube);
		
		for(Cube neighbour:neighbours)
		{
			if(!neighbour.getState().equals(CubeState.REAL) && !neighbour.getState().equals(CubeState.LOCKED))
			{
				FaceDirection neighbourFaceDirection = this.currentSelectedCube.getNeighbourDirection(neighbour);
				FaceDirection commonFace = neighbourFaceDirection.getOppositeDirection();
				
				if(neighbour.isFaceEmpty(commonFace) && this.currentSelectedCube.isFaceEmpty(neighbourFaceDirection))
				{
					this.currentSelectedFaceDirection = commonFace;
					System.out.println("current selected face is " + neighbourFaceDirection);
					this.currentSelectedCube.setSelectedFace(neighbourFaceDirection);
					
					break;
				}
			}
		}
	}
	
	private ArrayList<Cube> getNeighbourCubesOf(Cube cube)
	{
		return this.getNeighboursCube(cube.getCoordinates());
	}
	
	private void setSelectedCube(Cube newSelectedCube)
	{
		if(this.currentSelectedCube != null)
		{
			this.currentSelectedCube.setSelected(false);
		}
		
		if(newSelectedCube != null)
		{
			newSelectedCube.setSelected(true);
			this.currentSelectedCube = newSelectedCube;
			System.out.println("selected cube is " + newSelectedCube);
		}
	}
	
	private void updateCenter()
	{
		int minX = this.maxX;
		int maxX = 0;
		
		int minY = this.maxY;
		int maxY = 0;
		
		int minZ = this.maxZ;
		int maxZ = 0;
		
		if(this.realCubes.isEmpty())
		{
			this.center = new int[3];
			
			this.center[0] = 0;
			this.center[1] = 0;
			this.center[2] = 0;
		}
		else
		{
			for(Cube c:this.realCubes)
			{
				int[] coordCube = c.getCoordinates();
				
				int xCube = coordCube[0];
				int yCube = coordCube[1];
				int zCube = coordCube[2];
				
				if(xCube<minX)
				{
					minX = xCube;
				}
				
				if(xCube>maxX)
				{
					maxX = xCube;
				}
				
				if(yCube<minY)
				{
					minY = yCube;
				}
				
				if(yCube>maxY)
				{
					maxY = yCube;
				}
				
				if(zCube<minZ)
				{
					minZ = zCube;
				}
				
				if(zCube>maxZ)
				{
					maxZ = zCube;
				}
			}
			
			this.center = new int[3];
			this.center[0] = (int) Math.ceil((maxX-minX)/2.0);
			this.center[1] = (int) Math.ceil((maxY-minY)/2.0);
			this.center[2] = (int) Math.ceil((maxZ-minZ)/2.0);
		}
	}
	
	//PRIVATE
	/**
	 * This method is only for the constructor to initialize real cubes!
	 * add cubes to the board.
	 */
	public void addBasicCubes(ArrayList<Cube> startCubes)
	{
		for(Cube cube:startCubes)
		{
			int[] coord = cube.getCoordinates();
			
			int x = coord[0];
			int y = coord[1];
			int z = coord[2];
			
			Cube cubeInBoard = this.board[x][y][z];
			
			if(!cubeInBoard.getState().equals(CubeState.REAL) && !this.realCubes.contains(cube))
			{
				this.addCube(cube, x, y, z);
//				System.out.println("cube " + cube +" added to the board!");
			}
			else
			{
				System.out.println("cube " + cube +" couldn't add to the board!");
				//TODO exception
			}
		}
	}
	
	/**
	 * add a cube at a specific place on the board.
	 * @param cube
	 * @param x
	 * @param y
	 * @param z
	 */
	public void addCubeAt(Cube cube, int x, int y, int z)
	{
		if(x>=0 && x<=maxX && y>=0 && y<=maxY && z>=0 && z<=maxZ)
		{
			Cube c = this.board[x][y][z];
			
			if(this.possiblePositions.remove(c) && !this.realCubes.contains(cube))
			{
				addCube(cube,x,y,z);
			}
			else
			{
				//TODO throw exception
				System.out.println("ERROR--------------------not a possible position!-----------------------");
			}
		}
		else
		{
			//TODO throw exception
			System.out.println("ERROR: out of range");
		}
	
	}
	
	//PRIVATE
	/**
	 * add a cube at a specific position on the board. This method is only called by addBasicCubes(...) or addCubeAt(...) method!
	 */
	public void addCube(Cube cube, int x, int y, int z)
	{
		if(x>=0 && x<=maxX && y>=0 && y<=maxY && z>=0 && z<=maxZ)
		{
			Cube oldCube = this.board[x][y][z];
			
			if(this.possiblePositions.contains(oldCube))
			{
				this.possiblePositions.remove(oldCube);
			}
			
//			System.out.println("ADD CUBE " + cube);
			
			this.board[x][y][z] = cube;
			 
			cube.setState(CubeState.REAL);
			this.realCubes.add(cube);
			//TODO set position of the cube! 
			this.updateNeighbours(cube);
		}
		else
		{
			System.out.println("ERROR: NOT POSSIBLE TO ADD CUBE " + cube);
			//TODO throw exception
		}
		
		this.updateCenter();
	}
	
	//PRIVATE
	public void updateNeighbours(Cube cube)
	{
//		System.out.println("Update neighbours of cube " + cube);
		int[] pos = cube.getCoordinates();
		
		ArrayList<Cube>neighbours = this.getNeighboursCube(pos);
		for(Cube neighbour:neighbours)
		{
			this.updateCube(neighbour);
		}
	}
	
	private void updateCube(Cube cube)
	{
//		System.out.println("Update cube " + cube);
		
			if(!this.realCubes.contains(cube))
			{
				ArrayList<Cube>neighbours = this.getNeighboursCube(cube.getCoordinates());
				
				boolean realNeighbourFound = false;
				boolean noRealNeighbourHasOnCommonFaceAPlayer = true;
				for(Cube neighbour:neighbours)
				{
					
					if(neighbour.getState().equals(CubeState.REAL))
					{
						FaceDirection neighbourDirection  = cube.getNeighbourDirection(neighbour);
						FaceDirection commonFace  = neighbourDirection.getOppositeDirection();
						boolean hasNeighbourOnCommonFaceNoPlayers = neighbour.isFaceEmpty(commonFace);
						
						
//						System.out.println("neighbourDirection is " + neighbourDirection);
//						System.out.println("commonFace is " + commonFace);
//						System.out.println("neighboursCommonFace is empty?:" + hasNeighbourOnCommonFaceNoPlayers);
//						
						if(!hasNeighbourOnCommonFaceNoPlayers)
						{
							noRealNeighbourHasOnCommonFaceAPlayer = false;
							System.out.println("OCCUPIED: Neigbour: " + neighbour + "of cube " +cube +" has a player on the common face " + commonFace);
						} 
						
						realNeighbourFound = true;
//						break;
					}
				}
				
				if(realNeighbourFound && noRealNeighbourHasOnCommonFaceAPlayer)
				{
					cube.setState(CubeState.PLACEHOLDER);
//					System.out.println("cube is placeholder now");
					
					if(!this.possiblePositions.contains(cube))
					{
//						System.out.println("add cube to placeholders");
						this.possiblePositions.add(cube);
					}
					
					if(this.occupiedCubes.contains(cube))
					{
						this.occupiedCubes.remove(cube);
					}
				}
				else if(realNeighbourFound && !noRealNeighbourHasOnCommonFaceAPlayer)
				{
					cube.setState(CubeState.OCCUPIED);
					System.out.println("cube ( " + cube + " ) is occupied now");
					
					if(this.possiblePositions.contains(cube))
					{
//						System.out.println("remove cube from placeholders");
						this.possiblePositions.remove(cube);
						
					}
					System.out.println("add cube " + cube + " to occupiedCubes");
					this.occupiedCubes.add(cube);
				
				}
				else if(!realNeighbourFound)
				{
					cube.setState(CubeState.LOCKED);
//					System.out.println("cube is locked now");
					
					if(this.possiblePositions.contains(cube))
					{
//						System.out.println("remove cube from placeholders");
						this.possiblePositions.remove(cube);
					}
				}
				
			
			}
			else
			{
//				System.out.println("The neighbour cube is a real cube. You can't change its state indirect!");
			}
		
	}
	
	//PRIVATE
	public ArrayList<Cube> getNeighboursCube(int[] posCurrentCube)
	{
		ArrayList<int[]> neighboursCoordinates = this.getNeighboursCoordinates(posCurrentCube);
		
		ArrayList<Cube> neighbours = new ArrayList<Cube>();
		
		for(int[] coordinates:neighboursCoordinates)
		{
			int xNeighbour = coordinates[0];
			int yNeighbour = coordinates[1];
			int zNeighbour = coordinates[2];
			neighbours.add(this.board[xNeighbour][yNeighbour][zNeighbour]);
//			System.out.println("ADD NEIGHBOUR CUBE " + this.board[xNeighbour][yNeighbour][zNeighbour]);
		}
		
		return neighbours;
	}
	
	//PRIVATE
	/**
	 * returns all coordinates of the neighbours
	 * @param posCurrentCube
	 * @return
	 */
	public ArrayList<int[]> getNeighboursCoordinates(int[] posCurrentCube)
	{
		int x = posCurrentCube[0];
		int y = posCurrentCube[1];
		int z = posCurrentCube[2];
		
		ArrayList<int[]> neighbours = new ArrayList<int[]>();
		
		//LEFT
		if(y-1>=0 && y-1<maxY)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y-1;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//RIGHT
		if(y+1>=0 && y+1<maxY)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
		
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y+1;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//UP
		if(z+1>=0 && z+1<maxZ)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z+1;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//DOWN
		if(z-1>=0 && z-1<maxZ)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z-1;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//FRONT
		if(x+1>=0 && x+1<maxX)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x+1;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//BACK
		if(x-1>=0 && x-1<maxX)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x-1;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		return neighbours;
	}
	
	@OnlyForTesting
	public Cube getCubeAt(int x,int y,int z)
	{
		return this.board[x][y][z];
	}
	
	@OnlyForTesting
	public ArrayList<Cube> getPlaceholderCubes()
	{
		return this.possiblePositions;
	}
	
	@OnlyForTesting
	public ArrayList<Cube> getRealCubes()
	{
		return this.realCubes;
	}
	
	public int getMaxX()
	{
		return this.maxX;
	}
	
	public int getMaxY()
	{
		return this.maxY;
	}
	
	public int getMaxZ()
	{
		return this.maxZ;
	}
	
	public void showLayer(int z)
	{
		System.out.println("Layer " + z);
		
		String s = "y: ";
		String s2 = "x  ";
		
		for(int y = 0; y<this.maxY;y++)
		{
			s+=" | " + y + " | ";
			s2+=" | - | ";
		}
		
		System.out.println(s);
		System.out.println(s2);
		
		for(int x = 0; x<this.maxX;x++)
		{
			System.out.print("" + x +"  ");
			for(int y = 0; y<maxY;y++)
			{
				System.out.print(" | " + this.board[x][y][z].getState().getShort() + " | " ); 
			}
			System.out.println("");
		}
	}
                                                                                                                                     
	public void removeCubeAt(int x, int y, int z) 
	{
		if(x>=0 && x<=maxX && y>=0 && y<=maxY && z>=0 && z<=maxZ)
		{
			Cube oldCube = this.board[x][y][z];
			
			if(this.possiblePositions.contains(oldCube))
			{
//				System.out.println(":::::::: CURRENT STATE: " + oldCube.getState() +" is in possiblePositionsList");
				this.possiblePositions.remove(oldCube);
				oldCube.setState(CubeState.LOCKED);
			}
			
			if(this.realCubes.contains(oldCube))
			{
//				System.out.println(":::::::: CURRENT STATE: " + oldCube.getState() +" is in realsList");
				this.realCubes.remove(oldCube);
				oldCube.setState(CubeState.LOCKED);
			}
			
			this.updateCube(oldCube);
			this.updateNeighbours(oldCube);
		}
		else
		{
			System.out.println("ERROR: invalid position! No cube to remove!");
			//TODO throw exception
		}
		
		this.updateCenter();
	}

	public void update(int delta) 
	{
//		this.angle +=0.05f*delta ; //TODO 
		
		this.updateCenter();
//		this.realCubes.get(0).update(delta);
		this.updateRealCubes(delta);
		this.updatePlaceholderCubes(delta);
		this.updateOccupiedCubes(delta);
	}
	
	private void updateOccupiedCubes(int delta)
	{
		for(Cube c : this.occupiedCubes)
		{
			c.update(delta);
		}
	}
	
	private void updateRealCubes(int delta)
	{
		for(Cube c : this.realCubes)
		{
			c.update(delta);
		}
	}
	
	private void updatePlaceholderCubes(int delta)
	{
		for(Cube c : this.possiblePositions)
		{
			c.update(delta);
		}
	}
	
	
	public void movePlayer(Player player, FaceDirection direction, int number)
	{
		//TODO
	}
	
	public void setPlayer(Player player)
	{
		//TODO
	}

	//////////////
	//CONTROLLER//
	//////////////
	public void addSelectedCube()
	{
		if(this.currentSelectedCube.getState()==CubeState.PLACEHOLDER)
		{
			Cube cube = currentSelectedCube;
			
			this.addCubeAt(cube, cube.getBoardCoordinateX(), cube.getBoardCoordinateY(), cube.getBoardCoordinateZ());
		}
		else
		{
			System.out.println("-----ERROR: CAN NOT ADD CUBE TO BOARD!"); //TODO
		}
	}
	
	
	public void addAngle(float angleSignX, float angleSignY)
	{
		this.angleX += angleSignX*this.angleStep;
		this.angleY += angleSignY*this.angleStep;
	}
	
	public void changeSelection(int dx, int dy, int dz)
	{
		if(this.currentSelectionMode.equals(SelectionMode.CUBEMode))
		{
			this.changeSelectedCube(dx, dy, dz);
		}
		else
		{
			this.changeSelectedFace(dx,dy,dz);
		}
		
	}
	
	private void changeSelectedFace(int dx, int dy, int dz)
	{
		//TODO
	}

	private void changeSelectedCube(int dx,int dy, int dz)
	{
		if(this.currentSelectedCube != null)
		{
			int[] coordinatesCurrentSelectedCube = this.currentSelectedCube.getCoordinates();
			
			int currentX = coordinatesCurrentSelectedCube[0];
			int currentY = coordinatesCurrentSelectedCube[1];
			int currentZ = coordinatesCurrentSelectedCube[2];
			
			int newX = dx + currentX;
			int newY = dy + currentY;
			int newZ = dz + currentZ;
			
			///NEW
			Cube newCube = null;
			
			while(newCube==null || newCube.getState()==CubeState.LOCKED)
			{
				if(newX<0)
				{
					newX=this.maxX-1;
				}
				else if(newX>this.maxX-1)
				{
					newX=0;
				}

				if(newY<0)
				{
					newY=this.maxY-1;
				}
				else if(newY>this.maxY-1)
				{
					newY=0;
				}

				if(newZ<0)
				{
					newZ=this.maxZ-1;
				}
				else if(newZ>this.maxZ-1)
				{
					newZ=0;
				}

				newCube = this.board[newX][newY][newZ];

				newX +=dx;
				newY +=dy;
				newZ +=dz;
			}
			this.setSelectedCube(newCube);
		}
	}
	
	//////////
	//RENDER//
	//////////
	
	public void render()
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(this.center[0]*Cube.getWidth(),this.center[1]*Cube.getHeight(), this.center[2]*Cube.getDeepth());
		GL11.glRotatef(this.angleY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(this.angleX, 1.0f, 0.0f, 0.0f);
		GL11.glTranslatef(-this.center[0]*Cube.getWidth(),-this.center[1]*Cube.getHeight(), -this.center[2]*Cube.getDeepth());
		this.renderRealCubes();
		this.renderPlaceholderCubes();
		this.renderOccupiedCubes();
		this.renderBoard();
		
		GL11.glPopMatrix();
	}

	private void renderBoard()
	{
		// TODO Auto-generated method stub
		
	}

	private void renderOccupiedCubes() 
	{
//		this.possiblePositions.get(0).render();
		for(Cube c: this.occupiedCubes)
		{
			c.render();
		}
		
	}
	
	private void renderPlaceholderCubes() 
	{
//		this.possiblePositions.get(0).render();
		for(Cube c: this.possiblePositions)
		{
			c.render();
		}
		
	}

	private void renderRealCubes() 
	{
//		this.realCubes.get(0).render();
		for(Cube c: this.realCubes)
		{
			c.render();
		}
		
	}
}
