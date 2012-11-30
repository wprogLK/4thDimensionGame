package models;

public enum FaceDirection 
{
	UP(0,1,0),
	DOWN(0,-1,0),
	LEFT(-1,0,0),
	RIGHT(1,0,0),
	BACK(0,0,-1),
	FRONT(0,0,1);
	
	private FaceDirection prevX;
	private FaceDirection nextX;
	
	private FaceDirection prevY;
	private FaceDirection nextY;
	
	private FaceDirection prevZ;
	private FaceDirection nextZ;
	
	private FaceDirection opposite;
	
	//Relative position of the neighbour cube
	private int relPosX;
	private int relPosY;
	private int relPosZ;
	
	private FaceDirection(int relPosX, int relPosY, int relPosZ)
	{
		this.relPosX = relPosX;
		this.relPosY = relPosY;
		this.relPosZ = relPosZ;
	}
	
	static
	{
		UP.setNext("FRONT","UP","LEFT");		
		DOWN.setNext("BACK","DOWN","RIGHT");
		LEFT.setNext("LEFT","FRONT","DOWN");
		RIGHT.setNext("RIGHT","BACK","UP");
		BACK.setNext("UP","LEFT","BACK");    //nextZ is BACK, because FRONT is possible in the Z Axis
		FRONT.setNext("DOWN","RIGHT","FRONT");//nextZ is FRONT, because BACK is possible in the Z Axis
		
		UP.setOpposite("DOWN");
		DOWN.setOpposite("UP");
		
		LEFT.setOpposite("RIGHT");
		RIGHT.setOpposite("LEFT");
		
		BACK.setOpposite("FRONT");
		FRONT.setOpposite("BACK");
	}
	
	private void setNext(String nextX, String nextY, String nextZ) 
	{
		this.nextX = FaceDirection.valueOf(nextX);
		this.nextY = FaceDirection.valueOf(nextY);
		this.nextZ = FaceDirection.valueOf(nextZ);
		
		this.nextX.setPrevX(this);
		this.nextY.setPrevY(this);
		this.nextZ.setPrevZ(this);
	}
	
	private void setOpposite(String opposite)
	{
		this.opposite = FaceDirection.valueOf(opposite);
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
//		return values()[this.ordinal() + (this.ordinal() % 2)*2 -1]; //TODO Test this
		return this.opposite;
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
	
	public FaceDirection getNextFaceDirection(int dx, int dy, int dz)
	{
		if(this.equals(UP)) //Otherwise the controller is inverted for left and right //TODO Test this! //TODO let the player decide if he/she wants it or not!
		{
			dx = -dx;
		}
		
		if(dx==-1)
		{
			System.out.println("prevZ of " + this + " is " + this.prevZ());
			return this.prevZ();
		}
		else if(dx==1)
		{
			System.out.println("nextZ of " + this + " is " + this.nextZ());
			return this.nextZ();
		}
		else if(dy==-1)
		{
			System.out.println("nextX");
			return this.nextX();
		}
		else if(dy==1)
		{
			System.out.println("prevX");
			return this.prevX();
		}
		else if(dz==-1)
		{
			System.out.println("prevY");
			return this.prevY();
		}
		else if(dz==1)
		{
			System.out.println("nextY");
			return this.nextY();
		}
		else
		{
			System.out.println("Error: in getNextFaceDirection() in FaceDirection.class!"); //TODO Throw an exception
			return this;
		}
	}
	
	public int getRelPosXNeighbour()
	{
		return this.relPosX;
	}
	
	public int getRelPosYNeighbour()
	{
		return this.relPosY;
	}
	
	public int getRelPosZNeighbour()
	{
		return this.relPosZ;
	}
	
	public int[] getRelPosNeighbour()
	{
		int[] pos = {this.relPosX,this.relPosY,this.relPosZ};
		return pos;
	}
}
