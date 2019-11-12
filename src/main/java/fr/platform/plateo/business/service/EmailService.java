package fr.platform.plateo.business.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

	@Autowired
	private JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
	
	public void sendEmail(String Destinataire, String Subject,String Text) {
	        MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	         
	        try {
				helper.setTo(Destinataire);
			    helper.setSubject(Subject);
		        helper.setText(Text);
		        
		        javaMailSender.send(message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	    
	    }
	
}
