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
	
	@Test
	public Board SimpleBoardTest() 
	{
		ArrayList<Cube> basicCubes = new ArrayList<Cube>();
		
		basicCubes.add(new Cube(2,2,2));
		
		Board board = new Board(basicCubes);
		
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
	
	@Given("boardShouldHaveOneRealCubeAt2_2_2")
	public Board addCubeAt1_2_2(Board board)
	{
		Cube c = new Cube(1,2,2);
	
		board.addCubeAt(c, 1, 2, 2);
		
		return board;
	}
	
	@Given("addCubeAt1_2_2")
	public Board boardShouldHave10Placeholders(Board board)
	{
		assert(board.getPlaceholderCubes().size()==10);
		
		return board;
	}
	
	@Given("addCubeAt1_2_2")
	public Board boardShouldHave2RealCubes(Board board)
	{
		assert(board.getRealCubes().size()==2);
		
		return board;
	}
	
	@Given("addCubeAt1_2_2")
	public Board checkCubesAfterAddCubeAt1_2_2(Board board)
	{
		assert(board.getCubeAt(1, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(3, 2, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		assert(board.getCubeAt(0, 2, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(1, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(1, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(1, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(1, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	@Given("checkCubesAfterAddCubeAt1_2_2")
	public Board addCubeAdd0_2_2(Board board)
	{
		Cube c = new Cube(0,2,2);
		board.addCubeAt(c, 0, 2, 2); 
		
		return board;
	}
	
	@Given("addCubeAdd0_2_2")
	public Board boardShouldHave13Placeholders(Board board)
	{
		assertThat(board.getPlaceholderCubes().size(),equalTo(13));

		return board;
	}
	
	@Given("addCubeAdd0_2_2")
	public Board boardShouldHave3RealCubes(Board board)
	{
		assert(board.getRealCubes().size()==3);
		
		return board;
	}
	
	@Given("addCubeAdd0_2_2")
	public Board checkCubesAfterAddCubeAt0_2_2(Board board)
	{
		assert(board.getCubeAt(1, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(3, 2, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		assert(board.getCubeAt(0, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(2, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(1, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(1, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(1, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(1, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		assert(board.getCubeAt(0, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	////////////////////
	//REMOVE CUBE TEST//
	////////////////////
	
	@Test
	public Board removeCubeTest()
	{
		ArrayList<Cube> basicCubes = new ArrayList<Cube>();
		
		basicCubes.add(new Cube(2,2,2));
		basicCubes.add(new Cube(1,2,2));
		basicCubes.add(new Cube(0,2,2));
		
		Board board = new Board(basicCubes);
		
		return board;
	}
	
	@Given("removeCubeTest")
	public Board checkBoard(Board board)
	{
		assertThat(board.getRealCubes().size(),equalTo(3));
		assertThat(board.getPlaceholderCubes().size(),equalTo(13));
		
		assert(board.getCubeAt(1, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(3, 2, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		assert(board.getCubeAt(0, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(2, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(1, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(1, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(1, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(1, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		assert(board.getCubeAt(0, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	@Given("checkBoard")
	public Board removeCubeAt1_2_2(Board board)
	{
		board.removeCubeAt(1,2,2);
		
		return board;
	}
	
	@Given("removeCubeAt1_2_2")
	public Board checkBoardAfterRemoveCubeAt1_2_2(Board board)
	{
		assertThat(board.getRealCubes().size(),equalTo(2));
		assertThat(board.getPlaceholderCubes().size(),equalTo(10));
		
		assert(board.getCubeAt(1, 2, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(3, 2, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(2, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		assert(board.getCubeAt(0, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(2, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(1, 1, 2).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(1, 3, 2).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(1, 2, 1).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(1, 2, 3).getState().equals(CubeState.LOCKED));
		
		assert(board.getCubeAt(0, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	@Given("checkBoardAfterRemoveCubeAt1_2_2")
	public Board removeCubeAt2_2_2(Board board)
	{
		board.removeCubeAt(2,2,2);
		
		return board;
	}
	
	@Given("removeCubeAt2_2_2")
	public Board checkBoardAfterRemoveCubeAt2_2_2(Board board)
	{
		assertThat(board.getRealCubes().size(),equalTo(1));
		assertThat(board.getPlaceholderCubes().size(),equalTo(5));
		
		assert(board.getCubeAt(1, 2, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(3, 2, 2).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(2, 1, 2).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(2, 3, 2).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(2, 2, 1).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(2, 2, 3).getState().equals(CubeState.LOCKED));
		
		assert(board.getCubeAt(0, 2, 2).getState().equals(CubeState.REAL));
		assert(board.getCubeAt(2, 2, 2).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(1, 1, 2).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(1, 3, 2).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(1, 2, 1).getState().equals(CubeState.LOCKED));
		assert(board.getCubeAt(1, 2, 3).getState().equals(CubeState.LOCKED));
		
		assert(board.getCubeAt(0, 1, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 3, 2).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 2, 1).getState().equals(CubeState.PLACEHOLDER));
		assert(board.getCubeAt(0, 2, 3).getState().equals(CubeState.PLACEHOLDER));
		
		return board;
	}
	
	
	
	////////////////////
	//BASIC BOARD TEST//
	////////////////////
	
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
