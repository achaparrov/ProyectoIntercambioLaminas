package com.unbosque.prg2.edu.co.beans;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;


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
public class UserBean implements Serializable{
	private User usuario;
	private User usuarioSesion;
	private DataModel listaUsuarios;
	private List<User> listaU;
	
	private String respuesta;
	
	private String password;
	private int numeroErroresContrasenia;

	// private String ip = InetAddress.getLocalHost().getHostAddress();

	public UserBean() {
		usuario = new User();
		 usuarioSesion=new User();
	}

	public String prepararAdicionarUsuario() throws UnknownHostException {
		usuario = new User();
		usuario.setActive("A");
		usuario.setDateLastPassword(new Date());
		usuario.setIp(Inet4Address.getLocalHost().getHostAddress());
		return "userRegister";
	}

	public String prepararModificarUsuario() {
		usuario = (User) (listaUsuarios.getRowData());
		return "userRegister";
	}

	public String cambiarClave() throws UnknownHostException{
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
		
		return "login";
		
	}


	public String eliminarUsuario() throws UnknownHostException {
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

		return "listUser";
	}

	public String activarUsuario() throws UnknownHostException {
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

		return "listUser";
	}

	public String adicionarUsuario() throws UnknownHostException {
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

		return "listUser";
	}

	

	public String prepararAdicionarUsuarioInicio() throws UnknownHostException {

		usuario = new User();
		usuario.setActive("A");
		usuario.setDateLastPassword(new Date());
		usuario.setUserType("USER");
		usuario.setIp(Inet4Address.getLocalHost().getHostAddress());
		return "userRegisterInicio";

	}

	public String adicionarUsuarioInicio() throws UnknownHostException {
		UserDAO dao = new UserDAOImpl();
		Util util = new Util();
		String clave = util.generarClaveAutomatica(8);
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

		return "login";
	}

	public String login() throws UnknownHostException {
		Util util = new Util();

		String respuesta = null;
		String userName1 = usuario.getUserName();
		String userPassword1 = usuario.getPassword();
		String userPassword2 = util.getStringMessageDigest(userPassword1, "MD5");

		User user1 = new UserDAOImpl().buscarUsuario(userName1, userPassword2);
		if (user1 != null) {
			usuarioSesion=user1;
			String tipo = user1.getUserType();
			if (user1.getChangePassword() == 1) {
				
				Date fechaSumada=util.sumarRestarDiasFecha(user1.getDateChangePassword(), 2);				
				
				if(fechaSumada.after(new Date())||fechaSumada.equals(new Date())){
					respuesta = "changePassword";	
				}else{
					this.respuesta="Debes solicitar una nueva clave";
					respuesta = "errorPage";	
				}
			} else {

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
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Error Login");
			aud.setTableName("user");
			
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			respuesta = "login";
		}
		return respuesta;
	}

	public String modificarUsuario() throws UnknownHostException {
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

		return "listUser";
	}
	
	public String modificarDatosUsuario() throws UnknownHostException {
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

		return "menuUser";
	}

	public User getUsuarioSesion(){
		return usuarioSesion;
	}
	public void  setUsuarioSesion(User usuarioSesion){
		this.usuarioSesion=usuarioSesion;
	}
	
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public DataModel getListarUsuarios() {
		List<User> lista = new UserDAOImpl().list();
		listaUsuarios = new ListDataModel(lista);
		return listaUsuarios;
	}

	public String recordarClave() throws UnknownHostException{
		
		 usuario = new UserDAOImpl().buscarUsuarioEmail(usuario.getEmailAddress());
		
		if(usuario==null){
			this.respuesta="Email No Encontrado";
			return "errorPage";			
		}else{
			Util util=new Util();
			String clave = util.generarClaveAutomatica(8);
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


			return "login";
		}
		
		
		
	}

	public void enviarCorreo(String entrada, String mensaje,String asunto) {
		Correo correo = new Correo();
		String salida = "lamiti2018@gmail.com";
		String clave = "Mundial2018";
		

		correo.enviarcorreo(salida, clave, entrada, mensaje, asunto);
	}
	
	public void setRespuesta(String respuesta){
		this.respuesta=respuesta;
	}
	
	public String getRespuesta( ){
		return this.respuesta;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	
	public String getPassword( ){
		return this.password;
	}
	
	public void validarUserName(FacesContext faces, UIComponent ui, Object login)
			throws ValidatorException 
	{
		UserDAO dao = new UserDAOImpl();
		String usuario = login.toString();
		List<User> lista=dao.buscarUserLogin(usuario);
		if(lista.isEmpty()==true)
		{

		}
		else
		{
			throw new ValidatorException(new FacesMessage(" El usuario ya esta registrado"));
		}
	}
	
	

}
