package com.unbosque.prg2.edu.co.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Correo {

	public boolean enviarcorreo(String salida, String clave,String entrada,String mensaje,String asunto){
		boolean enviado = false;
		try{

			String host="smtp.gmail.com";

			Properties prop=System.getProperties();

			prop.put("mail.smtp.starttls.enable","true");
			prop.put("mail.smtp.host",host);
			prop.put("mail.smtp.user",salida);
			prop.put("mail.smtp.password",clave);
			prop.put("mail.smtp.port",587);
			prop.put("mail.smtp.auth","true");

			Session session=Session.getDefaultInstance(prop,null);
			MimeMessage message= new MimeMessage(session);

			message.setFrom(new InternetAddress(salida));

			message.setRecipient(Message.RecipientType.TO, new InternetAddress(entrada));
			message.setSubject(asunto);
			message.setText(mensaje);

			System.out.print(message);

			Transport transport=session.getTransport("smtp");
			transport.connect(host,salida,clave);

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			enviado=true;
			System.out.print(enviado);

		}catch (Exception e){
			e.printStackTrace();

		}
		return enviado;

	}

}