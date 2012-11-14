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
	private final int maxZ = 10;
	private final int maxX= 10;
	private final int maxY = 10;
	
	private ArrayList<Cube> possiblePositions;
	private ArrayList<Cube> realCubes;
	/*
	 * This constructor shouldn't used in the final game!
	 */
	public Board() 
	{
		this.basicInit();
	
		for(int x = 0; x<maxX;x++)
		{
			for(int y = 0; y<maxY; y++)
			{
				for(int z = 0; z<maxZ; z++)
				{
					Cube cube = new Cube(x,y,z,Cube.CubeState.LOCKED);
					
					this.board[x][y][z] = cube;
//					this.possiblePositions.add(cube);
				}
			}
		}
	}
	
	private void basicInit()
	{
		this.possiblePositions = new ArrayList<Cube>();
		this.realCubes = new ArrayList<Cube>();
		
		this.board = new Cube[maxX][maxY][maxZ];
	}
	
	public Board(ArrayList<Cube> startCubes)
	{
		this.basicInit();
		for(int x = 0; x<maxX;x++)
		{
			for(int y = 0; y<maxY; y++)
			{
				for(int z = 0; z<maxZ; z++)
				{
					Cube cube = new Cube(x,y,z,Cube.CubeState.LOCKED);
					
					this.board[x][y][z] = cube;
				}
			}
		}
		this.addBasicCubes(startCubes);
	}
	
	private void addBasicCubes(ArrayList<Cube> startCubes)
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
				System.out.println("cube " + cube +" added to the board!");
			}
			else
			{
				System.out.println("cube " + cube +" couldn't add to the board!");
			}
				
		}
	}
	
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
	
	private void addCube(Cube cube, int x, int y, int z)
	{
		if(x>0 && x<=maxX && y>0 && y<=maxY && z>0 && z<=maxZ)
		{
			System.out.println("ADD CUBE " + cube);
			
			this.board[x][y][z] = cube;
			 
			cube.setState(CubeState.REAL);
			this.realCubes.add(cube);
			 
			this.updateNeighbours(cube);
		}
		else
		{
			System.out.println("NOT POSSIBLE TO ADD CUBE " + cube);
		}
		
	}
	
	private void updateNeighbours(Cube cube)
	{
		System.out.println("Update neighbours of cube " + cube);
		int[] pos = cube.getCoordinates();
		
		ArrayList<Cube>neighbours = this.getNeighboursCube(pos);
		for(Cube neighbour:neighbours)
		{
			if(!this.realCubes.contains(neighbour))
			{
				ArrayList<Cube>neighboursOfNeighbour = this.getNeighboursCube(neighbour.getCoordinates());
				
				boolean realNeighbourFound = false;
				
				
				for(Cube neighbourNeighbour:neighboursOfNeighbour)
				{
					System.out.println("NeighbourNeighbour of cube " +neighbour + " has state " + neighbourNeighbour.getState());
					
					if(neighbourNeighbour.getState().equals(CubeState.REAL))
					{
						System.out.println("real neighbour found!");
						
						realNeighbourFound = true;
						break;
					}
				}
				
				if(realNeighbourFound)
				{
					neighbour.setState(CubeState.PLACEHOLDER);
					System.out.println("neighbour is placeholder now");
					
					if(!this.possiblePositions.contains(neighbour))
					{
						System.out.println("add neighbour to placeholders");
						this.possiblePositions.add(neighbour);
					}
				}
				else
				{
					neighbour.setState(CubeState.LOCKED);
					System.out.println("neighbour is locked now");
					
					if(this.possiblePositions.contains(neighbour))
					{
						System.out.println("remove neighbour from placeholders");
						this.possiblePositions.remove(neighbour);
					}
				}
			
			}
			else
			{
				System.out.println("The neighbour cube is a real cube. You can't change its state indirect!");
			}
		}
	}
	
//	//In case the cube (not the neigbourCube is removed), remove it first!
//	private boolean checkLockState(Cube neighbourCube) 
//	{
//		if()
//	}

	private ArrayList<Cube> getNeighboursCube(int[] posCurrentCube)
	{
		ArrayList<int[]> neighboursCoordinates = this.getNeighboursCoordinates(posCurrentCube);
		
		ArrayList<Cube> neighbours = new ArrayList<Cube>();
		
		for(int[] coordinates:neighboursCoordinates)
		{
			//TODO DEBUG THIS! MAYBE getNeighboursCoordinates() TOO!!
			FIX
		
			int xNeighbour = coordinates[0];
			int yNeighbour = coordinates[1];
			int zNeighbour = coordinates[2];
			neighbours.add(this.board[xNeighbour][yNeighbour][zNeighbour]);
			System.out.println("ADD NEIGHBOUR CUBE " + this.board[xNeighbour][yNeighbour][zNeighbour]);
		}
		
		return neighbours;
	}
	
	private ArrayList<int[]> getNeighboursCoordinates(int[] posCurrentCube)
	{
		int x = posCurrentCube[0];
		int y = posCurrentCube[1];
		int z = posCurrentCube[2];
		
		ArrayList<int[]> neighbours = new ArrayList<int[]>();
		
		int[] coordinateNeighbourTriple = new int[3]; 
		
		//LEFT
		if(y-1>=0 && y-1<=maxY)
		{
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y-1;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//RIGHT
		if(y+1>=0 && y+1<=maxY)
		{
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y+1;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//UP
		if(z+1>=0 && z+1<=maxZ)
		{
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z+1;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//DOWN
		if(z-1>=0 && z-1<=maxZ)
		{
			coordinateNeighbourTriple[0] = x;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z-1;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//FRONT
		if(x+1>=0 && x+1<=maxX)
		{
			coordinateNeighbourTriple[0] = x+1;
			coordinateNeighbourTriple[1] = y;
			coordinateNeighbourTriple[2] = z;
			
			neighbours.add(coordinateNeighbourTriple);
		}
		
		//BACK
		if(x-1>=0 && x-1<=maxX)
		{
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
}
