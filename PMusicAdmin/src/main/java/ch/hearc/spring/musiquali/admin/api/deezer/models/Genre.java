
package ch.hearc.spring.musiquali.admin.api.deezer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Genre
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

	@JsonProperty("picture_small")
	private String pictureSmall;

	@JsonProperty("picture_medium")
	private String pictureMedium;

	@JsonProperty("picture_big")
	private String pictureBig;

	@JsonProperty("picture_XL")
	private String pictureXL;
	}
