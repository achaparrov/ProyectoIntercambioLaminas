package com.unbosque.prg2.edu.co.beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.dao.impl.AuditDAOImpl;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.User;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import org.apache.log4j.Logger;



@ManagedBean
@SessionScoped
public class AuditBean  implements Serializable  {

	private Audit auditoria;
	private DataModel listaAuditoria;

	static final Logger logger = Logger.getLogger(AuditBean.class);

	public String adicionarAuditoria(Audit auditoria){
		logger.info("Entro en el metodo adicionarAuditoria");
		String respuesta = null;
		try {
			logger.info("Se adiciona o se guarda una clase de Audit o auditoria para despues pasar a el index.xhtml");
			AuditDAO dao=new AuditDAOImpl();
			dao.save(auditoria);
			respuesta = "index";
		}
		catch (Exception e) {
			logger.error("Error mientras se adicionaba o se guardaba una clase de Audit o auditoria, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}
	public DataModel getListarAuditoria(){
		logger.info("Entro en el metodo getListarAuditoria");
		DataModel respuesta = null;

		try {
			logger.info("Se da la lista de auditorias");
			List<Audit>lista=new AuditDAOImpl().list();
			listaAuditoria=new ListDataModel(lista);
			respuesta = listaAuditoria;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba la lista de auditorias, mensaje: " + e.getMessage());
			e.printStackTrace();
		}

		return respuesta;
	}

	public void cargaPDF(Object document) throws IOException, BadElementException, DocumentException {
		logger.info("Entro en el metodo cargarPDF");

		try {
			logger.info("Se carga un PDF con la información");
			Document pdf = (Document) document;
			pdf.open();
			pdf.setPageSize(PageSize.A4);

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "logo.png";
			Image logoLaminiti = Image.getInstance(logo);
			Image logoLaminitiFondo = Image.getInstance(logo);
			logoLaminiti.setAlignment(logoLaminiti.ALIGN_MIDDLE);
			logoLaminiti.scaleAbsolute(60, 56);
			pdf.add(logoLaminiti);
		}
		catch (Exception e) {
			logger.error("Error mientras se cargaba el PDF, mensaje: " + e.getMessage());
			e.printStackTrace();
		}

	}

}
