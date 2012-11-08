/**
 * 
 */
package models;

/**
 * @author Lukas
 *
 */
public class Cube
{
	private int xCoordiante;
	private int yCoordinate;
	private int zCoordinate;
	
	/**
	 * 
	 */
	public Cube(int xCoordiante, int yCoordinate, int zCoordinate)
	{
		this.xCoordiante = xCoordiante;
		this.yCoordinate = yCoordinate;
		this.zCoordinate = zCoordinate;
	}
	
	public int getCubeIndex()
	{
		return this.yCoordinate; //it's y coordinate because the lane size is the yCoordinate (-> see board.class)
	}

}
