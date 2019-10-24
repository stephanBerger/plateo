package fr.platform.plateo.util;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import fr.platform.plateo.entity.Client;

public class CreatePdf {

	
	 public static void main(Client client) throws IOException {
		 
	        try (PDDocument doc = new PDDocument()) {

	            PDPage myPage = new PDPage();
	            doc.addPage(myPage);

	            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {
	            	
	                cont.beginText();

	                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
	                cont.setLeading(14.5f);

	                cont.newLineAtOffset(25, 750);
	                String line1 = client.getNom() + " " + client.getPrenom();
	                cont.showText(line1);

	                cont.newLine();

	                String line2 = client.getAdresse();
	                cont.showText(line2);
	                cont.newLine();

	                String line3 = client.getCode_postal() + " - " + client.getVille();
	                cont.showText(line3);
	                cont.newLine();

	                String line4 = client.getEmail();
	                cont.showText(line4);
	                cont.newLine();

	                cont.endText();
	            }

	            doc.save("src/main/resources/" + client.getEmail() +".pdf");
	        }
	    }
	
}
