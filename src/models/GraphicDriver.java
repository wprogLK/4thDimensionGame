package models;


import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import interfaces.IGraphicDriver;

public class GraphicDriver implements IGraphicDriver
{
	protected Game game;
	
	protected long lastFrame; //Time at the last frame
	
	protected int fps; //frames per second
	protected long lastFPS; //last fps time
	
	protected int windowWidth;
	protected int windowHeight;
	
	private int windowDefaultWidth = 800;
	private int windowDefaultHeight = 600;
	
	private float nearPlane;
	private float farPlane;
	private float viewAngle;
	
	private float nearPlaneDefault = 0.1f;
	private float farPlaneDefault = 1000.0f;
	private float viewAngleDefault =45.0f;//45.0f;
	
	public GraphicDriver(int windowWidth, int windowHeight, float nearPlane, float farPlane, float viewAngle, Game game)
	{
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
		this.viewAngle = viewAngle;
		
		this.game = game;
	}
	
	public GraphicDriver(Game game)
	{
		this.windowWidth = windowDefaultWidth;
		this.windowHeight = windowDefaultHeight;
		
		this.nearPlane = this.nearPlaneDefault;
		this.farPlane = this.farPlaneDefault;
		
		this.viewAngle = this.viewAngleDefault;
		
		this.game = game;
	}
	
	public void start()
	{
		System.out.println("start graphicDriver");
		runDriver();
	}
	
	public void runDriver()
	{
		System.out.println("runGraphic driver");
		this.setupGL();
		
		this.getDelta();
		this.lastFPS = this.getTime();
		
		this.runGameLoop();
	}
	
	public final void startDriver()
	{
		System.out.println("StartDriver basic");
		this.setupDisplay();
		
//		try 
//		{
//			Display.create();
//		} 
//		catch (LWJGLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		this.start();
	}
	
	public void stopDriver()
	{
		//TODO
		System.out.println("Stop driver");
	}
	
	protected void setupDisplay()
	{
		System.out.println("setupDisplay in GraphicDriver");
		
		try 
		{
			Display.setDisplayMode(new DisplayMode(this.windowWidth, this.windowHeight));
			Display.create();
			System.out.println("Display succesfully created!");
		} 
		catch (LWJGLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/*need to render the scene twice because of the transparency objects!
	 * render 1) draw the entire scene with depth testing on for all the opaque fragments
	 * render 2) draw the entire scene again with the depth buffer read only.
	 *(Source: http://relativity.net.au/gaming/java/Transparency.html)
	 */
	
	protected void runGameLoop()
	{
		while(!this.game.isGameOver() && !Display.isCloseRequested())
		{
			int delta = this.getDelta(); //for interpolation ?
			
			this.game.checkInput();
			
			this.game.update(delta);
			this.updateFPS();
			
			GL11.glDepthMask(true);
			this.prepareForRendering();
			
			//first render
			GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_EQUAL, 1.0f);
            
			this.game.render();
			
			//second render
			GL11.glEnable(GL11.GL_BLEND);
            GL11.glDepthMask(false);
            GL11.glAlphaFunc(GL11.GL_LESS, 1.0f);

        	this.game.render();
            
			Display.update();
			Display.sync(60);
		}
		
		this.destroy();
	}
	
	public void destroy()
	{
		Display.destroy();
	}

	protected int getDelta()
	{
		long time = getTime();
		int delta = (int) (time - this.lastFrame);
		this.lastFrame = time;
		
		return delta;
	}
	
	protected long getTime()
	{
		return (Sys.getTime()*1000)/Sys.getTimerResolution();
	}
	
	protected void updateFPS()
	{
		if(this.getTime() - lastFPS >1000)
		{
			Display.setTitle("FPS: " + fps);
			fps = 0;
			
			this.lastFPS +=1000;
		}
		
		this.fps++;
	}
	
	public int getWindowWidth()
	{
		return this.windowWidth;
	}
	
	public int getWindowHeight()
	{
		return this.windowHeight;
	}
	
	protected void setupGL()
	{
		System.out.println("setupGL...");
		
		GL11.glEnable(GL11.GL_DEPTH_TEST); //GL forbid to "overpaint" given pixels.
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glViewport(0,0,this.windowWidth,this.windowHeight); //origin coordinates at (0|0)

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity(); //Load the standard matrix
		
		GLU.gluPerspective(this.viewAngle, this.windowWidth/this.windowHeight, this.nearPlane, this.farPlane);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	private void prepareForRendering()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glColor3f(0.5f, 0.5f, 1.0f);
		GL11.glLoadIdentity(); //Load the standard matrix
//		GL11.glTranslated(0.0f,-10.0f,-60.0f); //TODO try other values
//		GL11.glTranslated(+this.windowWidth/2,-this.windowHeight/2,-160.0f); //TODO try other values //x=-15.0f
		GL11.glTranslated(0,-10,-160.0f); //TODO try other values //x=-15.0f
	}
}
