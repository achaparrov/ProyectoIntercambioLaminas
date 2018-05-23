package com.unbosque.prg2.edu.co.beans;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.unbosque.prg2.edu.co.dao.AuditDAO;
import com.unbosque.prg2.edu.co.dao.MissingsheetDAO;
import com.unbosque.prg2.edu.co.dao.NewsDAO;
import com.unbosque.prg2.edu.co.dao.UserDAO;
import com.unbosque.prg2.edu.co.dao.impl.AuditDAOImpl;
import com.unbosque.prg2.edu.co.dao.impl.MissingsheetDAOImpl;
import com.unbosque.prg2.edu.co.dao.impl.NewsDAOImpl;
import com.unbosque.prg2.edu.co.dao.impl.UserDAOImpl;
import com.unbosque.prg2.edu.co.entity.Audit;
import com.unbosque.prg2.edu.co.entity.Missingsheet;
import com.unbosque.prg2.edu.co.entity.News;
import com.unbosque.prg2.edu.co.entity.User;



@ManagedBean
@SessionScoped
public class NewsBean  implements Serializable  {

	@ManagedProperty("#{userBean}")
	private UserBean userBean;

	private News news;
	private DataModel listaNews;

	static final Logger logger = Logger.getLogger(NewsBean.class);


	public String eliminarNews() throws UnknownHostException {
		logger.info("Entro en el metodo eliminarNews");
		String respuesta = null;

		try {
			logger.info("Se elimina una noticia");
			News newTemp = (News) (listaNews.getRowData());
			NewsDAO dao = new NewsDAOImpl();
			newTemp.setState("I");
			dao.update(newTemp);

			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Desactivar");
			aud.setTableId(newTemp.getId());
			aud.setTableName("news");
			aud.setUserId(userBean.getUsuarioSesion().getId());
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			respuesta = "listNews";
		}
		catch (Exception e) {
			logger.error("Error mientras se eliminaba una noticia, mensaje: " + e.getMessage());
			e.printStackTrace();
		}

		return respuesta;
	}

	public String activarNews() throws UnknownHostException {
		logger.info("Entro en el metodo activarNews");
		String respuesta = null;

		try {
			logger.info("Se activa la noticia y se inicia listNews.xhtml");
			News newsTemp = (News) (listaNews.getRowData());
			NewsDAO dao = new NewsDAOImpl();
			newsTemp.setState("A");
			dao.update(newsTemp);

			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Activar");
			aud.setTableId(newsTemp.getId());
			aud.setUserId(userBean.getUsuarioSesion().getId());
			aud.setTableName("news");
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			respuesta = "listNews";
		}
		catch (Exception e) {
			logger.error("Error mientras se activaba una noticia y se inicializaba listNews.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}
	public String prepararModificarNews() {
		logger.info("Entro en el metodo prepararModificarNews");
		String respuesta = null;

		try {
			logger.info("Se prepara para modificar una noticia y se inicia gerenciarNews.xhtml");
			news =  (News) (listaNews.getRowData());
			respuesta = "gerenciarNews";
		}
		catch (Exception e) {
			logger.error("Error mientras se preparaba para modificar las noticias e inicializar gerenciarNews.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String prepararVerNews() {
		logger.info("Entro en el metodo prepararVerNews");
		String respuesta = null;

		try {
			logger.info("Se prepara para ver noticias y se inicializa verNews.xhtml");
			news =  (News) (listaNews.getRowData());
			respuesta = "verNews";
		}
		catch (Exception e) {
			logger.error("Error mientras se preparaba para ver las noticas y se inicializaba verNews.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String adicionarNews() throws UnknownHostException {
		logger.info("Entro en el metodo adicionarNews");
		String respuesta = null;

		try {
			logger.info("Se adiciona noticas y se inicia listNews.xhtml");
			NewsDAO dao = new NewsDAOImpl();
			dao.save(news);
			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Nuevo");
			aud.setTableId(news.getId());
			aud.setTableName("news");
			aud.setUserId(userBean.getUsuarioSesion().getId());
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento

			respuesta =  "listNews";
		}
		catch (Exception e) {
			logger.error("Error mientras se adicionaba una noticia listNews.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String modificarNews() throws UnknownHostException {
		logger.info("Entro en el metodo modificarNews");
		String respuesta = null;

		try {
			logger.info("Se modifica las noticias y se inicia listNews.xhtml");
			NewsDAO dao = new NewsDAOImpl();
			dao.update(news);
			// Auditoria ELemento
			Audit aud = new Audit();
			aud.setCreateDate(new Date());
			aud.setOperation("Editar");
			aud.setTableId(news.getId());
			aud.setTableName("news");
			aud.setUserId(userBean.getUsuarioSesion().getId());
			aud.setIp(Inet4Address.getLocalHost().getHostAddress());
			AuditDAO audiDao = new AuditDAOImpl();
			audiDao.save(aud);
			// TERMINA Auditoria ELemento
			respuesta =  "listNews";
		}
		catch (Exception e) {
			logger.error("Error mientras se modificaba una noticia y se inicializaba listNews.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public News getNews() {
		logger.info("Entro en el metodo getNews");
		News respuesta = null;

		try {
			logger.info("Se da las noticias");
			respuesta = news;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba las noticias, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public void setNews(News news) {

		logger.info("Entro en el metodo setNews");

		try {
			logger.info("Se cambia las noticias");
			this.news = news;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambiaba las noticas, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}


	public DataModel getListarNews() {
		logger.info("Entro en el metodo getListarNews");
		DataModel respuesta = null;
		try {
			List<News> lista = new NewsDAOImpl().list();
			listaNews = new ListDataModel(lista);		
			respuesta = listaNews;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el DatModel o la lista de noticias, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public String prepararAdicionarNoticia() {
		logger.info("Entro en el metodo prepararAdicionarNotica");
		String respuesta = null;

		try
		{
			logger.info("Se prepara para adicionar una noticia y se inicializa gerenciarNews.xhtml");
			news = new News();
			news.setState("A");
			news.setDateNews(new Date());		
			respuesta = "gerenciarNews";
		}
		catch (Exception e) {
			logger.error("Error mientras se preparaba para adicionar una notica y se inicializaba gerenciarNews.xhtml, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public UserBean getUserBean(){
		logger.info("Entro en el metodo getUserBean");
		UserBean respuesta = null;

		try
		{
			logger.info("Se da el UserBean");
			respuesta = userBean;
		}
		catch (Exception e) {
			logger.error("Error mientras se daba el UserBean, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return respuesta;
	}

	public void setUserBean(UserBean userBean){
		logger.info("Entro en el metodo setUserBean");
		try
		{
			logger.info("Se cambia el UserBean");
			this.userBean=userBean;
		}
		catch (Exception e) {
			logger.error("Error mientras se cambiaba el UserBean, mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
