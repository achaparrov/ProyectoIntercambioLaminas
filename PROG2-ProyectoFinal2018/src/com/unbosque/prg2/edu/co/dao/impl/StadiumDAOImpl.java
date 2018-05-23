package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.dao.StadiumDAO;
import com.unbosque.prg2.edu.co.entity.Stadium;
import com.unbosque.prg2.edu.co.util.HibernateUtil;

public class StadiumDAOImpl implements StadiumDAO {

	static final Logger logger = Logger.getLogger(StadiumDAOImpl.class);
	@Override
	public void save(Stadium stadium) {
		logger.warn("Se necesita un objeto de tipo Stadium para poder guardarlo");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(stadium);
		t.commit();
	}

	@Override
	public List<Stadium> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from stadium").list();
		t.commit();
		return lista;	

	}

	@Override
	public void remove(Stadium stadium) {
		logger.warn("Se necesita un objeto de tipo Stadium para poder removerlo");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(stadium);
		t.commit();
	}

	@Override
	public void update(Stadium stadium) {
		logger.warn("Se necesita un objeto de tipo Stadium para poder actualizarlo");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(stadium);
		t.commit();

	}

}
