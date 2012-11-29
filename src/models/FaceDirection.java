package models;

public enum FaceDirection 
{
//	UP("FRONT","UP","LEFT"),			
//	DOWN("BACK","DOWN","RIGHT"),
//	LEFT("LEFT","FRONT","DOWN"),
//	RIGHT("RIGHT","BACK","UP"),
//	BACK("DOWN","RIGHT","FRONT"),
//	FRONT("UP","LEFT","BACK");
	UP,
	DOWN,
	LEFT,
	RIGHT,
	BACK,
	FRONT;
	
	private FaceDirection prevX;
	private FaceDirection nextX;
	
	private FaceDirection prevY;
	private FaceDirection nextY;
	
	private FaceDirection prevZ;
	private FaceDirection nextZ;
	
//	FaceDirection(String prevX, String prevY,String prevZ, String nextX, String nextY, String nextZ)
//	{
//		this.prevX = FaceDirection.valueOf(prevX);
//		this.nextX = FaceDirection.valueOf(nextX);
//		
//		this.prevY = FaceDirection.valueOf(prevY);
//		this.nextY = FaceDirection.valueOf(nextY);
//		
//		this.prevZ = FaceDirection.valueOf(prevZ);
//		this.nextZ = FaceDirection.valueOf(nextZ);
//	}
	
	static
	{
		UP.setNext("FRONT","UP","LEFT");		
		DOWN.setNext("BACK","DOWN","RIGHT");
		LEFT.setNext("LEFT","FRONT","DOWN");
		RIGHT.setNext("RIGHT","BACK","UP");
		BACK.setNext("UP","LEFT","FRONT");
		FRONT.setNext("DOWN","RIGHT","BACK");
	}
	
//	private FaceDirection(String nextX, String nextY, String nextZ) 
//	{
//		this.nextX = FaceDirection.valueOf(nextX);
//		this.nextY = FaceDirection.valueOf(nextY);
//		this.nextZ = FaceDirection.valueOf(nextZ);
//		
//		this.nextX.setPrevX(this);
//		this.nextY.setPrevY(this);
//		this.nextZ.setPrevZ(this);
//	}
	
	private void setNext(String nextX, String nextY, String nextZ) 
	{
		this.nextX = FaceDirection.valueOf(nextX);
		this.nextY = FaceDirection.valueOf(nextY);
		this.nextZ = FaceDirection.valueOf(nextZ);
		
		this.nextX.setPrevX(this);
		this.nextY.setPrevY(this);
		this.nextZ.setPrevZ(this);
	}
	
	private void setPrevX(FaceDirection prevX)
	{
		this.prevX = prevX;
	}
	
	private void setPrevY(FaceDirection prevY)
	{
		this.prevY = prevY;
	}
	
	private void setPrevZ(FaceDirection prevZ)
	{
		this.prevZ = prevZ;
	}
	
	public FaceDirection getOppositeDirection()
	{
		return values()[this.ordinal() + (this.ordinal() % 2)*2 -1]; //TODO Test this
	}
	
	public FaceDirection nextX()
	{
		return nextX;
	}
	
	public FaceDirection nextY()
	{
		return nextY;
	}
	
	public FaceDirection nextZ()
	{
		return nextZ;
	}
	
	public FaceDirection prevX()
	{
		return prevX;
	}
	
	public FaceDirection prevY()
	{
		return prevY;
	}
	
	public FaceDirection prevZ()
	{
		return prevZ;
	}
	

}
