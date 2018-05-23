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
	

	public String eliminarNews() throws UnknownHostException {
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

		return "listNews";
	}

	public String activarNews() throws UnknownHostException {
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

		return "listNews";
	}
	public String prepararModificarNews() {
		news =  (News) (listaNews.getRowData());
		return "gerenciarNews";
	}
	
	public String prepararVerNews() {
		news =  (News) (listaNews.getRowData());
		return "verNews";
	}
	
	public String adicionarNews() throws UnknownHostException {
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
		
		return  "listNews";
	}
	
	public String modificarNews() throws UnknownHostException {
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
		return  "listNews";
	}
	
	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
	

	public DataModel getListarNews() {
		List<News> lista = new NewsDAOImpl().list();
		listaNews = new ListDataModel(lista);		
		return listaNews;
	}
	
	public String prepararAdicionarNoticia() {
		news = new News();
		news.setState("A");
		news.setDateNews(new Date());		
		return "gerenciarNews";
	}
	
	public UserBean getUserBean(){
		return userBean;
	}
	
	public void setUserBean(UserBean userBean){
		this.userBean=userBean;
	}


}
