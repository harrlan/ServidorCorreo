package rest.sendmail;

import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvioCorreo {
   private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";

   @RequestMapping("/{correo}/{usuario}")
   public void SendMail(@PathVariable String correo, @PathVariable String usuario) throws Exception
   {
      Properties properties = new Properties();
         properties.put("mail.transport.protocol", "smtp");
         properties.put("mail.smtp.host", SMTP_HOST_NAME);
         properties.put("mail.smtp.port", 587);
         properties.put("mail.smtp.auth", "true");
         Authenticator auth = new SMTPAuthenticator();
         Session mailSession = Session.getDefaultInstance(properties, auth);
         
         MimeMessage message = new MimeMessage(mailSession);
         Multipart multipart = new MimeMultipart("alternative");
         BodyPart part1 = new MimeBodyPart();
         part1.setText(usuario  + "te ha añadido como amigo.");
         BodyPart part2 = new MimeBodyPart();
         part2.setContent(
             "<p>" + usuario + " te ha añadido como amigo</p>",
             "text/html");
         multipart.addBodyPart(part1);
         multipart.addBodyPart(part2);
         message.setFrom(new InternetAddress("app@actividades.com"));
         message.addRecipient(Message.RecipientType.TO,
            new InternetAddress(correo));
         message.setSubject("Notificación de amistad");
         message.setContent(multipart);
         
         Transport transport = mailSession.getTransport();
      // Connect the transport object.
      transport.connect();
      // Send the message.
      transport.sendMessage(message, message.getAllRecipients());
      // Close the connection.
      transport.close();
      
   }
   private class SMTPAuthenticator extends javax.mail.Authenticator {
	   public PasswordAuthentication getPasswordAuthentication() {
	      String username = SMTP_AUTH_USER;
	      String password = SMTP_AUTH_PWD;
	      return new PasswordAuthentication(username, password);
	   }
}

}
