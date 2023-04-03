
package ch.hearc.spring.musiquali.game.api.models.deezer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an artist on Deezer
 */
public class Artist
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/**
	 * Gets the ID
	 * @return An ID
	 */
	public Long getId()
		{
		return this.id;
		}

	/**
	 * Gets the name
	 * @return A name
	 */
	public String getName()
		{
		return this.name;
		}

	/**
	 * Gets the link
	 * @return A link
	 */
	public String getLink()
		{
		return this.link;
		}

	/**
	 * Gets the picture in small size
	 * @return A small picture
	 */
	public String getPictureSmall()
		{
		return this.pictureSmall;
		}

	/**
	 * Gets the picture in medium size
	 * @return A medium picture
	 */
	public String getPictureMedium()
		{
		return this.pictureMedium;
		}

	/**
	 * Gets the picture in big size
	 * @return A big picture
	 */
	public String getPictureBig()
		{
		return this.pictureBig;
		}

	/**
	 * Gets the picture in XL size
	 * @return A XL picture
	 */
	public String getPictureXL()
		{
		return this.pictureXL;
		}

	/**
	 * Gets the number of albums
	 * @return A number of albums
	 */
	public String getNbAlbums()
		{
		return this.nbAlbums;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("link")
	private String link;

	@JsonProperty("picture_small")
	private String pictureSmall;

	@JsonProperty("picture_medium")
	private String pictureMedium;

	@JsonProperty("picture_big")
	private String pictureBig;

	@JsonProperty("picture_xl")
	private String pictureXL;

	@JsonProperty("nb_album")
	private String nbAlbums;
	}
