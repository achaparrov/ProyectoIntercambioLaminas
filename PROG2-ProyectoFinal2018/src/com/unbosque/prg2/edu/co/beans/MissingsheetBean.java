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

	
	  public String prepararAdicionarLamina() { 
		missingsheet = new	Missingsheet();
		missingsheet.setCountSheets(0);
		missingsheet.setUserId(userBean.getUsuarioSesion().getId());
		return  "agregarLamina";
		}
	


	public String eliminarLamina() throws UnknownHostException {
		Missingsheet missingsheetTemp = (Missingsheet) (listaMissingsheet.getRowData());
		MissingsheetDAO dao = new MissingsheetDAOImpl();
		

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
		return "laminasFaltantes";
	}

	public String adicionarLamina() throws UnknownHostException {
		MissingsheetDAO dao = new MissingsheetDAOImpl();
		dao.save(missingsheet);
		
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
				
		
		return "laminasFaltantes";
	}

	

	public Missingsheet getMissingsheet() {
		return missingsheet;
	}

	public void setMissingsheet(Missingsheet missingsheet) {
		this.missingsheet = missingsheet;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public DataModel getListarMissingsheet() {

		List<Missingsheet> lista = new MissingsheetDAOImpl().list(userBean.getUsuarioSesion().getId());
		listaMissingsheet = new ListDataModel(lista);

		return listaMissingsheet;
	}

}
