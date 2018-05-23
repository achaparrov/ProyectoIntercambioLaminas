package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.dao.NewsDAO;
import com.unbosque.prg2.edu.co.entity.Missingsheet;
import com.unbosque.prg2.edu.co.entity.News;
import com.unbosque.prg2.edu.co.util.HibernateUtil;

public class NewsDAOImpl implements NewsDAO {

	static final Logger logger = Logger.getLogger(NewsDAOImpl.class);
	@Override
	public void save(News news) {
		logger.warn("Se necesita un objeto de tipo News para poder guardarse");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(news);
		t.commit();

	}

	@Override
	public News getId(int id) {
		logger.warn("Se necesita una id para poder dar News");
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (News) session.load(News.class, id);
	}

	@Override
	public List<News> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from News order by id DESC").list();
		t.commit();
		return lista;
	}

	@Override
	public void remove(News news) {
		logger.warn("Se necesita un objeto de tipo News para poder removerlo");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(news);
		t.commit();

	}

	@Override
	public void update(News news) {
		logger.warn("Se necesita un objeto de tipo News para poder actualizarse");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(news);
		t.commit();

	}



}
