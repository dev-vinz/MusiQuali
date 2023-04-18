
package ch.hearc.spring.musiquali.game.jms.model;

public class Log
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Log(String message, LogType logType)
		{
		this.message = message;
		this.logType = logType;
		this.timestamp = System.currentTimeMillis();
		}

	public Log()
		{
		this("No message", LogType.INFO);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public String toString()
		{
		return "Log [message=" + this.message + ", logType=" + this.logType + ", timestamp=" + this.timestamp + "]";
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getMessage()
		{
		return this.message;
		}

	public LogType getLogType()
		{
		return this.logType;
		}

	public Long getTimestamp()
		{
		return this.timestamp;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setLogType(LogType logType)
		{
		this.logType = logType;
		}

	public void setMessage(String message)
		{
		this.message = message;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Nothing

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private String message;
	private LogType logType;
	private Long timestamp;
	}
