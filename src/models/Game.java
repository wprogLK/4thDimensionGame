/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import models.Board.SelectionMode;
import models.Cube.CubeState;
import models.MouseState.MouseButtonState;
import models.Player.Colour;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import annotations.OnlyForTesting;

import exceptions.BoardException;
import exceptions.GameException;
import exceptions.InvalidColourException;

/**
 * @author Lukas
 *
 */
public class Game 
{ 
	private boolean gameOver;
	
	private GraphicDriver driver;
	
	private MouseHandler mouseHandler;
	private KeyboardHandler keyboardHandler;
	
	private Queue<Player> players;
	@OnlyForTesting
	private Player currentPlayer;

	private Board board;
	
	public static void main(String args[])
	{
		new Game();
	}
	
	/**
	 * 
	 */
	public Game() 
	{	
		this.players = new LinkedList<Player>();
		this.currentPlayer = null;
		this.gameOver = false;
		
		this.driver = new GraphicDriver(this);
		
		this.mouseHandler = new MouseHandler();
		this.keyboardHandler = new KeyboardHandler();
		
		initTestBoard();
		
		this.start();
	}
	
	@OnlyForTesting
	private void initTestBoard()
	{
		ArrayList<Cube> basicCubes = new ArrayList<Cube>();
		basicCubes.add(new Cube(2,2,2));
		basicCubes.add(new Cube(1,1,1));
		basicCubes.add(new Cube(2,1,1));
		basicCubes.add(new Cube(3,1,1));
		basicCubes.add(new Cube(4,1,1));
		
		//Add testplayer
//		Player player = new Player("TestPlayer", Colour.BLUE);
//		this.players.add(player);
//		
//		basicCubes.get(3).addPlayer(player, FaceDirection.DOWN); //DEBUG!
		
		this.board = new Board(basicCubes);
	}

	public void play() throws GameException
	{
		if(this.players.size()<2)
		{
			throw new GameException("You'r forever alone? You can't play this game with only yourself!");
		}
		else
		{
			this.start();
		}
	}

	@OnlyForTesting
	public Player getCurrentPlayer()
	{
		if(this.currentPlayer==null)
		{
			this.currentPlayer = this.players.peek();
		}
		
		return this.currentPlayer;
	}
	
	public Player nextPlayer()
	{
		Player currentPlayer = this.players.poll();
		this.players.add(currentPlayer);
		this.currentPlayer = currentPlayer;
		
		return currentPlayer;
	}
	
	public void addPlayers(ArrayList<Player> players) 
	{
		this.players.addAll(players);
		
	}
	
	public boolean isGameOver()
	{
		return this.gameOver;
	}

	public void checkInput() 
	{
		this.mouseHandler.check();
		this.keyboardHandler.check();
	}
	
	///////////////////
	//PRIVATE METHODS//
	///////////////////
	
	private void start()
	{
		this.driver.start();
	}
	
	public void update(int delta)
	{
		//TODO
//		System.out.println("Update the game here...");
		this.board.update(delta);
	}

	public void render()
	{
//		System.out.println("Render the game here...");
		this.board.render();
	}
	///////////////
	//INNER CLASS//
	///////////////
	
	private class MouseHandler
	{
		private int leftButton = 0;
		private int rightButton = 1;
		private int middleButton = 3;
		
		private int mouseButtonForRotation = leftButton;
		
	
		private float angleX = 0.0f;
		private float angleY = 0.0f;
		
		public void check()
		{
			while(Mouse.next())
			{
				this.handleLeftButton();
				this.handleRightButton();
				this.handleMiddleButton();
				
				int button = Mouse.getEventButton();
				
				if(button == -1)
				{
					this.handleMovement();
				}
			}
		}
		
		private void handleLeftButton()
		{
//			if()
		}
		
		private void handleRightButton()
		{
			
		}
		
		private void handleMiddleButton()
		{
			
		}
		
		private void handleMovement()
		{
			int angleXSign = 0;
			int angleYSign = 0;
			
			if(Mouse.getEventDX()<0)
			{
				angleXSign = -1;
			}
			if(Mouse.getEventDX()>0)
			{
				angleXSign = +1;
			}
			
			if(Mouse.getEventDY()<0)
			{
				angleYSign = +1;
			}
			if(Mouse.getEventDY()>0)
			{
				angleYSign = -1;
			}
			
			if(Mouse.isButtonDown(mouseButtonForRotation))
			{
				board.addAngle(angleYSign,angleXSign);
			}
		}
		
	}
	
	private class KeyboardHandler
	{
		public void check()
		{
			while (Keyboard.next()) 
			{
                int key = Keyboard.getEventKey();
                
                if (Keyboard.getEventKeyState()) 
                {
                	 switch(key) 
                     {
                     		//ROTATE BOARD:
                             case Keyboard.KEY_UP:
                             {
                             	board.addAngle(-1,0);
                             	break;
                             }
                             case Keyboard.KEY_DOWN:
                             {
                             	board.addAngle(+1,0);
                             	break;
                             } 
                             case Keyboard.KEY_LEFT:
                             {
                             	board.addAngle(0,-1);
                             	break;
                             }
                             case Keyboard.KEY_RIGHT:
                             {
                             	board.addAngle(0,+1);
                             	break;
                             }
                             //CHANGE SELECTED CUBE: (at the moment only placeholder cubes!)
                             case Keyboard.KEY_A:
                             {
                             	board.changeSelection(-1, 0, 0);//, CubeState.PLACEHOLDER);
                             	break;
                             }
                             case Keyboard.KEY_D:
                             {
                             	board.changeSelection(1, 0, 0);//, CubeState.PLACEHOLDER);
                             	break;
                             }
                             case Keyboard.KEY_W:
                             {
                             	board.changeSelection(0, 1, 0);//, CubeState.PLACEHOLDER);
                             	break;
                             }
                             case Keyboard.KEY_S:
                             {
                             	board.changeSelection(0, -1, 0);//, CubeState.PLACEHOLDER);
                             	break;
                             }
                             case Keyboard.KEY_Q:
                             {
                             	board.changeSelection(0, 0, 1);//, CubeState.PLACEHOLDER);
                             	break;
                             }
                             case Keyboard.KEY_Y:
                             {
                             	board.changeSelection(0, 0, -1);//, CubeState.PLACEHOLDER);
                             	break;
                             }
                             
                             //ADD CUBE
                             case Keyboard.KEY_SPACE:
                             {
                            	 board.addSelectedCube();
                            	 break;
                             }
                             
                             //CHANGE SELECTION MODE ON BOARD:
                             case Keyboard.KEY_1:
                             {
                            	 board.changeSelectionMode(SelectionMode.CUBEMode);
                            	 break;
                             }
                             case Keyboard.KEY_2:
                             {
                            	 board.changeSelectionMode(SelectionMode.FACEMode);
                            	 break;
                             }
                            	 
                     }   
                }
               
			}
		}
	}
}
