package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	public static void sendMail(String email,String mess,String subject){
		String out = "Soccardweb@gmail.com";
		String pass = "SOCCARD2018";
		try{

			String host="smtp.gmail.com";

			Properties prop=System.getProperties();

			prop.put("mail.smtp.starttls.enable","true");
			prop.put("mail.smtp.host",host);
			prop.put("mail.smtp.user",out);
			prop.put("mail.smtp.password",pass);
			prop.put("mail.smtp.port",587);
			prop.put("mail.smtp.auth","true");

			Session session=Session.getDefaultInstance(prop,null);
			MimeMessage message= new MimeMessage(session);

			message.setFrom(new InternetAddress(out));

			message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject(subject);
			message.setText(mess);

			Transport transport=session.getTransport("smtp");
			transport.connect(host,out,pass);

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}