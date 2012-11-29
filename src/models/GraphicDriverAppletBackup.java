/**
 * 
 */
package models;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * @author Lukas
 *
 */
public class GraphicDriverAppletBackup extends GraphicDriver 
{
	private Canvas displayParent;
	private Applet applet;
	private Thread gameThread;
	
	/**
	 * @param windowWidth
	 * @param windowHeight
	 * @param nearPlane
	 * @param farPlane
	 * @param viewAngle
	 * @param game
	 */
	public GraphicDriverAppletBackup(int windowWidth, int windowHeight,float nearPlane, float farPlane, float viewAngle, Game game)
	{
		super(windowWidth, windowHeight, nearPlane, farPlane, viewAngle, game);
//		this.initApplet();
	}

	/**
	 * @param game
	 */
	public GraphicDriverAppletBackup(Game game) 
	{
		super(game);
//		this.initApplet();
	}
	
	
	protected void setupDisplay()
	{
		System.out.println("setupDisplay in applet");
		
		this.applet = new Applet();
		this.applet.setLayout(new BorderLayout());
		try
		{
			this.displayParent = new Canvas()
			{
				public final void addNotify()
				{
					super.addNotify();
					startDriver();
				}
				
				public final void removeNotify()
				{
					stopDriver();
					super.removeNotify();
				}
				
			};
			
//			try {
//				Display.setDisplayMode(new DisplayMode(this.windowWidth, this.windowHeight));
//			} catch (LWJGLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			
			this.displayParent.setSize(this.windowWidth, this.windowHeight);
			this.applet.setSize(this.windowWidth, this.windowHeight);
			this.applet.add(this.displayParent);
			this.displayParent.setFocusable(true);
			this.displayParent.requestFocus();
			this.displayParent.setIgnoreRepaint(true);
			this.applet.setVisible(true);
			
		} 
		catch (Exception e) 
		{
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
		
		try 
		{
			Display.setParent(this.displayParent);
			System.out.println("is displayable? " + Display.getParent().isDisplayable());
//			Display.create();
		} 
		catch (LWJGLException e) 
		{
			System.out.println("ERROR IN APPLET!");
			e.printStackTrace();
		}
		
		System.out.println("SETUP DISPLAY DONE IN APPLET!");
	}
	
//TODO	
//	@Override
//	public void startDriver()
//	{
//		System.out.println("Start driver");
//		
//		Display.setParent(this.displayParent);
//		Display.create();
//	}
	
	public void start()
	{
//		Display.setParent(this.displayParent);
////		Display.create();
//		
//		this.setupGL();
//		
//		this.getDelta();
//		this.lastFPS = this.getTime();
//		
//		this.runGameLoop();
		
		this.gameThread = new Thread()
		{
			public void run()
			{
				game.setGameOver(false);
				
				try
				{
					Display.setParent(displayParent);
					Display.create();
					setupGL();
				}
				catch(LWJGLException e)
				{
					e.printStackTrace();
					return;
				}
				
				runGameLoop();
			}
		};
		gameThread.start();
		}
	
	
	@Override
	public void stopDriver()
	{
		System.out.println("Stop driver");
		game.setGameOver(true);
		
		try
		{
			this.gameThread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			
		}
		Display.destroy();
	}
	
	@Override
	public void destroy()
	{
		this.applet.remove(this.displayParent);
		super.destroy(); //TODO necessary?
	}

}
