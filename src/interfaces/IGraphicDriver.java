package interfaces;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public interface IGraphicDriver 
{
	public  void start();
	public  void startDriver();
	public void stopDriver();
	public void destroy();
	
}
