package models;

public enum FaceDirection 
{
	UP,
	DOWN,
	LEFT,
	RIGHT,
	BACK,
	FRONT;
	
	private FaceDirection prev;
	private FaceDirection next;
	
	
	public FaceDirection getOppositeDirection()
	{
		return values()[this.ordinal() + (this.ordinal() % 2)*2 -1];
	}
}
