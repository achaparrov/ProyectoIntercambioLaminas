package com.unbosque.prg2.edu.co.beans;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.dao.NewsDAO;
import com.unbosque.prg2.edu.co.dao.RepeatedsheetDAO;
import com.unbosque.prg2.edu.co.dao.impl.AuditDAOImpl;
import com.unbosque.prg2.edu.co.dao.impl.NewsDAOImpl;
import com.unbosque.prg2.edu.co.dao.impl.RepeatedsheetDAOImpl;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.News;
import com.unbosque.prg2.edu.co.entity.Repeatedsheet;
import com.unbosque.prg2.edu.co.util.Correo;

@ManagedBean
@SessionScoped
public class RepeatedsheetBean implements Serializable {

	@ManagedProperty("#{userBean}")
	private UserBean userBean;

	private Repeatedsheet repeatedsheet;
	private DataModel listaRepeatedsheet;

	static final Logger logger = Logger.getLogger(RepeatedsheetBean.class);

	public String prepararAdicionarRepeatedsheet() {
		logger.info("Entro en el metodo prepararAdicionarRepeatedsheet");
		String respuesta = null;

		try {
			logger.info("Se prepara para adicionar el Repeatedsheet y se inicializa agregarLaminaRepetidas.xhtml");
			repeatedsheet = new Repeatedsheet();
			repeatedsheet.setCountSheets(0);
			repeatedsheet.setUserId(userBean.getUsuarioSesion().getId());

			respuesta = "agregarLaminasRepetidas";
		}
		catch (Exception e) {
			logger.error("Error mientras se preparaba para adicionar el Repeatedsheet y se inicializaba agregarLaminaRepetidas.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String prepararModificarLamina() {
		logger.info("Entro en el metodo prepararMoficiarLamina");
		String respuesta = null;

		try {
			logger.info("Se prepara para modificar la lamina y se inicializa agregarLaminaRepetidas.xhtml");
			repeatedsheet =  (Repeatedsheet) (listaRepeatedsheet.getRowData());
			respuesta = "agregarLaminasRepetidas";

		}
		catch (Exception e) {
			logger.error("Error mientras se preparaba para modificar la lamina y se inicializaba agregarLaminaRepetidas.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}
	public String modificarLaminaRepetida() throws UnknownHostException {
		logger.info("Entro en el metodo modificarLaminaRepetida");
		String respuesta = null;

		try {
			logger.info("Se prepara para modificar la lamina repetida y se inicializa laminasRepetidas.xhtml");
			RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();
			dao.update(repeatedsheet);
			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Editar");
			aud.setTableId(repeatedsheet.getId());
			aud.setTableName("repeatedsheet");
			aud.setUserId(userBean.getUsuarioSesion().getId());
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento
			respuesta =  "laminasRepetidas";
		}
		catch (Exception e) {
			logger.error("Error mientras se modificaba la lamina repetida laminaRepetidas.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String eliminarLamina() throws UnknownHostException {
		logger.info("Entro en el metodo eliminarLamina");
		String respuesta = null;

		try {
			logger.info("Se prepara para eliminar la lamina y se inicializa laminasRepetidas.xhtml");
			Repeatedsheet repeatedsheetTemp = (Repeatedsheet) (listaRepeatedsheet.getRowData());
			RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();

			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Eliminar");
			aud.setTableId(repeatedsheetTemp.getId());
			aud.setUserId(userBean.getUsuarioSesion().getId());
			aud.setTableName("repeatedsheet");
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			dao.remove(repeatedsheetTemp);
			respuesta = "laminasRepetidas";
		}
		catch (Exception e) {
			logger.error("Error mientras se eliminaba la lamina y se inicializaba laminaRepetidas.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String contactoCorreoCambio() {
		logger.info("Entro en el metodo contactoCorreoCambio");
		String respuesta = null;

		try {
			logger.info("Se contacta por correo al usuario para que haga cambio de su contraseña y se inicializa intercambio2.xhtml");
			userBean.enviarCorreo("alix.chava.95@gmail.com","Hola Soy Ibrahim y quiero cambiar láminas contigo."
					+ "\n\nTengo estas laminas para cambio: 1,2,4,6 "
					+ "\ny de las que tienes me sirven estas : 8,10"
					+ "\n\nContacta conmigo al:"
					+ "\n Correo: ibrahimdkj@gmail.com"
					+ "\n Télefono: 3183933987"
					+ "\n\nCorreo generado con Lamiti.", "Cambiar Laminitas");

			respuesta = "intercambio2";
		}
		catch (Exception e) {
			logger.error("Error mientras se contactaba por correo al usuario para que haga cambio de su contraseña y se inicializaba intercambio2.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String adicionarLaminaRepetida() {
		logger.info("Entro en el metodo adicionarLaminaRepetida");
		String respuesta = null;

		try {
			logger.info("Se adiciona una lamina repetida y se inicializa laminasRepetidas.xhtml");
			RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();
			dao.save(repeatedsheet);
			respuesta = "laminasRepetidas";
		}
		catch (Exception e) {
			logger.error("Error mientras se adicionaba una lamina repetida y se inicializaba laminaRepetidas.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String modificarRepeatedsheet() {
		logger.info("Entro en el metodo modificarRepeatedsheet");
		String respuesta = null;

		try {
			logger.info("Se modifica el Repeatedsheet y se inicializa laminasRepetidas.xhtml");
			RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();
			dao.update(repeatedsheet);
			respuesta = "laminasRepetidas";
		}
		catch (Exception e) {
			logger.error("Error mientras se modificaba el Repeatedsheet y se inicializaba laminaRepetidas.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;

	}

	public Repeatedsheet getRepeatedsheet() {
		logger.info("Entro en el metodo getRepeatedsheet");
		Repeatedsheet respuesta = null;

		try {
			logger.info("Se da el Repeatedsheet");
			respuesta = repeatedsheet;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el Repeatedsheet, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public void setRepeatedsheet(Repeatedsheet repeatedsheet) {
		logger.info("Entro en el metodo setRepeatedsheet");
		try {
			logger.info("Se cambia el Repeatedsheet");
			this.repeatedsheet = repeatedsheet;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambiaba el Repeatedsheet, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public DataModel getListarRepeatedsheet() {
		logger.info("Entro en el metodo getListarRepeatedsheet");
		DataModel respuesta = null;
		try {
			logger.info("Se da el DataModel o la lista de Repeatedsheet");
			List<Repeatedsheet> lista = new RepeatedsheetDAOImpl().list(userBean.getUsuarioSesion().getId());
			listaRepeatedsheet = new ListDataModel(lista);
			respuesta = listaRepeatedsheet;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el DataModel o la lista de Repeatedsheet, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public DataModel getListarLaminasFaltantes() {
		logger.info("Entro en el metodo getListarLaminasFaltantes");
		DataModel respuesta = null;
		try {
			logger.info("Se da el DataModel o la lista de laminas faltantes");
			List lista = new RepeatedsheetDAOImpl().listaUsuariosCambio(userBean.getUsuarioSesion().getId());		
			respuesta =  new ListDataModel(lista);
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el DataModel o la lista de laminas faltantes, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
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
			logger.error("Error mientras se cambiaba el UserBean, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
