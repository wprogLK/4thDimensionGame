package models;

import java.applet.Applet;

public class AppletDriver extends Applet
{
	private Game game;
	private GraphicDriverApplet graphicDriver;
	
	public void init()
	{
		this.game = new Game();
		this.graphicDriver = this.game.getGraphicDriverApplet();
		
		this.graphicDriver.addApplet(this);
		
		this.graphicDriver.startDriver();
	}
}
