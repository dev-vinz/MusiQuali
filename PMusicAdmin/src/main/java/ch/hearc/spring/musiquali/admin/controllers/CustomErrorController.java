
package ch.hearc.spring.musiquali.admin.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@GetMapping(value = { "/error" })
	public String error(HttpServletResponse response, Model model)
		{
		int statusCode = response.getStatus();

		String error;

		switch(statusCode)
			{
			case 401:
				error = "Vous n'êtes pas autorisé à accéder à cette ressource";
				break;
			case 403:
				error = "Vous n'avez pas un rôle suffisant pour accéder à cette ressource";
				break;
			case 404:
				error = "La ressource que vous voulez atteindre n'existe pas";
				break;
			case 500:
				error = "Une erreur interne est survenue, réessayez plus tard";
				break;
			default:
				error = "Une erreur inconnue est intervenue";
				break;
			}

		model.addAttribute("statusCode", statusCode);
		model.addAttribute("error", error);

		return "error";
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
