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

	public String prepararAdicionarRepeatedsheet() {
		repeatedsheet = new Repeatedsheet();
		repeatedsheet.setCountSheets(0);
		repeatedsheet.setUserId(userBean.getUsuarioSesion().getId());

		return "agregarLaminasRepetidas";
	}

	public String prepararModificarLamina() {
		repeatedsheet =  (Repeatedsheet) (listaRepeatedsheet.getRowData());
		return "agregarLaminasRepetidas";
	}
	public String modificarLaminaRepetida() throws UnknownHostException {
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
		return  "laminasRepetidas";
	}
	
	public String eliminarLamina() throws UnknownHostException {
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
		return "laminasRepetidas";
	}
	
	public String contactoCorreoCambio() {
		userBean.enviarCorreo("alix.chava.95@gmail.com","Hola Soy David y quiero cambiar láminas contigo."
				+ "\n\nTengo estas laminas para cambio: 1,2,4,6 "
				+ "\ny de las que tienes me sirven estas : 8,10"
				+ "\n\nContacta conmigo al:"
				+ "\n Correo: davidpoveda91@gmail.com"
				+ "\n Télefono: 3185369109"
				+ "\n\nCorreo generado con Lamiti.", "Cambiar Laminitas");
		
		return "intercambio2";
	}

	public String adicionarLaminaRepetida() {
		RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();
		dao.save(repeatedsheet);
		return "laminasRepetidas";
	}

	public String modificarRepeatedsheet() {
		RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();
		dao.update(repeatedsheet);
		return "laminasRepetidas";
	}

	public Repeatedsheet getRepeatedsheet() {
		return repeatedsheet;
	}

	public void setRepeatedsheet(Repeatedsheet repeatedsheet) {
		this.repeatedsheet = repeatedsheet;
	}

	public DataModel getListarRepeatedsheet() {
		List<Repeatedsheet> lista = new RepeatedsheetDAOImpl().list(userBean.getUsuarioSesion().getId());
		listaRepeatedsheet = new ListDataModel(lista);
		return listaRepeatedsheet;
	}
	
	public DataModel getListarLaminasFaltantes() {
		List lista = new RepeatedsheetDAOImpl().listaUsuariosCambio(userBean.getUsuarioSesion().getId());		
		
		return  new ListDataModel(lista);
	}
	
	
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	

}
