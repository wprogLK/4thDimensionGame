package tests;

import java.util.ArrayList;
import java.util.Arrays;

import models.Board;
import models.Cube;
import models.Game;
import models.Player;
import models.Cube.CubeState;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import static org.junit.Assert.*;
//
//import static org.hamcrest.collection.IsArrayContaining.*;
//import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(JExample.class)
public class BoardTest 
{
	private final int maxX = 10;
	private final int maxY = 10;
	private final int maxZ = 10;
	
	/////////////////////
	//SIMPLE BOARD TEST//
	/////////////////////
	
//	@Ignore
	@Test
	public Board SimpleBoardTest() 
	{
		ArrayList<Cube> basicCubes = new ArrayList<Cube>();
		
		basicCubes.add(new Cube(2,2,2));
		
		Board board = new Board(basicCubes);
		
		board.showLayer(1);
		return board;
	}
	
	@Given("SimpleBoardTest")
	public Board boardShouldHaveOneRealCubeAt2_2_2(Board board)
	{
		Cube cube = board.getCubeAt(2, 2, 2);
		assert(cube.getState().equals(CubeState.REAL));
		
		return board;
	}
	
	@Given("boardShouldHaveOneRealCubeAt2_2_2")
	public Board boardShouldHaveSixPlaceholders(Board board)
	{
		ArrayList<Cube> placeholders = board.getPlaceholderCubes();
	
		assert(placeholders.size()==6);
		
		return board;
	}
	
	@Given("boardShouldHaveOneRealCubeAt2_2_2")
	public Board boardShouldHaveAPlaceholderAt1_2_2(Board board)
	{
		Cube cube = board.getCubeAt(1, 2, 2);
		
		assert(cube.getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	@Given("boardShouldHaveOneRealCubeAt2_2_2")
	public Board boardShouldHaveAPlaceholderAt3_2_2(Board board)
	{
		Cube cube = board.getCubeAt(3, 2, 2);
		
		assert(cube.getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	@Given("boardShouldHaveOneRealCubeAt2_2_2")
	public Board boardShouldHaveAPlaceholderAt2_1_2(Board board)
	{
		Cube cube = board.getCubeAt(2, 1, 2);
		
		assert(cube.getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	@Given("boardShouldHaveOneRealCubeAt2_2_2")
	public Board boardShouldHaveAPlaceholderAt2_3_2(Board board)
	{
		Cube cube = board.getCubeAt(2, 3, 2);
		
		assert(cube.getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	@Given("boardShouldHaveOneRealCubeAt2_2_2")
	public Board boardShouldHaveAPlaceholderAt2_2_1(Board board)
	{
		Cube cube = board.getCubeAt(2, 2, 1);
		
		assert(cube.getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	@Given("boardShouldHaveOneRealCubeAt2_2_2")
	public Board boardShouldHaveAPlaceholderAt2_2_3(Board board)
	{
		Cube cube = board.getCubeAt(2, 2, 3);
		
		assert(cube.getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	////////////////////
	//BASIC BOARD TEST//
	////////////////////
	@Ignore
	@Test
	public Board basicBoardTest()
	{
		Board board = new Board(maxX,maxY,maxZ);
		
		return board;
	}
	
	@Given("basicBoardTest")
	public Board allCubesShouldBeLocked(Board board)
	{
		for(int x = 0; x<board.getMaxX();x++)
		{
			for(int y = 0; y<board.getMaxY();y++)
			{
				for(int z = 0; z<board.getMaxZ();z++)
				{
					Cube c = board.getCubeAt(x, y, z);
					assert(c.getState().equals(CubeState.LOCKED));
				}
			}
		}
		
		return board;
	}
	
	@Given("basicBoardTest")
	public Board noRealCubesShouldExist(Board board)
	{
		assert(board.getRealCubes().isEmpty());
		return board;
	}
	
	@Given("basicBoardTest")
	public Board noPlaceholderCubeShouldExist(Board board)
	{
		assert(board.getPlaceholderCubes().isEmpty());
		return board;
	}
	
	@Given("basicBoardTest")
	public ArrayList<int[]> neighbourCoordinatesOfCubeAt2_2_2ShouldHaveSize6(Board board)
	{
		int[] cubeCoordiates = {2,2,2};
		
		ArrayList<int[]> neighboursCoordinates = board.getNeighboursCoordinates(cubeCoordiates);
		
		assert(neighboursCoordinates.size()==6);
		
		return neighboursCoordinates;
	}
	
	@Given("neighbourCoordinatesOfCubeAt2_2_2ShouldHaveSize6")
	public ArrayList<int[]> neighbourCoordinatesOfCubeAt2_2_2ShouldContainAllNeighbours(ArrayList<int[]> neighboursCoordinates)
	{
		int[] cubeUpCoordinates = {2,2,3};
		assertThat(neighboursCoordinates,hasItem(cubeUpCoordinates));
		
		int[] cubeDownCoordinates = {2,2,1};
		assertThat(neighboursCoordinates,hasItem(cubeDownCoordinates));
		
		
		int[] cubeLeftCoordinates = {2,1,2};
		assertThat(neighboursCoordinates,hasItem(cubeLeftCoordinates));
		
		int[] cubeRightCoordinates = {2,3,2};
		assertThat(neighboursCoordinates,hasItem(cubeRightCoordinates));

		
		int[] cubeFrontCoordinates = {3,2,2};
		assertThat(neighboursCoordinates,hasItem(cubeFrontCoordinates));

		int[] cubeBackCoordinates = {1,2,2};
		assertThat(neighboursCoordinates,hasItem(cubeBackCoordinates));
		
		return neighboursCoordinates;
	}
	
	@Given("allCubesShouldBeLocked")
	public Board cubeShouldAddedToTheBoardByPrivateMethod(Board board)
	{
		Cube cubeToAdd = new Cube(2,2,2);
		
		board.addCube(cubeToAdd, 2, 2, 2);
		return board;
	}

}
