package fr.platform.plateo.presentation;

import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 */
@Controller
public class IndexController {

    /**
     * Default constructor
     */
    public IndexController() {
    	@GetMapping("/")
    	public String index() {

    		return "index";
    	}
    	
    }

    /**
     * 
     */
    private void ProService proServ;

    /**
     * 
     */
    public void String index() {
        // TODO implement here
    }

    /**
     * 
     */
    public void String proForm() {
        // TODO implement here
    }

}