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
	
	public enum CubeState
	{
		LOCKED,
		PLACEHOLDER,
		REAL;
	}
	
	private CubeState state;
	
	/**
	 * 
	 */
	public Cube(int xCoordiante, int yCoordinate, int zCoordinate, CubeState state)
	{
		this.xCoordiante = xCoordiante;
		this.yCoordinate = yCoordinate;
		this.zCoordinate = zCoordinate;
		
		this.state = state;
	}
	
	public Cube(int xCoordiante, int yCoordinate, int zCoordinate)
	{
		this.xCoordiante = xCoordiante;
		this.yCoordinate = yCoordinate;
		this.zCoordinate = zCoordinate;
		
		this.state = CubeState.REAL;
	}
	
	public int getCubeIndex()
	{
		return this.yCoordinate; //it's y coordinate because the lane size is the yCoordinate (-> see board.class)
	}
	
	public CubeState getState()
	{
		return this.state;
	}
	
	public void setState(CubeState state)
	{
		this.state = state;
	}

	public int[] getCoordinates()
	{
		int[] coordinates = {this.xCoordiante,this.yCoordinate,this.zCoordinate};
		
		return coordinates;
		
	}
	
	@Override
	public String toString()
	{
		return "CUBE: State: " + this.state + " at [ " + this.xCoordiante + " | " + this.yCoordinate + " | " + this.zCoordinate + " ]";
	}
}
