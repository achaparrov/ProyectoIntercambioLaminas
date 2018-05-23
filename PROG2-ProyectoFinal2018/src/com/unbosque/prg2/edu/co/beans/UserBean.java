package com.unbosque.prg2.edu.co.beans;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.dao.UserDAO;
import com.unbosque.prg2.edu.co.dao.impl.AuditDAOImpl;
import com.unbosque.prg2.edu.co.dao.impl.UserDAOImpl;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.User;
import com.unbosque.prg2.edu.co.util.Util;
import com.unbosque.prg2.edu.co.util.Correo;

@ManagedBean
@SessionScoped
public class UserBean {
	private User usuario;
	private User usuarioSesion;
	private DataModel listaUsuarios;	

	private String respuesta;

	private String password;

	// private String ip = InetAddress.getLocalHost().getHostAddress();

	static final Logger logger = Logger.getLogger(UserBean.class);

	public UserBean() {
		usuario = new User();
		usuarioSesion=new User();
	}

	public String prepararAdicionarUsuario() throws UnknownHostException {
		logger.info("Entro en el metodo prepararAdicionarUsuario");
		String respuesta = null;
		try {
			logger.info("Se prepara para adicionar un usuario y se inicializa userRegister.xhtml");
			usuario = new User();
			usuario.setActive("A");
			usuario.setDateLastPassword(new Date());
			usuario.setIp(Inet4Address.getLocalHost().getHostAddress());
			respuesta = "userRegister";
		}
		catch (Exception e) {
			logger.error("Error mientras se preparaba para adicionar un usuario y se inicializaba userRegister.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String prepararModificarUsuario() {
		logger.info("Entro en el metodo prepararModificarUsuario");
		String respuesta = null;
		try {
			logger.info("Se prepara para modificar un usuario y se inicializa userRegister.xhtml");
			usuario = (User) (listaUsuarios.getRowData());
			respuesta = "userRegister";
		}
		catch (Exception e) {
			logger.error("Error mientras se preparaba para modificar un usuario y se inicializaba userRegister.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String cambiarClave() throws UnknownHostException{
		logger.info("Entro en el metodo cambiarClave");
		String respuesta = null;
		try {
			logger.info("Se cambia la clave de un usuario y se inicializa login.xhtml");
			Util util=new Util();
			usuario=usuarioSesion;
			usuario.setDateLastPassword(new Date());
			usuario.setChangePassword(0);
			usuario.setPassword(password);
			usuario.setPassword(util.getStringMessageDigest(password, "MD5"));
			UserDAO dao = new UserDAOImpl();
			dao.update(usuario);
			usuarioSesion=usuario;

			String texto = "Cambio de Clave\nUsted ha cambiado la clave\nLogin: " + usuario.getUserName() + "\nContraseña: "
					+ password + "\n\n\n\nPor favor no responder a este correo";
			enviarCorreo(usuario.getEmailAddress(), texto,"Cambio de Clave Lamiti");


			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("C.CLAVE");
			aud.setTableId(usuario.getId());
			aud.setUserId(usuarioSesion.getId());
			aud.setTableName("user");
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			respuesta = "login";
		}
		catch (Exception e) {
			logger.error("Error mientras se cambiaba la clave de un usuario y se inicializaba login.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;

	}


	public String eliminarUsuario() throws UnknownHostException {
		logger.info("Entro en el metodo eliminarUsuario");
		String respuesta = null;
		try {
			logger.info("Se elimina un usuario y se inicializa listUser.xhtml");
			User usuarioTemp = (User) (listaUsuarios.getRowData());
			UserDAO dao = new UserDAOImpl();
			usuarioTemp.setActive("I");
			dao.update(usuarioTemp);

			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Desactivar");
			aud.setTableId(usuarioTemp.getId());
			aud.setTableName("user");
			aud.setUserId(usuarioSesion.getId());
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			respuesta = "listUser";
		}
		catch (Exception e) {
			logger.error("Error mientras se eliminaba un usuario y se inicializaba listUser.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String activarUsuario() throws UnknownHostException {
		logger.info("Entro en el metodo activarUsuario");
		String respuesta = null;
		try {
			logger.info("Se activa un usuario y se inicializa listUser.xhtml");
			User usuarioTemp = (User) (listaUsuarios.getRowData());
			UserDAO dao = new UserDAOImpl();
			usuarioTemp.setActive("A");
			dao.update(usuarioTemp);

			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Activar");
			aud.setTableId(usuarioTemp.getId());
			aud.setTableName("user");
			aud.setUserId(usuarioSesion.getId());
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			respuesta = "listUser";
		}
		catch (Exception e) {
			logger.error("Error mientras se activaba un usuario y se inicializaba listUser.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String adicionarUsuario() throws UnknownHostException {
		logger.info("Entro en el metodo adicionarUsuario");
		String respuesta = null;
		try {
			logger.info("Se adiciona un usuario y se inicializa listUser.xhtml");
			UserDAO dao = new UserDAOImpl();
			dao.save(usuario);

			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Nuevo");
			aud.setTableId(usuario.getId());
			aud.setTableName("user");
			aud.setUserId(usuarioSesion.getId());
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			respuesta = "listUser";
		}
		catch (Exception e) {
			logger.error("Error mientras se adicionaba un usuario y se inicializaba listUser.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String generarClaveAutomatica() {
		logger.info("Entro en el metodo adicioarClaveAutomatica");
		String respuesta = null;
		try {
			logger.info("Se genera una clave automatica");
			String[] elementos = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
					"i", "j", "k", "l", "m", "n ", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

			String pass = "";
			for (int i = 0; i < 8; i++) {
				int el = (int) (Math.random() * 37);
				pass += elementos[el];
			}
			respuesta = pass;
		}
		catch (Exception e) {
			logger.error("Error mientras se generaba una clave automatica, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String prepararAdicionarUsuarioInicio() throws UnknownHostException {
		logger.info("Entro en el metodo prepararAdicionarUsuarioInicio");
		String respuesta = null;
		try {
			logger.info("Se prepara para adicionar un usuario y se inicializa userRegisterInicio.xhtml");
			usuario = new User();
			usuario.setActive("A");
			usuario.setDateLastPassword(new Date());
			usuario.setUserType("USER");
			usuario.setIp(Inet4Address.getLocalHost().getHostAddress());
			respuesta = "userRegisterInicio";
		}
		catch (Exception e) {
			logger.error("Error mientras se preparaba para adicionar un usuario y se inicializaba userRegisterInicio.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;

	}

	public String adicionarUsuarioInicio() throws UnknownHostException {
		logger.info("Entro en el metodo adicionarUsuarioInicio");
		String respuesta = null;
		try {
			logger.info("Se adiciona un usuario y se inicializa login.xhtml");
			UserDAO dao = new UserDAOImpl();
			Util util = new Util();
			String clave = generarClaveAutomatica();
			usuario.setPassword(util.getStringMessageDigest(clave, "MD5"));
			usuario.setChangePassword(1);
			usuario.setDateChangePassword(new Date());
			dao.save(usuario);
			String texto = "Bienvenido a Lamiti\nUsted se ha registrado\nLogin: " + usuario.getUserName() + "\nContraseña: "
					+ clave + "\n\n\n\nPor favor no responder a este correo";
			enviarCorreo(usuario.getEmailAddress(), texto,"Bienvenido a Lamiti");

			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Registro");
			aud.setTableId(usuario.getId());
			aud.setTableName("user");
			aud.setUserId(usuarioSesion.getId());
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			respuesta = "login";
		}
		catch (Exception e) {
			logger.error("Error mientras se adicionaba un usuario y se inicializaba login.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String login() throws UnknownHostException {
		logger.info("Entro en el metodo login");
		String respuesta = null;
		try {

			Util util = new Util();
			String userName1 = usuario.getUserName();
			String userPassword1 = usuario.getPassword();
			String userPassword2 = util.getStringMessageDigest(userPassword1, "MD5");

			User user1 = new UserDAOImpl().buscarUsuario(userName1, userPassword2);
			if (user1 != null) {
				usuarioSesion=user1;
				String tipo = user1.getUserType();
				if (user1.getChangePassword() == 1) {

					Date fechaSumada=util.sumarRestarDiasFecha(user1.getDateChangePassword(), 2);				
					logger.info("Se cambia la contraseña de un usuario y se inicializa changePassword.xhtml");
					if(fechaSumada.after(new Date())||fechaSumada.equals(new Date())){
						respuesta = "changePassword";	
					}else{

						logger.info("Se pide solicitar una nueva clave y se inicializa errorPage.xhtml");
						this.respuesta="Debes solicitar una nueva clave";
						respuesta = "errorPage";	
					}
				} else {
					logger.info("Se logea un usuario y se inicializa menuUser.xhtml");
					if (tipo.equals("USER")) {
						Audit aud = new Audit();
						aud.setCreateDate(new Date());
						aud.setOperation("Login");
						aud.setTableName("user");
						aud.setUserId(usuarioSesion.getId());
						aud.setIp(Inet4Address.getLocalHost().getHostAddress());
						AuditDAO audiDao = new AuditDAOImpl();
						audiDao.save(aud);
						respuesta = "menuUser";
					} else if (tipo.equals("ADMIN")) {

						logger.info("Se logea el administrador y se inicializa menuAdmin.xhtml");
						Audit aud = new Audit();
						aud.setCreateDate(new Date());
						aud.setOperation("Login");
						aud.setTableName("user");
						aud.setUserId(usuarioSesion.getId());
						aud.setIp(Inet4Address.getLocalHost().getHostAddress());
						AuditDAO audiDao = new AuditDAOImpl();
						audiDao.save(aud);
						respuesta = "menuAdmin";
					}
				}
			} else {
				logger.info("No se ha podido logear y se inicializa login.xhtml");
				Audit aud = new Audit();
				aud.setCreateDate(new Date());
				aud.setOperation("Error Login");
				aud.setTableName("user");

				aud.setIp(Inet4Address.getLocalHost().getHostAddress());
				AuditDAO audiDao = new AuditDAOImpl();
				audiDao.save(aud);
				respuesta = "login";
			}
		}
		catch (Exception e) {
			logger.error("Error mientras se generaba el logeo, el cambio de contraseña o el regreso y se inicializaba el .xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String modificarUsuario() throws UnknownHostException {
		logger.info("Entro en el metodo modificarUsuario");
		String respuesta = null;
		try {
			logger.info("Se modifica un usuario y se inicializa listUser.xhtml");
			UserDAO dao = new UserDAOImpl();
			dao.update(usuario);
			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Actualizar");
			aud.setTableId(usuario.getId());
			aud.setTableName("user");
			aud.setUserId(usuarioSesion.getId());
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			respuesta = "listUser";
		}
		catch (Exception e) {
			logger.error("Error mientras se modificaba el usuario y se inicializaba el listUser.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public User getUsuarioSesion(){
		logger.info("Entro en el metodo getUsuarioSesion");
		User respuesta = null;
		try {
			logger.info("Se da un User o usuarioSesion");
			respuesta = usuarioSesion;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba un User o usuarioSesion, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}
	public void  setUsuarioSesion(User usuarioSesion){
		logger.info("Entro en el metodo setUsuarioSesion");
		try {
			logger.info("Se da cambia un User o usuarioSesion");
			this.usuarioSesion=usuarioSesion;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambia un User o usuarioSesion, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public User getUsuario() {
		logger.info("Entro en el metodo getUsuario");
		User respuesta = null;
		try {
			logger.info("Se da un User o usuario");
			respuesta = usuario;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba un User o usuario, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public void setUsuario(User usuario) {
		logger.info("Entro en el metodo setUsuario");
		try {
			logger.info("Se da cambia un User o usuario");
			this.usuario = usuario;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambia un User o usuario, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public DataModel getListarUsuarios() {
		logger.info("Entro en el metodo getListarUsuarios");
		DataModel respuesta = null;
		try {
			logger.info("Se da un DataModel o la lista de usuarios");
			List<User> lista = new UserDAOImpl().list();
			listaUsuarios = new ListDataModel(lista);
			respuesta = listaUsuarios;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba un DataModel o la lista de usuarios, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String recordarClave() throws UnknownHostException{
		logger.info("Entro en el metodo recordarClave");

		String respuesta = null;
		try {
			logger.info("Se le recuerda la contraseña a el usuario y se inicializa login.xhtml");
			usuario = new UserDAOImpl().buscarUsuarioEmail(usuario.getEmailAddress());

			if(usuario==null){
				this.respuesta="Email No Encontrado";
				return "errorPage";			
			}else{
				Util util=new Util();
				String clave = generarClaveAutomatica();
				usuario.setPassword(util.getStringMessageDigest(clave, "MD5"));
				usuario.setChangePassword(1);
				usuario.setDateChangePassword(new Date());
				UserDAO dao = new UserDAOImpl();
				dao.update(usuario);
				String texto = "Solicitud cambio de clave para " + usuario.getUserName() + "\n su nueva Contraseña es: "
						+ clave + "\n\n\n\nPor favor no responder a este correo";
				enviarCorreo(usuario.getEmailAddress(), texto,"Solicitud cambio clave Lamiti");

				// Auditoria ELemento
				Audit aud = new Audit();
				aud.setCreateDate(new Date());
				aud.setOperation("Registro");
				aud.setTableId(usuario.getId());
				aud.setTableName("user");
				aud.setUserId(usuarioSesion.getId());
				aud.setIp(Inet4Address.getLocalHost().getHostAddress());
				AuditDAO audiDao = new AuditDAOImpl();
				audiDao.save(aud);
				// TERMINA Auditoria ELemento
			}
			respuesta = "login";
		}
		catch (Exception e) {
			logger.error("Error mientras se le recordaba la contraseña a un usuario y se inicializaba login.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public void enviarCorreo(String entrada, String mensaje,String asunto) {
		logger.info("Entro en el metodo enviarCorreo");

		try {
			logger.info("Se envia un correo");
			Correo correo = new Correo();
			String salida = "lamiti2018@gmail.com";
			String clave = "Mundial2018";
			correo.enviarcorreo(salida, clave, entrada, mensaje, asunto);
		}
		catch (Exception e) {
			logger.error("Error mientras se enviaba un correo, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void setRespuesta(String respuesta){
		logger.info("Entro en el metodo setRespuesta");
		try {
			logger.info("Se cambia la respuesta");
			this.respuesta=respuesta;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambiaba la respuesta, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String getRespuesta( ){
		logger.info("Entro en el metodo getRespuesta");
		String respuesta = null;
		try {
			logger.info("Se da la respuesta");
			respuesta = this.respuesta;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba la respuesta, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public void setPassword(String password){
		logger.info("Entro en el metodo setPassword");
		try {
			logger.info("Se cambia el password");
			this.password=password;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambiaba el password, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String getPassword( ){
		logger.info("Entro en el metodo getPassword");
		String respuesta = null;
		try {
			logger.info("Se da el password");
			respuesta = this.password;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el password, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

}
