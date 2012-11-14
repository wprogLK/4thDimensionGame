/**
 * 
 */
package models;

import java.util.ArrayList;

import annotations.OnlyForTesting;

import models.Cube.CubeState;

/**
 * @author Lukas
 *
 */
public class Board 
{
	private Cube[][][] board;
	
	//Constrains for the dimensions of the board
	private final int maxZ;
	private final int maxX;
	private final int maxY;
	
	private final static int defaultMaxX = 10;
	private final static int defaultMaxY = 10;
	private final static int defaultMaxZ = 10;
	
	private ArrayList<Cube> possiblePositions;
	private ArrayList<Cube> realCubes;
	/*
	 * This constructor shouldn't used in the final game!
	 */
	public Board(int maxX, int maxY, int maxZ) 
	{
		assert(maxX>0 && maxY>0 && maxZ>0);
		
		this.maxX = maxX+1;
		this.maxY = maxY+1;
		this.maxZ = maxZ+1;
		
		this.possiblePositions = new ArrayList<Cube>();
		this.realCubes = new ArrayList<Cube>();
		
		this.board = new Cube[this.maxX][this.maxY][this.maxZ];
		
		for(int x = 0; x<this.maxX;x++)
		{
			for(int y = 0; y<this.maxY; y++)
			{
				for(int z = 0; z<this.maxZ; z++)
				{
					Cube cube = new Cube(x,y,z,Cube.CubeState.LOCKED);
					
					this.board[x][y][z] = cube;
				}
			}
		}
	}
	
	public Board(ArrayList<Cube> startCubes)
	{
		this(defaultMaxX,defaultMaxY,defaultMaxZ);
		
		this.addBasicCubes(startCubes);
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
		if(x>0 && x<=maxX && y>0 && y<=maxY && z>0 && z<=maxZ)
		{
			Cube c = this.board[x][y][z];
			
			if(this.possiblePositions.remove(c) && !this.realCubes.contains(cube))
			{
				addCube(cube,x,y,z);
			}
			else
			{
				//TODO throw exception
				System.out.println("not a possible position!");
			}
		}
		else
		{
			//TODO throw exception
			System.out.println("out of range");
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
			System.out.println("ADD CUBE " + cube);
			
			this.board[x][y][z] = cube;
			 
			cube.setState(CubeState.REAL);
			this.realCubes.add(cube);
			//TODO set position of the cube! 
			this.updateNeighbours(cube);
		}
		else
		{
			System.out.println("NOT POSSIBLE TO ADD CUBE " + cube);
			//TODO throw exception
		}
		
	}
	
	//PRIVATE
	public void updateNeighbours(Cube cube)
	{
		System.out.println("Update neighbours of cube " + cube);
		int[] pos = cube.getCoordinates();
		
		ArrayList<Cube>neighbours = this.getNeighboursCube(pos);
		for(Cube neighbour:neighbours)
		{
			
			this.updateCube(neighbour);
//			if(!this.realCubes.contains(neighbour))
//			{
//				ArrayList<Cube>neighboursOfNeighbour = this.getNeighboursCube(neighbour.getCoordinates());
//				
//				boolean realNeighbourFound = false;
//				
//				for(Cube neighbourNeighbour:neighboursOfNeighbour)
//				{
////					System.out.println("NeighbourNeighbour of cube " +neighbour + " has state " + neighbourNeighbour.getState());
//					
//					if(neighbourNeighbour.getState().equals(CubeState.REAL))
//					{
////						System.out.println("real neighbour found!");
//						
//						realNeighbourFound = true;
//						break;
//					}
//				}
//				
//				if(realNeighbourFound)
//				{
//					neighbour.setState(CubeState.PLACEHOLDER);
//					System.out.println("neighbour is placeholder now");
//					
//					if(!this.possiblePositions.contains(neighbour))
//					{
//						System.out.println("add neighbour to placeholders");
//						this.possiblePositions.add(neighbour);
//					}
//				}
//				else
//				{
//					neighbour.setState(CubeState.LOCKED);
//					System.out.println("neighbour is locked now");
//					
//					if(this.possiblePositions.contains(neighbour))
//					{
//						System.out.println("remove neighbour from placeholders");
//						this.possiblePositions.remove(neighbour);
//					}
//				}
//			
//			}
//			else
//			{
////				System.out.println("The neighbour cube is a real cube. You can't change its state indirect!");
//			}
		}
	}
	
	private void updateCube(Cube cube)
	{
		System.out.println("Update cube " + cube);
		
//		ArrayList<Cube>neighbours = this.getNeighboursCube(pos);
//		for(Cube neighbour:neighbours)
//		{
			if(!this.realCubes.contains(cube))
			{
				ArrayList<Cube>neighbours = this.getNeighboursCube(cube.getCoordinates());
				
				boolean realNeighbourFound = false;
				
				for(Cube neighbour:neighbours)
				{
//					System.out.println("NeighbourNeighbour of cube " +neighbour + " has state " + neighbourNeighbour.getState());
					
					if(neighbour.getState().equals(CubeState.REAL))
					{
//						System.out.println("real neighbour found!");
						
						realNeighbourFound = true;
						break;
					}
				}
				
				if(realNeighbourFound)
				{
					cube.setState(CubeState.PLACEHOLDER);
					System.out.println("cube is placeholder now");
					
					if(!this.possiblePositions.contains(cube))
					{
						System.out.println("add cube to placeholders");
						this.possiblePositions.add(cube);
					}
				}
				else
				{
					cube.setState(CubeState.LOCKED);
					System.out.println("cube is locked now");
					
					if(this.possiblePositions.contains(cube))
					{
						System.out.println("remove cube from placeholders");
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
	public ArrayList<int[]> getNeighboursCoordinates(int[] posCurrentCube)
	{
		int x = posCurrentCube[0];
		int y = posCurrentCube[1];
		int z = posCurrentCube[2];
		
		ArrayList<int[]> neighbours = new ArrayList<int[]>();
		
		//LEFT
		if(y-1>=0 && y-1<=maxY)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y-1;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//RIGHT
		if(y+1>=0 && y+1<=maxY)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
		
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y+1;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//UP
		if(z+1>=0 && z+1<=maxZ)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z+1;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//DOWN
		if(z-1>=0 && z-1<=maxZ)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z-1;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//FRONT
		if(x+1>=0 && x+1<=maxX)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x+1;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//BACK
		if(x-1>=0 && x-1<=maxX)
		{
			int[] coordinateNeighbourTriple = new int[3]; 
			
			coordinateNeighbourTriple[0] = x-1;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		return neighbours;
	}
	
//TODO
//	public void removeCubeAt(int x, int y, int z)
//	{
//		if(x>0 && x<=maxX && y>0 && y<=maxY && z>0 && z<=maxZ)
//		{
//			Cube c = this.board[x][y][z];
//			
//			if(this.possiblePositions.remove(c))
//			{
//				 this.board[x][y][z] = cube;
//				 this.realCubes.add(cube);
//			}
//			else
//			{
//				//TODO throw exception
//				System.out.println("not a possible position!");
//			}
//		}
//		else
//		{
//			//TODO throw exception
//			System.out.println("out of range");
//		}
//	
//	}
	
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
}
