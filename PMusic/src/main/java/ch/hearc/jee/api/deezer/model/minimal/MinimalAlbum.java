
package ch.hearc.jee.api.deezer.model.minimal;

public class MinimalAlbum
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MinimalAlbum(long id, String title, String cover, String coverSmall, String coverMedium, String coverBig, String coverXl, String md5Image, String tracklist)
		{
		// Inputs
			{
			this.id = id;
			this.title = title;
			this.cover = cover;
			this.coverSmall = coverSmall;
			this.coverMedium = coverMedium;
			this.coverBig = coverBig;
			this.coverXl = coverXl;
			this.md5Image = md5Image;
			this.tracklist = tracklist;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public long getId()
		{
		return this.id;
		}

	public String getTitle()
		{
		return this.title;
		}

	public String getCover()
		{
		return this.cover;
		}

	public String getCoverSmall()
		{
		return this.coverSmall;
		}

	public String getCoverMedium()
		{
		return this.coverMedium;
		}

	public String getCoverBig()
		{
		return this.coverBig;
		}

	public String getCoverXl()
		{
		return this.coverXl;
		}

	public String getMd5Image()
		{
		return this.md5Image;
		}

	public String getTracklist()
		{
		return this.tracklist;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private long id;

	private String title;
	private String cover;
	private String coverSmall;
	private String coverMedium;
	private String coverBig;
	private String coverXl;
	private String md5Image;
	private String tracklist;
	}
