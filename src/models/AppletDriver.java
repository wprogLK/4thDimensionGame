package models;

import java.applet.Applet;

public class AppletDriver extends Applet
{
	private Game game;
	private GraphicDriverApplet graphicDriver;
	
	public void init()
	{
		System.out.println("init applet");
		
		this.game = new Game();
		this.graphicDriver = this.game.getGraphicDriverApplet();
		
		this.graphicDriver.addApplet(this);
		this.graphicDriver.init();
		
		this.game.start();
//		this.graphicDriver.startDriver();
	}
}
