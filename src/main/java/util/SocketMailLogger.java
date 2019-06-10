package util;

/**
 * @author chathuram - 6/10/2019
 */
public final class SocketMailLogger
{
	private SocketMailLogger()
	{
		//constructor
	}

	public static void logInfoMessage(String message)
	{
		System.out.println(message);
	}

	public static void logErrorMessage(String message, Throwable e)
	{
		System.err.println(message);
		e.printStackTrace();
	}

	public static void logErrorMessage(String message)
	{
		System.err.println(message);
	}
}
