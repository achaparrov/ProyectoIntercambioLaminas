/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad El Bosque (Bogotá - Colombia)
 * Facultad de Ingenieria, Programa de Ingenieria de Sistemas
 * 
 * Proyecto Sistema de gestión de intercambio de láminas para el campeonato
 * mundial de fútbol Rusia 2018 Lamiti.
 * Empresa de Software: Future in hands
 * Autores: David Poveda, Andrés Lozano, Laura Peña y Alix Chaparro
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package com.unbosque.prg2.edu.co.beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.dao.impl.AuditDAOImpl;
import com.unbosque.prg2.edu.co.dao.impl.RepeatedsheetDAOImpl;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.User;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;


/**
 * The Class AuditBean.
 */
@ManagedBean
@SessionScoped
public class AuditBean implements Serializable {


	/** The auditoria. */
	private Audit auditoria;
	
	/** The lista auditoria. */
	private DataModel listaAuditoria;

	/**
	 * Adicionar auditoria.
	 *
	 * @param auditoria the auditoria
	 * @return the string
	 */
	public String adicionarAuditoria(Audit auditoria) {
		AuditDAO dao = new AuditDAOImpl();
		dao.save(auditoria);
		return "index";
	}

	/**
	 * Gets the listar auditoria.
	 *
	 * @return the listar auditoria
	 */
	public DataModel getListarAuditoria() {
		
		 List<Audit>lista=new AuditDAOImpl().list(); listaAuditoria=new
		 ListDataModel(lista); return listaAuditoria;
		 
	

	}
	
	/**
	 * Gets the listar audit usuario.
	 *
	 * @return the listar audit usuario
	 */
	public DataModel getListarAuditUsuario() {
		List lista = new AuditDAOImpl().listaConUsuario();		
		
		return  new ListDataModel(lista);
	}
	

	/**
	 * Carga PDF.
	 *
	 * @param document the document
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BadElementException the bad element exception
	 * @throws DocumentException the document exception
	 */
	public void cargaPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images"
				+ File.separator + "logo.png";
		Image logoLaminiti = Image.getInstance(logo);
		Image logoLaminitiFondo = Image.getInstance(logo);
		logoLaminiti.setAlignment(logoLaminiti.ALIGN_MIDDLE);
		logoLaminiti.scaleAbsolute(60, 56);
		pdf.add(logoLaminiti);

	}

}
