package com.unbosque.prg2.edu.co.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.unbosque.prg2.edu.co.dao.MissingsheetDAO;
import com.unbosque.prg2.edu.co.entity.Missingsheet;

import com.unbosque.prg2.edu.co.util.HibernateUtil;

public class MissingsheetDAOImpl implements MissingsheetDAO {

	static final Logger logger = Logger.getLogger(MissingsheetDAOImpl.class);
	@Override
	public void save(Missingsheet missingsheet) {
		logger.warn("Se necesita un objeto de tipo Missingsheet para poder guardarse");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(missingsheet);
		t.commit();
	}

	@Override
	public Missingsheet getUserId(int userId) {
		logger.warn("Se necesita la id del usuario para poder dar el Missingsheet");
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Missingsheet) session.load(Missingsheet.class, userId);
	}

	@Override
	public List<Missingsheet> list(int pUserId) {
		logger.warn("Se necesita la id del usuario para poder obtener la lista de Missingsheet");
		Session session = HibernateUtil.getSessionFactory().openSession();	

		Query query = session.createQuery("from Missingsheet where userId = :pUserId");
		query.setParameter("pUserId", pUserId);
		List lista = query.list();
		return lista;
	}

	@Override
	public void remove(Missingsheet missingsheet) {
		logger.warn("Se necesita un objeto de tipo Missingsheet para poder removerse");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(missingsheet);
		t.commit();

	}

	@Override
	public void update(Missingsheet missingsheet) {
		logger.warn("Se necesita un objeto de tipo Missingsheet para poder actualizarse");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(missingsheet);
		t.commit();

	}

}
