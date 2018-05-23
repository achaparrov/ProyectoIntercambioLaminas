package com.unbosque.prg2.edu.co.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.primefaces.model.UploadedFile;

import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.dao.StadiumDAO;
import com.unbosque.prg2.edu.co.dao.impl.AuditDAOImpl;
import com.unbosque.prg2.edu.co.dao.impl.StadiumDAOImpl;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.Stadium;
import com.unbosque.prg2.edu.co.util.Util;

@ManagedBean
@SessionScoped
public class StadiumBean implements Serializable {

	@ManagedProperty("#{userBean}")
	private UserBean userBean;
	private Stadium stadium;
	private DataModel listaStadium;
	private UploadedFile file;

	public String eliminarStadium() throws UnknownHostException {
		Stadium stadiumTemp = (Stadium) (listaStadium.getRowData());
		StadiumDAO dao = new StadiumDAOImpl();
		stadiumTemp.setState("I");
		dao.update(stadiumTemp);

		// Auditoria ELemento
		Audit aud = new Audit();
		aud.setCreateDate(new Date());
		aud.setOperation("Desactivar Estadio");
		aud.setTableId(stadiumTemp.getId());
		aud.setTableName("stadium");
		aud.setUserId(userBean.getUsuarioSesion().getId());
		aud.setIp(Inet4Address.getLocalHost().getHostAddress());
		AuditDAO audiDao = new AuditDAOImpl();
		audiDao.save(aud);
		// TERMINA Auditoria ELemento

		return "listStadium";
	}

	public String activarStadium() throws UnknownHostException {
		Stadium stadiumTemp = (Stadium) (listaStadium.getRowData());
		StadiumDAO dao = new StadiumDAOImpl();
		stadiumTemp.setState("A");
		dao.update(stadiumTemp);

		// Auditoria ELemento
		Audit aud = new Audit();
		aud.setCreateDate(new Date());
		aud.setOperation("Activar");
		aud.setTableId(stadiumTemp.getId());
		aud.setUserId(userBean.getUsuarioSesion().getId());
		aud.setTableName("news");
		aud.setIp(Inet4Address.getLocalHost().getHostAddress());
		AuditDAO audiDao = new AuditDAOImpl();
		audiDao.save(aud);
		// TERMINA Auditoria ELemento

		return "listNews";
	}

	public String prepararModificarStadium() {
		stadium = (Stadium) (listaStadium.getRowData());
		return "gerenciarStadium";
	}

	public String prepararVerStadium() {
		stadium = (Stadium) (listaStadium.getRowData());
		return "verStadium";
	}

	public String adicionarStadium() throws UnknownHostException {
		StadiumDAO dao = new StadiumDAOImpl();

		Util util = new Util();
		String destino = "";
		if (file != null) {
			
			try {
				destino = util.copyFile(file.getInputstream(), util.destinoEstadios);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		stadium.setPhoto(destino);
		dao.save(stadium);
		// Auditoria ELemento
		Audit aud = new Audit();
		aud.setCreateDate(new Date());
		aud.setOperation("Nuevo Estadio");
		aud.setTableId(stadium.getId());
		aud.setTableName("stadium");
		aud.setUserId(userBean.getUsuarioSesion().getId());
		aud.setIp(Inet4Address.getLocalHost().getHostAddress());
		AuditDAO audiDao = new AuditDAOImpl();
		audiDao.save(aud);
		// TERMINA Auditoria ELemento

		return "listStadium";
	}

	public String modificarStadium() throws UnknownHostException {
		StadiumDAO dao = new StadiumDAOImpl();
		Util util = new Util();
		String destino = "";
		String fotoAnterior=util.destinoEstadios+stadium.getPhoto();
		if (file != null) {			
			try {
				destino = util.copyFile(file.getInputstream(), util.destinoEstadios);
				stadium.setPhoto(destino);
				//util.eliminarArchivo(fotoAnterior);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		dao.update(stadium);
		// Auditoria ELemento
		Audit aud = new Audit();
		aud.setCreateDate(new Date());
		aud.setOperation("Editar Estadio");
		aud.setTableId(stadium.getId());
		aud.setTableName("stadium");
		aud.setUserId(userBean.getUsuarioSesion().getId());
		aud.setIp(Inet4Address.getLocalHost().getHostAddress());
		AuditDAO audiDao = new AuditDAOImpl();
		audiDao.save(aud);
		// TERMINA Auditoria ELemento
		return "listStadium";
	}

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}
	public List getListarStadiumLista() {
		List<Stadium> lista = new StadiumDAOImpl().list();		
		return lista;
	}

	public DataModel getListarStadium() {
		List<Stadium> lista = new StadiumDAOImpl().list();
		listaStadium = new ListDataModel(lista);
		return listaStadium;
	}

	public String prepararAdicionarEstadio() {
		stadium = new Stadium();
		stadium.setState("A");
		// stadium.setDateStadium(new Date());
		return "gerenciarStadium";
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}

