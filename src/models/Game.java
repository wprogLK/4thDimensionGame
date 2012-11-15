/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
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
		
		this.driver = new GraphicDriver();
		
		initTestBoard();
		
		this.start();
	}
	
	@OnlyForTesting
	private void initTestBoard()
	{
		ArrayList<Cube> basicCubes = new ArrayList<Cube>();
		basicCubes.add(new Cube(2,2,2));
		
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

	///////////////////
	//PRIVATE METHODS//
	///////////////////
	
	private void start()
	{
		this.driver.start();
	}
	
	private void updateGame(int delta)
	{
		//TODO
		System.out.println("Update the game here...");
		this.board.update(delta);
	}

	private void renderGame()
	{
		System.out.println("Render the game here...");
		this.board.render();
	}
	///////////////
	//INNER CLASS//
	///////////////
	
	private class GraphicDriver
	{
		private long lastFrame; //Time at the last frame
		
		private int fps; //frames per second
		private long lastFPS; //last fps time
		
		private int windowWidth;
		private int windowHeight;
		
		private int windowDefaultWidth = 800;
		private int windowDefaultHeight = 600;
		
		private float nearPlane;
		private float farPlane;
		private float viewAngle;
		
		private float nearPlaneDefault = 0.1f;
		private float farPlaneDefault = 1000.0f;
		private float viewAngleDefault =45.0f;//45.0f;
		
		public GraphicDriver(int windowWidth, int windowHeight, float nearPlane, float farPlane, float viewAngle)
		{
			this.windowWidth = windowWidth;
			this.windowHeight = windowHeight;
			
			this.nearPlane = nearPlane;
			this.farPlane = farPlane;
			this.viewAngle = viewAngle;
		}
		
		public GraphicDriver()
		{
			this.windowWidth = windowDefaultWidth;
			this.windowHeight = windowDefaultHeight;
			
			this.nearPlane = this.nearPlaneDefault;
			this.farPlane = this.farPlaneDefault;
			
			this.viewAngle = this.viewAngleDefault;
		}
		
		public void start()
		{
			this.setupDisplay();
			this.setupGL();
			
			this.getDelta();
			this.lastFPS = this.getTime();
			
			this.runGameLoop();
		}
		
		private void setupDisplay()
		{
			try
			{
				Display.setDisplayMode(new DisplayMode(this.windowWidth, this.windowHeight));
				Display.create();
			}
			catch(LWJGLException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		private void runGameLoop()
		{
			while(!gameOver && !Display.isCloseRequested())
			{
				int delta = this.getDelta(); //for interpolation ?
				
				updateGame(delta);
				this.updateFPS();
				
				this.prepareForRendering();
				renderGame();
				
				Display.update();
				Display.sync(60);
			}
			
			Display.destroy();
		}
		
		private int getDelta()
		{
			long time = getTime();
			int delta = (int) (time - this.lastFrame);
			this.lastFrame = time;
			
			return delta;
		}
		
		private long getTime()
		{
			return (Sys.getTime()*1000)/Sys.getTimerResolution();
		}
		
		private void updateFPS()
		{
			if(this.getTime() - lastFPS >1000)
			{
				Display.setTitle("FPS: " + fps);
				fps = 0;
				
				this.lastFPS +=1000;
			}
			
			this.fps++;
		}
		
		private void setupGL()
		{
			GL11.glEnable(GL11.GL_DEPTH_TEST); //GL forbid to "overpaint" given pixels.
			GL11.glViewport(0,0,this.windowWidth,this.windowHeight); //origin coordinates at (0|0)
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity(); //Load the standard matrix
			
			GLU.gluPerspective(this.viewAngle, this.windowWidth/this.windowHeight, this.nearPlane, this.farPlane);
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			
//			GL11.glMatrixMode(GL11.GL_PROJECTION);
//			GL11.glLoadIdentity();
//			GL11.glOrtho(0, this.windowWidth, 0, this.windowHeight, 1, -1); 
//			GL11.glMatrixMode(GL11.GL_MODELVIEW);
		}
		
		private void prepareForRendering()
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glColor3f(0.5f, 0.5f, 1.0f);
			GL11.glLoadIdentity(); //Load the standard matrix
			GL11.glTranslated(0.0f,-10.0f,-60.0f); //TODO try other values
		}
		
	}
	
}
