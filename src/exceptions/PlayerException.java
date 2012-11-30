package exceptions;

public class PlayerException extends Exception
{

	public PlayerException(String message) 
	{
		super("ERROR: " + message);
	}

}
