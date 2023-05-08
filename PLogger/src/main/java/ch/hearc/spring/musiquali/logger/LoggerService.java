
package ch.hearc.spring.musiquali.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.hearc.spring.musiquali.logger.model.Log;

/**
 * singleton
 */
public class LoggerService
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private LoggerService()
		{
		this.file = new File(FILENAME);
		this.formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		initLogFile();
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static synchronized LoggerService getInstance()
		{
		if (instance == null)
			{
			instance = new LoggerService();
			}

		return instance;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void log(Log log)
		{
		try
			{
			this.fileWriter = new FileWriter(FILENAME, true);

			String type = log.getLogType().name();
			Date dateLog = new Date(log.getTimestamp());
			String stringDateLog = this.formatter.format(dateLog);
			String message = log.getMessage();

			this.fileWriter.write(type + " [" + stringDateLog + "] : " + message);
			this.fileWriter.write(NEW_LINE);

			this.fileWriter.close();
			}
		catch (IOException e)
			{
			System.err.println("An error occured while writting in the log file");
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void initLogFile()
		{
		try
			{
			if (this.file.createNewFile())
				{
				// new file created
				}
			else
				{
				// already existing
				}

			}
		catch (IOException e)
			{
			System.err.println("An error occured while openning the log file");
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Outputs

	// Tools
	private File file;
	private FileWriter fileWriter;
	private SimpleDateFormat formatter;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String FILENAME = "MusiQualiAuthAccess_log.txt";
	private static final String NEW_LINE = System.lineSeparator();
	private static LoggerService instance = null;

	}
