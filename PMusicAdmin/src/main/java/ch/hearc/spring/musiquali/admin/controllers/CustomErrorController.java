
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
		String description = null;

		switch(statusCode)
			{
			case 401:
				error = "Non authentifié";
				description = "Une authentification est demandée pour accéder à cette ressource.";
				break;
			case 403:
				error = "Accès interdit";
				description = "Nous sommes désolés, mais vous ne possédez pas un rôle suffisant pour accéder à cette ressource.";
				break;
			case 404:
				error = "Page introuvable";
				description = "Oups ! Il semblerait que la page que vous recherchez n'existe pas ou est introuvable.";
				break;
			case 500:
				error = "Erreur interne";
				description = "Une erreur est survenue, veuillez réessayer plus tard lorsqu'elle aura été corrigée.";
				break;
			default:
				error = "Erreur";
				description = "Une erreur non définie est intervenue, merci de bien vouloir réessayer plus tard";
				break;
			}

		model.addAttribute("statusCode", statusCode);
		model.addAttribute("error", error);
		model.addAttribute("description", description);

		return "error";
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
