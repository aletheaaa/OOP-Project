package is442.portfolioAnalyzer.Tools.Email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class SendEmail {
  /**
   * Outgoing Mail (SMTP) Server requires
   * TLS or SSL: smtp.gmail.com (use authentication)
   * Use Authentication: Yes
   * Port for TLS/STARTTLS: 587
   * 
   * @param toEmail
   */

  public static void sendEmail(String toEmail, String subject, String body) {
    // Sender email details
    final String fromEmail = "uchef216@gmail.com";
    final String password = "cnhpdfukrpwseoic";

    System.out.println("sendEmail Start");
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
    props.put("mail.smtp.port", "587"); // TLS Port
    props.put("mail.smtp.auth", "true"); // enable authentication
    props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

    // create Authenticator object to pass in Session.getInstance argument
    Authenticator auth = new Authenticator() {
      // override the getPasswordAuthentication method
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(fromEmail, password);
      }
    };
    Session session = Session.getInstance(props, auth);

    EmailUtil.sendEmail(session, fromEmail, toEmail, subject, body);
  }
}
