package fr.platform.plateo.controller;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

	@Controller
	public class InscriptionController {

		//index du site
		/*@GetMapping("/index")
		public String index(Model model) {
			return "index";
		}*/
		
		@GetMapping("/inscription")
		public String proForm() {
			return "inscription";
		}
		
		
		
		
}
