package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Método que envia un correo desde la cuenta
 *
 */

public class Mail {

	public boolean sendMail(String out, String pass,String email,String mess,String subject){
		boolean send = false;
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

			System.out.print(message);

			Transport transport=session.getTransport("smtp");
			transport.connect(host,out,pass);

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			send=true;
			System.out.print(send);

		}catch (Exception e){
			e.printStackTrace();

		}
		return send;

	}

}