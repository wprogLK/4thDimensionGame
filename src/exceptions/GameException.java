package exceptions;

public class GameException extends Exception
{

	public GameException(String message) 
	{
		super("ERROR: " + message);
	}

}
