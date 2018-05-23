package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.dao.NewsDAO;
import com.unbosque.prg2.edu.co.entity.Missingsheet;
import com.unbosque.prg2.edu.co.entity.News;
import com.unbosque.prg2.edu.co.util.HibernateUtil;

public class NewsDAOImpl implements NewsDAO {

	@Override
	public void save(News news) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(news);
		t.commit();
		
	}

	@Override
	public News getId(int id) {
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
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(news);
		t.commit();
		
	}

	@Override
	public void update(News news) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(news);
		t.commit();
		
	}
	
	

}
