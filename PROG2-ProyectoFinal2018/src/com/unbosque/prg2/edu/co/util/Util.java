package com.unbosque.prg2.edu.co.util;

	import java.security.MessageDigest;
	import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Calendar;

	public class Util {

		// algoritmos
		public static String MD5 = "MD5";

		private static String toHexadecimal(byte[] digest) {
			String hash = "";
			for (byte aux : digest) {
				int b = aux & 0xff;
				if (Integer.toHexString(b).length() == 1)
					hash += "0";
				hash += Integer.toHexString(b);
			}
			return hash;
		}

		
		public static String getStringMessageDigest(String message, String algorithm) {
			byte[] digest = null;
			byte[] buffer = message.getBytes();
			try {
				MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
				messageDigest.reset();
				messageDigest.update(buffer);
				digest = messageDigest.digest();
			} catch (NoSuchAlgorithmException ex) {
				System.out.println("Error creando Digest");
			}
			return toHexadecimal(digest);
		}

		public static long ahora() {
			return Calendar.getInstance().getTimeInMillis();
		}
		
		 public Date sumarRestarDiasFecha(Date fecha, int dias){			
			       Calendar calendar = Calendar.getInstance();		
			       calendar.setTime(fecha); // Configuramos la fecha que se recibe		
			       calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
			       return (Date) calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos				
			  }
		
	}


