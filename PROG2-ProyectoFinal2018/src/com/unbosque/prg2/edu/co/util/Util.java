package com.unbosque.prg2.edu.co.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Calendar;

public class Util {

	// algoritmos
	public static String MD5 = "MD5";
	public String destinoEstadios = "C:\\Users\\alixc\\OneDrive\\Documentos\\PROG2-ProyectoFinal2018\\WebContent\\resources\\images\\uploadEstadios\\";

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

	public Date sumarRestarDiasFecha(Date fecha, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias); // numero de días a añadir, o
													// restar en caso de días<0
		return (Date) calendar.getTime(); // Devuelve el objeto Date con los
											// nuevos días añadidos
	}

	public String generarClaveAutomatica(int cantidad) {
		String[] elementos = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n ", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

		String pass = "";
		for (int i = 0; i < cantidad; i++) {
			int el = (int) (Math.random() * 36);
			pass += elementos[el + 1];
		}
		return pass;
	}

	public void eliminarArchivo(String path) {
		File f = new File(path);
		try {
			f.delete();
		} catch (Exception x) {
			System.err.println(x);
		}
	}

	public String copyFile(InputStream in, String destination) {
		String destino = "";
		try {

			// write the inputStream to a FileOutputStream
			Util util = new Util();
			destino = util.generarClaveAutomatica(12) + ".png";
			OutputStream out = new FileOutputStream(new File(destination + destino));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			destino = "";
			System.out.println(e.getMessage());
		}
		return destino;
	}

}
