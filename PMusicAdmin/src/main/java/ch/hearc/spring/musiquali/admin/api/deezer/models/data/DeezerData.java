
<<<<<<<< HEAD:PMusicAdmin/src/main/java/ch/hearc/spring/musiquali/admin/api/deezer/models/data/DeezerData.java
package ch.hearc.spring.musiquali.admin.api.deezer.models.data;
========
package ch.hearc.spring.musiquali.admin.controllers;
>>>>>>>> 93fee57 (Create RestMusicController and adapt repository):PMusicAdmin/src/main/java/ch/hearc/spring/musiquali/admin/controllers/MusicController.java

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeezerData<T>
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

	public List<T> getData()
		{
		return this.data;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	@JsonProperty("data")
	private List<T> data;
	}
