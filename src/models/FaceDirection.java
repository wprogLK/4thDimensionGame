package models;

public enum FaceDirection 
{
	UP,
	DOWN,
	LEFT,
	RIGHT,
	BACK,
	FRONT;
	
	public FaceDirection getOppositeDirection()
	{
		switch(this)
		{
			case UP:
			{
				return DOWN;
			}
			case DOWN:
			{
				return UP;
			}
			case LEFT:
			{
				return RIGHT;
			}
			case RIGHT:
			{
				return LEFT;
			}
			case FRONT:
			{
				return BACK;
			}
			case BACK:
			{
				return FRONT;
			}
			default:
			{
				System.out.println("Error: unknown faceDirection in getOppositeDirection-Method in FaceDirection!"); //TODO throw an exception
				return null;
			}
		}
	}
}
