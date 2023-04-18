
package ch.hearc.spring.musiquali.logger.model;

public class Log
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	// Nothing

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

	public void setTimestamp(Long timestamp)
		{
		this.timestamp = timestamp;
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
