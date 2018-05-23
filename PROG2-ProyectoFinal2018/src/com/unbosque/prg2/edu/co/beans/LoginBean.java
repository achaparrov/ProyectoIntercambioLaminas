package com.unbosque.prg2.edu.co.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;

	static final Logger logger = Logger.getLogger(LoginBean.class);

	public String getUserName() {
		logger.info("Entro en el metodo getUserName");
		String respuesta = null;

		try {
			logger.info("Se da el userName");
			respuesta = userName;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el userName, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}
	public void setUserName(String userName) {
		logger.info("Entro en el metodo setUserName");

		try {
			logger.info("Se cambia el userName");
			this.userName = userName;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambiaba el userName, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}
	public String getPassword() {
		logger.info("Entro en el metodo getPassword");
		String respuesta = null;

		try {
			logger.info("Se da el password");
			respuesta = password;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el password, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}
	public void setPassword(String password) {
		logger.info("Entro en el metodo setPassword");

		try {
			logger.info("Se cambia el password");
			this.password = password;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambiaba el password, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String prueba(){
		logger.info("Entro en el metodo prueba");
		String respuesta = null;
		try {
			logger.info("Se da la prueba que es inicioAdmin.xhtml");
			respuesta = "inicioAdmin";
		}
		catch (Exception e) {
			logger.error("Error mientras se daba la prueba que es inicioAdmin.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

}
