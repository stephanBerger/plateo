package fr.platform.plateo.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.platform.plateo.entity.Client;
import fr.platform.plateo.service.ClientRepository;
import fr.platform.plateo.util.CreatePdf;

@Controller
public class ClientController {

	private final ClientRepository clientRepository;

	@Autowired
	public ClientController(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	// recherche client
		@GetMapping("/clients/recherche")
		public String cherche(Model model, @RequestParam(name="recherche", defaultValue="") String mc) {
			model.addAttribute("clients", clientRepository.findByNomLike(mc));
			return "/clients/listing";
		}

		
	// login du site
	@GetMapping({"/login" })
	public String index() {
		return "login";
	}

	// index du site
		@GetMapping("/index")
		public String index(Model model) {
			return "index";
		}
		
	// listing client - index dossier client
	@GetMapping("/clients/")
	public String listing(Model model) {
		model.addAttribute("clients", clientRepository.findAll());
		return "/clients/listing";
	}

	// methode bouton nouveau client
	@GetMapping("/clients/new_client")
	public String showSignUpForm(Client client) {
		return "/clients/nouveau_client";
	}

	// methode bouton valider formulaire nouveau client
	@PostMapping("/clients/add")
	public String addClient(@Valid Client client, BindingResult result, Model model) {
		// si error reste sur le formulaire
		if (result.hasErrors()) {
			return "/clients/nouveau_client";
		}
		// si ok rajoute le client et redirect sur valid clients
		clientRepository.save(client);
		SendEMailViaGMail(client);
		return "/clients/valid_client";
	}

	private void SendEMailViaGMail(Client client) {
		 Properties props = new Properties();
		    props.put("mail.smtp.host", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", "true");
		    //Establishing a session with required user details
		    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		        protected PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication("moumeneen.peer@gmail.com", "@azerty1234");
		        }
		    });
		    try {
		        //Creating a Message object to set the email content
		        MimeMessage msg = new MimeMessage(session);
		        //Storing the comma seperated values to email addresses
		        String to = client.getEmail();
		        /*Parsing the String with defualt delimiter as a comma by marking the boolean as true and storing the email
		        addresses in an array of InternetAddress objects*/
		        InternetAddress[] address = InternetAddress.parse(to, true);
		        //Setting the recepients from the address variable
		        msg.setRecipients(Message.RecipientType.TO, address);
		        msg.setSubject("PLATEO  : votre inscription à bien été enregistré");
		        msg.setSentDate(new Date());
		        msg.setText("Bonjour " + client.getNom() + " " + client.getPrenom()+ " , bienvenue sur PLATEO, votre compte client à bien été créer, PLATEO vous remercie de votre confiance.");
		        msg.setHeader("XPriority", "1");
		        Transport.send(msg);
		        System.out.println("Email envoyé avec succès");
		    } catch (MessagingException mex) {
		        System.out.println("Impossible d'envoyer l'email : " + mex);
		    }
		
	}


	
	// methode bouton pdf client
	@GetMapping("/clients/PdfClient/{id}")
	public String PdfClient(@PathVariable("id") long id, Model model) throws IOException {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
		
		CreatePdf createpdf = new CreatePdf();
		createpdf.main(client);
		model.addAttribute("clients", clientRepository.findAll());
		return "/clients/listing";
	}
	
	

	// methode bouton modifier client
	@GetMapping("/clients/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
		model.addAttribute("client", client);
		return "/clients/modif_client";
	}

	// methode bouton modifier formulaire client
	@PostMapping("/clients/update/{id}")
	public String updateClient(@PathVariable("id") long id, @Valid Client client, BindingResult result, Model model) {
		if (result.hasErrors()) {
			client.setId(id);
			return "/clients/modif_client";
		}

		clientRepository.save(client);
		model.addAttribute("clients", clientRepository.findAll());
		return "redirect:/clients/";
	}

	// supprimer un client
	@GetMapping("/clients/delete/{id}")
	public String deleteClient(@PathVariable("id") long id, Model model) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
		clientRepository.delete(client);
		model.addAttribute("clients", clientRepository.findAll());
		return "redirect:/clients/";
	}

}
