package com.unbosque.prg2.edu.co.beans;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.dao.MissingsheetDAO;
import com.unbosque.prg2.edu.co.dao.impl.AuditDAOImpl;
import com.unbosque.prg2.edu.co.dao.impl.MissingsheetDAOImpl;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.Missingsheet;

@ManagedBean
@SessionScoped
public class MissingsheetBean implements Serializable {

	@ManagedProperty("#{userBean}")
	private UserBean userBean;

	private Missingsheet missingsheet;
	private DataModel listaMissingsheet;

	static final Logger logger = Logger.getLogger(MissingsheetBean.class);


	public String prepararAdicionarLamina() { 
		logger.info("Entro en el metodo prepararAdicionarLamina");
		String respuesta = null;
		try {
			logger.info("Se prepara para poder adicionar lamina e iniciar agregarLamina.xhtml");
			missingsheet = new	Missingsheet();
			missingsheet.setCountSheets(0);
			missingsheet.setUserId(userBean.getUsuarioSesion().getId());
			respuesta =  "agregarLamina";
		}
		catch (Exception e) {
			logger.error("Error mientras se preparaba para poder adicionar lamina e iniciar agregarLamina.xhtml , mensaje: " + e.getMessage());
			e.printStackTrace();
		}

		return respuesta;
	}



	public String eliminarLamina() throws UnknownHostException {
		logger.info("Entro en el metodo eliminarLamina");
		Missingsheet missingsheetTemp = (Missingsheet) (listaMissingsheet.getRowData());
		MissingsheetDAO dao = new MissingsheetDAOImpl();
		String respuesta = null;

		try {
			logger.info("Se elimina una lamina y se inicializa laminaFaltantes.xhtml");
			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Eliminar");
			aud.setTableId(missingsheetTemp.getId());
			aud.setUserId(userBean.getUsuarioSesion().getId());
			aud.setTableName("missingsheet");
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			dao.remove(missingsheetTemp);
			respuesta = "laminasFaltantes";
		}
		catch (Exception e) {
			logger.error("Error mientras se eliminaba una lamina y se inicializaba laminaFaltantes.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}

		return respuesta;
	}

	public String adicionarLamina() throws UnknownHostException {
		logger.info("Entro en el metodo adicionarLamina");
		MissingsheetDAO dao = new MissingsheetDAOImpl();
		dao.save(missingsheet);
		String respuesta = null;

		try {
			logger.info("Se adiciona una lamina y se inicia laminaFaltantes.xhtml");
			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Agregar");
			aud.setTableId(missingsheet.getId());
			aud.setUserId(userBean.getUsuarioSesion().getId());
			aud.setTableName("missingsheet");
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento


			respuesta = "laminasFaltantes";
		}
		catch (Exception e) {
			logger.error("Error mientras se adiciona una lamina y se inicia laminaFaltantes.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}



	public Missingsheet getMissingsheet() {
		logger.info("Entro en el metodo getMissingsheet");
		Missingsheet respuesta = null;
		try {
			logger.info("Se da el Missingsheet");
			respuesta = missingsheet;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el Missingsheet, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public void setMissingsheet(Missingsheet missingsheet) {
		logger.info("Entro en el metodo setMissingsheet");

		try {
			logger.info("Se cambia el Missingsheet");
			this.missingsheet = missingsheet;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambia el Missingsheet, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public UserBean getUserBean() {
		logger.info("Entro en el metodo getUserBean");
		UserBean respuesta = null;

		try {
			logger.info("Se da el UserBean");
			respuesta = userBean;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el UserBean, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public void setUserBean(UserBean userBean) {
		logger.info("Entro en el metodo setUserBean");

		try {
			logger.info("Se cambia el UserBean");
			this.userBean = userBean;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambia el UserBean, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public DataModel getListarMissingsheet() {
		logger.info("Entro en el metodo getListarMissingsheet");
		DataModel respuesta = null;

		try {
			List<Missingsheet> lista = new MissingsheetDAOImpl().list(userBean.getUsuarioSesion().getId());
			listaMissingsheet = new ListDataModel(lista);

			respuesta = listaMissingsheet;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el DataModel o listaMissingsheet, mensaje: " + e.getMessage());
			e.printStackTrace();
		}

		return respuesta;
	}

}
