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
public class GraphicDriverApplet extends GraphicDriver 
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
	public GraphicDriverApplet(int windowWidth, int windowHeight,float nearPlane, float farPlane, float viewAngle, Game game)
	{
		super(windowWidth, windowHeight, nearPlane, farPlane, viewAngle, game);
//		this.initApplet();
	}

	/**
	 * @param game
	 */
	public GraphicDriverApplet(Game game) 
	{
		super(game);
//		this.initApplet();
	}
	
	public void addApplet(AppletDriver appletDriver)
	{
		System.out.println("add appletDriver in graphicDriverApplet");
		this.applet = appletDriver;
	}
	
	public void init()
	{
		System.out.println("init in applet");
		
//		this.applet = new Applet();
		this.applet.setLayout(new BorderLayout());
		try
		{
			this.displayParent = new Canvas()
			{
				public final void addNotify()
				{
					System.out.println("addNotify canvas");
					super.addNotify();
					startDriver();
				}
				
				public final void removeNotify()
				{
					System.out.println("removeNotify canvas");
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
			System.out.println("add displayParent to applet...");
			this.applet.add(this.displayParent);
			System.out.println("... added done!");
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
	
//	protected void setupDisplay()
//	{
//		System.out.println("setupDisplay in applet");
//		
////		this.applet = new Applet();
//		this.applet.setLayout(new BorderLayout());
//		try
//		{
//			this.displayParent = new Canvas()
//			{
//				public final void addNotify()
//				{
//					System.out.println("addNotify canvas");
//					super.addNotify();
//					startDriver();
//				}
//				
//				public final void removeNotify()
//				{
//					System.out.println("removeNotify canvas");
//					stopDriver();
//					super.removeNotify();
//				}
//				
//			};
//			
////			try {
////				Display.setDisplayMode(new DisplayMode(this.windowWidth, this.windowHeight));
////			} catch (LWJGLException e1) {
////				// TODO Auto-generated catch block
////				e1.printStackTrace();
////			}
//			
//			this.displayParent.setSize(this.windowWidth, this.windowHeight);
//			this.applet.setSize(this.windowWidth, this.windowHeight);
//			System.out.println("add displayParent to applet...");
//			this.applet.add(this.displayParent);
//			System.out.println("... added done!");
//			this.displayParent.setFocusable(true);
//			this.displayParent.requestFocus();
//			this.displayParent.setIgnoreRepaint(true);
//			this.applet.setVisible(true);
//			
//		} 
//		catch (Exception e) 
//		{
//			System.err.println(e);
//			throw new RuntimeException("Unable to create display");
//		}
//		
//		try 
//		{
//			Display.setParent(this.displayParent);
//			System.out.println("is displayable? " + Display.getParent().isDisplayable());
////			Display.create();
//		} 
//		catch (LWJGLException e) 
//		{
//			System.out.println("ERROR IN APPLET!");
//			e.printStackTrace();
//		}
//		
//		System.out.println("SETUP DISPLAY DONE IN APPLET!");
//	}
	
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
		System.out.println("start graphicDriverApplet");
//		Display.setParent(this.displayParent);
////		Display.create();
//		
//		this.setupGL();
//		
//		this.getDelta();
//		this.lastFPS = this.getTime();
//		
//		this.runGameLoop();
		
//		if(gameThread==null)
//		{
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
					
//					runDriver();
					
					setupGL();
					
					getDelta();
					lastFPS = getTime();
				}
			};
			System.out.println("start game thread!");
			gameThread.start();
		}
		
//		}
	
	
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
