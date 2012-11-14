package tests;

import java.util.ArrayList;

import models.Board;
import models.Cube;
import models.Game;
import models.Player;
import models.Cube.CubeState;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class BoardTest 
{
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
	
		System.out.println("size = " + placeholders.size());
		System.out.println("size real= " + board.getRealCubes().size());
		assert(placeholders.size()==6);
		
		return board;
	}

}
